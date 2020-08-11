package practice.design_patterns.structural

class FinalLogger {
  def log(message: String, severity: String): Unit = {
    println(s"${severity.toUpperCase}: $message")
  }
}

trait Log {
  def info(message: String)
  def debug(message: String)
  def warning(message: String)
  def error(message: String)
}

object AdapterExample extends App {
  val logger: Log = new FinalLogger
  logger.info("This is an info message")
  logger.debug("Debug something here.")
  logger.info("Bye!")
}
