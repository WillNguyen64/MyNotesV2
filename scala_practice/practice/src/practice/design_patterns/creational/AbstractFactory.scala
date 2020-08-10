package practice.design_patterns.creational

// Abstract factory
// Create factories to create a family of objects (e.g., DB connections, UI elements, etc.)
// Use object composition to pass factory object to the class that uses it

trait DatabaseConnectorFactory {
  def connect(): SimpleConnection
}

class MySqlFactory extends DatabaseConnectorFactory {
  override def connect(): SimpleConnection = new SimpleMysqlConnection
}

class PgSqlFactory extends DatabaseConnectorFactory {
  override def connect(): SimpleConnection = new SimplePgSqlConnection
}

class DatabaseClientWithFactory(connectionFactory: DatabaseConnectorFactory) {
  def executeQuery(query: String): Unit = {
    val connection = connectionFactory.connect()
    connection.executeQuery(query)
  }
}

object AbstractFactoryExample extends App {
  val clientMySql: DatabaseClientWithFactory = new DatabaseClientWithFactory(new MySqlFactory)
  clientMySql.executeQuery("SELECT * FROM users")
}
