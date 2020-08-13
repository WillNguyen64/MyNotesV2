package practice.design_patterns.behavioral2

import java.io.{ByteArrayInputStream, InputStreamReader}

import com.github.tototoshi.csv.CSVReader
import org.json4s.{DefaultFormats, StringInput}
import org.json4s.native.JsonMethods

// Template method defers algorithm steps to subclasses
// Suitable for implementing frameworks
// You have an algorithm that performs a set of steps, where each step can be implemented differently

case class Person(name: String, age: Int, address: String)

abstract class DataFinder[T, Y] {

  def find(f: T => Option[Y]): Option[Y] = {
    try {
      val data: Array[Byte] = readData()
      val parsed: T = parse(data)
      f(parsed)
    } finally {
      cleanup()
    }
  }

  def readData(): Array[Byte]
  def parse(data: Array[Byte]): T
  def cleanup()
}

class JsonDataFinder extends DataFinder[List[Person], Person] {
  implicit val formats = DefaultFormats

  override def readData(): Array[Byte] = {
    val stream = this.getClass.getClassLoader.getResourceAsStream("people.json")
    LazyList.continually(stream.read).takeWhile(_ != -1).map(_.toByte).toArray
  }

  override def cleanup(): Unit = {
    System.out.println("Reading json: nothing to do.")
  }

  override def parse(data: Array[Byte]): List[Person] =
    JsonMethods.parse(StringInput(new String(data, "UTF-8"))).extract[List[Person]]
}

class CSVDataFinder extends DataFinder[List[Person], Person] {
  override def readData(): Array[Byte] = {
    val stream = this.getClass.getClassLoader.getResourceAsStream("people.csv")
    LazyList.continually(stream.read).takeWhile(_ != -1).map(_.toByte).toArray
  }

  override def cleanup(): Unit = {
    System.out.println("Reading csv: nothing to do.")
  }

  override def parse(data: Array[Byte]): List[Person] =
    CSVReader.open(new InputStreamReader(new ByteArrayInputStream(data))).all().map {
      case List(name, age, address) =>
        Person(name, age.toInt, address)
    }
}


object DataFinderExample {
  def main(args: Array[String]): Unit = {
    val jsonDataFinder: DataFinder[List[Person], Person] = new JsonDataFinder
    val csvDataFinder: DataFinder[List[Person], Person] = new CSVDataFinder

    System.out.println(s"Find a person with name Ivan in the json: ${jsonDataFinder.find(_.find(_.name == "Ivan"))}")
    System.out.println(s"Find a person with name James in the json: ${jsonDataFinder.find(_.find(_.name == "James"))}")

    System.out.println(s"Find a person with name Maria in the csv: ${csvDataFinder.find(_.find(_.name == "Maria"))}")
    System.out.println(s"Find a person with name Alice in the csv: ${csvDataFinder.find(_.find(_.name == "Alice"))}")
  }
}