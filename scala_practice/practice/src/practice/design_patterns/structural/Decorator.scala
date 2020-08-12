package practice.design_patterns.structural

import scala.jdk.CollectionConverters._
import java.io.{BufferedInputStream, BufferedReader, ByteArrayOutputStream, InputStreamReader}
import java.nio.charset.Charset
import java.util.Base64
import java.util.zip.GZIPOutputStream

import com.typesafe.scalalogging.LazyLogging

// Use stackable traits to implement decorator design pattern

trait InputReader {
  def readLines(): LazyList[String]
}

class AdvancedInputReader(reader: BufferedReader) extends InputReader {
  override def readLines(): LazyList[String] =
    reader.lines().iterator().asScala.to(LazyList)
}

trait CapitalizedInputReaderTrait extends InputReader {
  // Abstract override allows us to call super for a method in a trait that is declared abstract
  abstract override def readLines(): LazyList[String] =
    super.readLines().map(_.toUpperCase)
}

trait CompressingInputReader extends InputReader with LazyLogging {
  abstract override def readLines(): LazyList[String] = super.readLines().map {
    case line =>
      val text = line.getBytes(Charset.forName("UTF-8"))
      logger.info("Length before compression: {}", text.length.toString)
      val output = new ByteArrayOutputStream()
      val compressor = new GZIPOutputStream(output)
      try {
        compressor.write(text, 0, text.length)
        val outputByteArray = output.toByteArray
        logger.info("Length after compression: {}", outputByteArray.length.toString)
        new String(outputByteArray, Charset.forName("UTF-8"))
      } finally {
        compressor.close()
        output.close()
      }
  }
}

trait Base64EncoderInputReader extends InputReader with LazyLogging {
  abstract override def readLines(): LazyList[String] = super.readLines().map {
    case line => Base64.getEncoder.encodeToString(line.getBytes(Charset.forName("UTF-8")))
  }
}


// Base decorator
abstract class InputReaderDecorator(inputReader: InputReader) extends InputReader {
  override def readLines(): LazyList[String] =
    inputReader.readLines()
}


object DecoratorExample extends App {
  val stream = new BufferedReader(
    new InputStreamReader(
      new BufferedInputStream(this.getClass.getClassLoader.getResourceAsStream("data.txt"))
    )
  )
  try {
    val reader = new AdvancedInputReader(stream) with CapitalizedInputReaderTrait
      with Base64EncoderInputReader
      with CompressingInputReader
    reader.readLines().foreach(println)
  } finally {
    stream.close()
  }
}
