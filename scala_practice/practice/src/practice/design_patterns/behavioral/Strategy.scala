package practice.design_patterns.behavioral

import practice.design_patterns.creational.Person

class Application[T](strategy: (String) => List[T]) {
  def write(file: String): Unit = {
    println(s"Got the following data ${strategy(file)}")
  }
}

object StrategyFactory {
  def apply(filename: String): (String) => List[Person] =
    filename match {
      case f if f.endsWith(".json") => parseJson
      case f if f.endsWith(".csv") => parseCsv
      case f => throw new RuntimeException(s"Unknown format: $f")
    }

    def parseJson(file: String): List[Person] = {
      // Implementation omitted
      println(s"Parsing JSON file: $file")
      List.empty
    }

    def parseCsv(file: String): List[Person] = {
      // Implementation omitted
      println(s"Parsing CSV file: $file")
      List.empty
    }
}

object StrategyExample extends App {
  val app = new Application[Person](
    StrategyFactory("people.csv")
  )
  app.write("people.csv")
}
