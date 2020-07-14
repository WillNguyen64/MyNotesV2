package synonym_matching.vocab

import java.nio.file.Paths

import org.apache.lucene.analysis.Analyzer
import org.apache.lucene.analysis.core.WhitespaceAnalyzer
import org.apache.lucene.index.DirectoryReader
import org.apache.lucene.queryparser.classic.QueryParser
import org.apache.lucene.search.{IndexSearcher, TopDocs}
import org.apache.lucene.store.FSDirectory

/**
 * Test driver for searching a Lucene index using vocabulary-based synonym matching
 * From Chapter 2 of "Deep Learning for Search"
 */
object SearchDriver extends App {

  val path = Paths.get("./lucene_index")
  val dir = FSDirectory.open(path)

  def searchTimeAnalyzer: Analyzer = {
    new WhitespaceAnalyzer()
  }

  val reader = DirectoryReader.open(dir)
  val searcher = new IndexSearcher(reader)
  val parser = new QueryParser("text", searchTimeAnalyzer)

  val query = parser.parse("plane")

  val hits: TopDocs = searcher.search(query, 10)

  for (scoreDoc <- hits.scoreDocs) {
    val doc = reader.document(scoreDoc.doc)
    println(s"${doc.get("title")} by ${doc.get("author")} (score: ${scoreDoc.score})")
  }


}
