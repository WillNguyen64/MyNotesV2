package practice.design_patterns.creational

// Factory Method
// Define logic in the abstract class, defer object creation to subclasses

trait SimpleConnection {
  def executeQuery(query: String): Unit
}
class SimpleMysqlConnection extends SimpleConnection {
  override def executeQuery(query: String): Unit = println(s"Executing the query '$query' the MySQL way.")
}
class SimplePgSqlConnection extends SimpleConnection {
  override def executeQuery(query: String): Unit =  println(s"Executing the query '$query' the PgSQL way.")
}

abstract class DatabaseClient {
  def executeQuery(query: String): Unit = {
    val connection = connect()
    connection.executeQuery(query)
  }

  protected def connect(): SimpleConnection
}

class MysqlClient extends DatabaseClient {
  override protected def connect(): SimpleConnection = new SimpleMysqlConnection
}

class PgSqlClient extends DatabaseClient {
  override protected def connect(): SimpleConnection = new SimplePgSqlConnection
}

object FactoryMethodExample extends App {
  val clientMySql: DatabaseClient = new MysqlClient
  clientMySql.executeQuery("SELECT * FROM users")
}
