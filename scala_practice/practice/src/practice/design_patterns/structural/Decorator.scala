package practice.design_patterns.structural

import scala.jdk.CollectionConverters._
import java.io.BufferedReader
import java.nio.charset.Charset
import java.util.Base64

import com.typesafe.scalalogging.LazyLogging

trait InputReader {
  def readLines(): LazyList[String]
}

class AdvancedInputReader(reader: BufferedReader) extends InputReader {
  override def readLines(): LazyList[String] =
    reader.lines().iterator().asScala.to(LazyList)
}

// Base decorator
abstract class InputReaderDecorator(inputReader: InputReader) extends InputReader {
  override def readLines(): LazyList[String] =
    inputReader.readLines()
}

// Implementations of decorator
class CapitalizedInputReader(inputReader: InputReader) extends InputReaderDecorator(inputReader) {
  override def readLines(): LazyList[String] = super.readLines().map(_.toUpperCase)
}

class CompressingInputReader(inputReader: InputReader) extends InputReaderDecorator(inputReader) with LazyLogging {
  override def readLines(): LazyList[String] = super.readLines().map {
    _.toUpperCase()
  }
}

class Base64EncoderInputReader(inputReader: InputReader) extends InputReaderDecorator(inputReader) {
  override def readLines(): LazyList[String] = super.readLines().map {
    case line => Base64.getEncoder.encodeToString(line.getBytes(Charset.forName("UTF-8")))
  }
}
