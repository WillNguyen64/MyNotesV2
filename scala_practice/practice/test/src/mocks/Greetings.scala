package mocks

object Greetings {
  trait Formatter { def format(s: String): String }
  object EnglishFormatter { def format(s: String): String = s"Hello $s" }
  object GermanFormatter { def format(s: String): String = s"Hallo $s" }
  object JapaneseFormatter { def format(s: String): String =  s"こんにちは $s" }

  def sayHello(name: String, formatter: Formatter): Unit = {
    println(formatter.format(name))
  }
}