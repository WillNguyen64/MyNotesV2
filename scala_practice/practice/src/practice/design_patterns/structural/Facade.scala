package practice.design_patterns.structural

import java.nio.charset.Charset

trait DataDownloader {
  def download(url: String): Array[Byte] = "some data from website".getBytes(Charset.forName("UTF-8"))
}

trait DataDecoder {
  def decode(data: Array[Byte]): String = new String(data)
}

class DataReader extends DataDownloader with DataDecoder {
  def readData(url: String): String = {
    val data = download(url)
    decode(data)
  }
}

object FacadeExample extends App {
  val reader = new DataReader
  println(s"We just read the following data: ${reader.readData("https://some-website.com")}")
}
