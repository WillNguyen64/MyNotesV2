package synonym_matching.vocab

import java.nio.file.Paths

import org.apache.lucene.analysis.core.{KeywordAnalyzer, StopAnalyzer, WhitespaceAnalyzer, WhitespaceTokenizerFactory}
import org.apache.lucene.analysis.custom.CustomAnalyzer
import org.apache.lucene.analysis.en.EnglishAnalyzer
import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper
import org.apache.lucene.analysis.synonym.SynonymGraphFilterFactory
import org.apache.lucene.analysis.{Analyzer, CharArraySet}
import org.apache.lucene.document.{Document, Field, TextField}
import org.apache.lucene.index.{IndexWriter, IndexWriterConfig}
import org.apache.lucene.store.FSDirectory

import scala.jdk.CollectionConverters._
import scala.collection.mutable

/**
 * Test driver for creating a Lucene index that supports vocabulary-based synonym matching
 * From Chapter 2 of "Deep Learning for Search"
 *
 * Limitations of using vocabulary-based approach:
 *   - Requires maintaining a list of synonyms. Can use a public one like WordNet but
 *     it is limited to English.
 *   - Synonyms based on dictionary definitions may not cover all of the synonyms used in
 *     everyday communication, like news articles, books, online discussions, etc.
 *     Would need a more flexible approach that considers context instead of grammar / syntax,
 *     such as word2vec which identifies two words as synonyms if they appear in similar
 *     contexts.
 */
object IndexDriver extends App {

  // Define the index directory
  val path = Paths.get("./lucene_index")
  val dir = FSDirectory.open(path)

  def textFieldAnalyzerWithStopWordFilter: Analyzer = {
    // Define the analyzers
    // For "title" field, tokenizes the title based on whitespace chars
    // For "text" field, tokens at non-letter chars, removes the stop words, convert tokens to lowercase.
    // For other fields, use the default English analyzer, which uses the default stop words list.
    val stopWords = List("a", "an", "the")
    val stopWordsAnalyzer = new StopAnalyzer(
      new CharArraySet(stopWords.asJava, true)
    )

    val perFieldAnalyzers = Map(
      "title" -> new WhitespaceAnalyzer(),
      "text" -> stopWordsAnalyzer
    )

    new PerFieldAnalyzerWrapper(
      new EnglishAnalyzer(),
      perFieldAnalyzers.asJava
    )
  }

  def textFieldAnalyzerWithSynFilter: Analyzer = {

    val synGraphFilterArgs = mutable.Map(
      "ignoreCase" -> "true",
      "synonyms" -> "synonym_matching/vocab/synonyms.txt"
      // Couldn't find steps to create the synonyms file using the WordNet database
      // "synonyms" -> "synonyms-wn.txt",
      // "format" -> "wordnet")
    )

    CustomAnalyzer.builder()
      .withTokenizer(classOf[WhitespaceTokenizerFactory])
      .addTokenFilter(classOf[SynonymGraphFilterFactory], synGraphFilterArgs.asJava)
      .build()
  }

  // Create the index, with two documents

  println(s"Creating analyzer")
  val analyzer = new PerFieldAnalyzerWrapper(
    textFieldAnalyzerWithSynFilter,
    Map[String, Analyzer]("year" -> new KeywordAnalyzer).asJava
  )

  println(s"Creating index writer")
  val config = new IndexWriterConfig(analyzer)
  val writer = new IndexWriter(dir, config)

  println(s"Adding documents to index")
  val doc = new Document()
  doc.add(new Field("title", "Aeroplane", TextField.TYPE_STORED))
  doc.add(new Field("author", "Red Hot Chili Peppers", TextField.TYPE_STORED))
  doc.add(new Field("year", "1995", TextField.TYPE_STORED))
  doc.add(new Field("album", "One Hot Minute", TextField.TYPE_STORED))
  doc.add(new Field("text", "I like pleasure spiked with pain and music " +
    "is my aeroplane ...", TextField.TYPE_STORED))
  writer.addDocument(doc)

  println(s"Committing index")
  writer.commit()
  writer.close()



}
