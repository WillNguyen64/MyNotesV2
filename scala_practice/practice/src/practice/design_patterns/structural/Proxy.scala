package practice.design_patterns.structural

import scala.jdk.CollectionConverters._
import java.io.{BufferedReader, InputStreamReader}

// Use proxy design pattern when you want to delegate some expensive operations to other classes,
// do operation lazily, etc..

// Proxies are similar to Decorator, but where they differ is Decorators enhance an interface with additional
// functionality, while proxies provide the same interface for application efficiency

trait FileReader {
  def readFileContents(): String
}

class FileReaderReal(filename: String) extends FileReader {
  val contents = {
    val stream = this.getClass.getClassLoader.getResourceAsStream(filename)
    val reader = new BufferedReader(
      new InputStreamReader(
        stream
      )
    )
    try {
      reader.lines().iterator().asScala.mkString
      System.getProperty("line.separator")
    } finally {
      reader.close()
      stream.close()
    }
  }

  println(s"Finished reading the actual file: $filename")

  override def readFileContents(): String = contents
}

class FileReaderProxy(filename: String) extends FileReader {
  private lazy val fileReader: FileReaderReal = new FileReaderReal(filename)

  override def readFileContents(): String = {
    fileReader.readFileContents()
  }
}

object ProxyExample extends App {
  val fileMap = Map(
    "file1.txt" -> new FileReaderProxy("file1.txt"),
    "file2.txt" -> new FileReaderProxy("file2.txt"),
    "file3.txt" -> new FileReaderProxy("file3.txt"),
    "file4.txt" -> new FileReaderReal("file1.txt"),

  )

  println("Created the map. You should have seen file1.txt read because it wasn't used in a proxy.")
  println(s"Reading file1.txt from the proxy: ${fileMap("file1.txt").readFileContents()}")
}
