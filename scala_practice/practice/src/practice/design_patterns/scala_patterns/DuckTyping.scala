package practice.design_patterns.scala_patterns

import java.util.StringTokenizer

// Duck typing - allows us to handle different objects with same method signature without needing to
// write common interfaces.  Can achieve same result using wrappers.  One con of using this is that
// under the hood, it uses reflection which leads to slower performance.

class SentenceParserSplit {

  def parse(sentence: String): Array[String] =
    sentence.split("\\s")
}

class SentenceParserTokenize {

  def parse(sentence: String): Array[String] = {
    val tokenizer = new StringTokenizer(sentence)
    Iterator.continually({
      val hasMore = tokenizer.hasMoreTokens
      if (hasMore) {
        (hasMore, tokenizer.nextToken())
      } else {
        (hasMore, null)
      }
    }).takeWhile(_._1).map(_._2).toArray
  }

}

object DuckTypingExample {

  def printSentenceParts(sentence: String, parser: {def parse(sentence: String): Array[String]}) =
    parser.parse(sentence).foreach(println)

  def main(args: Array[String]): Unit = {
    val tokenizerParser = new SentenceParserTokenize
    val splitParser = new SentenceParserSplit

    val sentence = "This is the sentence we will be splitting."

    System.out.println("Using the tokenize parser: ")
    printSentenceParts(sentence, tokenizerParser)

    System.out.println("Using the split parser: ")
    printSentenceParts(sentence, splitParser)
  }
}