package practice.design_patterns.scala_patterns

import java.sql.{Connection, PreparedStatement, ResultSet}

import org.h2.jdbcx.JdbcConnectionPool

// Cake pattern - implements DI without using third-party library like Spring or Guice

// It is possible to use monads with the cake pattern, using Reader monad in the Scalaz library.
// We can build complex operations using map, flatMap and for comprehensions. Injecting dependencies
// are deferred until the last moment, where we can simplify call apply on a reader with actual component(s)
// it needs.

// Example - An application that can read from a database, data about people, classes, and who has signed
// up to which classes

// Data model
case class Class(id: Int, name: String)
case class Person(id: Int, name: String, age: Int)

// Database
trait DatabaseService {
  val dbDriver: String
  val connectionString: String
  val username: String
  val password: String

  val ds = {
    JdbcConnectionPool.create(connectionString, username, password)
  }

  def getConnection: Connection = ds.getConnection
}

// Database component mix-in
trait DatabaseComponent {

  val databaseService: DatabaseService

  class H2DatabaseService(val connectionString: String, val username: String, val password: String) extends DatabaseService {
    val dbDriver = "org.h2.Driver"
  }
}

// Migration component to create a DB and populate it
trait MigrationComponent {
  // self type annotation - if you mix in MigrationComponent into a class, you also need to mix in DatabaseComponent
  this: DatabaseComponent =>

  val migrationService: MigrationService

  class MigrationService() {
    def runMigrations(): Unit = {
      val connection = databaseService.getConnection
      try {
        // create the database
        createPeopleTable(connection)
        createClassesTable(connection)
        createPeopleToClassesTable(connection)
        // populate
        insertPeople(
          connection,
          List(
            Person(1, "Ivan", 26),
            Person(2, "Maria", 25),
            Person(3, "John", 27)
          )
        )
        insertClasses(
          connection,
          List(
            Class(1, "Scala Design Patterns"),
            Class(2, "Java Programming"),
            Class(3, "Mountain Biking")
          )
        )
        signPeopleToClasses(
          connection,
          List(
            (1, 1),
            (1, 2),
            (1, 3),
            (2, 1),
            (3, 1),
            (3, 3)
          )
        )
      } finally {
        connection.close()
      }
    }

    private def createPeopleTable(connection: Connection): Unit = {
      val statement = connection.prepareStatement(
        """
          |CREATE TABLE people(
          | id INT PRIMARY KEY,
          | name VARCHAR(255) NOT NULL,
          | age INT NOT NULL
          |)
        """.stripMargin
      )
      try {
        statement.executeUpdate()
      } finally {
        statement.close()
      }
    }

    private def createClassesTable(connection: Connection): Unit = {
      val statement = connection.prepareStatement(
        """
          |CREATE TABLE classes(
          | id INT PRIMARY KEY,
          | name VARCHAR(255) NOT NULL,
          |)
        """.stripMargin
      )
      try {
        statement.executeUpdate()
      } finally {
        statement.close()
      }
    }

    private def createPeopleToClassesTable(connection: Connection): Unit = {
      val statement = connection.prepareStatement(
        """
          |CREATE TABLE people_classes(
          | person_id INT NOT NULL,
          | class_id INT NOT NULL,
          | PRIMARY KEY(person_id, class_id),
          | FOREIGN KEY(person_id) REFERENCES people(id) ON DELETE CASCADE ON UPDATE CASCADE,
          | FOREIGN KEY(class_id) REFERENCES classes(id) ON DELETE CASCADE ON UPDATE CASCADE
          |)
        """.stripMargin
      )
      try {
        statement.executeUpdate()
      } finally {
        statement.close()
      }
    }

    private def insertPeople(connection: Connection, people: List[Person]): Unit = {
      val statement = connection.prepareStatement(
        "INSERT INTO people(id, name, age) VALUES (?, ?, ?)"
      )
      try {
        people.foreach {
          case person =>
            statement.setInt(1, person.id)
            statement.setString(2, person.name)
            statement.setInt(3, person.age)
            statement.addBatch()
        }
        statement.executeBatch()
      } finally {
        statement.close()
      }
    }

    private def insertClasses(connection: Connection, classes: List[Class]): Unit = {
      val statement = connection.prepareStatement(
        "INSERT INTO classes(id, name) VALUES (?, ?)"
      )
      try {
        classes.foreach{
          case cls =>
            statement.setInt(1, cls.id)
            statement.setString(2, cls.name)
            statement.addBatch()
        }
        statement.executeBatch()
      } finally {
        statement.close()
      }
    }

    private def signPeopleToClasses(connection: Connection, peopleToClasses: List[(Int, Int)]): Unit = {
      val statement = connection.prepareStatement(
        "INSERT INTO people_classes(person_id, class_id) VALUES (?, ?)"
      )
      try {
        peopleToClasses.foreach {
          case (personId, classId) =>
            statement.setInt(1, personId)
            statement.setInt(2, classId)
            statement.addBatch()
        }
        statement.executeBatch()
      } finally {
        statement.close()
      }
    }
  }
}

// DAO component - data access layer

trait DaoComponent {
  // self type annotation
  this: DatabaseComponent =>

  val dao: Dao

  class Dao() {
    def getPeople: List[Person] = {
      val connection = databaseService.getConnection
      try {
        executeSelect(
          connection.prepareStatement("SELECT id, name, age FROM people")
        ) {
          rs =>
            readResultSet(rs) {
              row =>
                Person(row.getInt(1), row.getString(2), row.getInt(3))
            }
        }
      } finally {
        connection.close()
      }
    }

    def getClasses: List[Class] = {
      val connection = databaseService.getConnection
      try {
        executeSelect(
          connection.prepareStatement("SELECT id, name FROM classes")
        ) {
          rs =>
            readResultSet(rs) {
              row =>
                Class(row.getInt(1), row.getString(2))
            }
        }
      } finally {
        connection.close()
      }
    }

    def getPeopleInClass(className: String): List[Person] = {
      val connection = databaseService.getConnection
      try {
        val statement = connection.prepareStatement(
          """
            |SELECT p.id, p.name, p.age
            |FROM people p
            | JOIN people_classes pc ON p.id = pc.person_id
            | JOIN classes c ON c.id = pc.class_id
            |WHERE c.name = ?
          """.stripMargin
        )
        statement.setString(1, className)
        executeSelect(
          statement
        ) {
          rs =>
            readResultSet(rs) {
              row =>
                Person(row.getInt(1), row.getString(2), row.getInt(3))
            }
        }
      } finally {
        connection.close()
      }

    }

    private def executeSelect[T](preparedStatement: PreparedStatement)(f: (ResultSet) => List[T]): List[T] =
      try {
        f(preparedStatement.executeQuery())
      } finally {
        preparedStatement.close()
      }

    private def readResultSet[T](rs: ResultSet)(f: ResultSet => T): List[T] =
      Iterator.continually((rs.next(), rs)).takeWhile(_._1).map {
        case (_, row) =>
          f(rs)
      }.toList

  }
}

// User service

trait UserComponent {
  this: DaoComponent =>

  val userService: UserService
  class UserService {
    def getAverageAgeOfUsersInClass(className: String): Double = {
      val (ageSum, peopleCount) = dao.getPeopleInClass(className).foldLeft((0, 0)) {
        case ((sum, count), person) =>
          (sum + person.age, count + 1)
      }
      if (peopleCount != 0) {
        ageSum.toDouble / peopleCount.toDouble
      } else {
        0.0
      }
    }
  }
}

// Component registry class, with all of the components mixed-in

object ApplicationComponentRegistry
  extends UserComponent
    with DaoComponent
    with DatabaseComponent
    with MigrationComponent {
  override val dao: ApplicationComponentRegistry.Dao = new Dao
  override val databaseService: DatabaseService = new H2DatabaseService("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "", "")
  override val migrationService: ApplicationComponentRegistry.MigrationService = new MigrationService
  override val userService: ApplicationComponentRegistry.UserService = new UserService
}

// Main application

object Application {
  import ApplicationComponentRegistry._
  def main(args: Array[String]): Unit = {
    migrationService.runMigrations()
    System.out.println(dao.getPeople)
    System.out.println(dao.getClasses)
    System.out.println(dao.getPeopleInClass("Scala Design Patterns"))
    System.out.println(dao.getPeopleInClass("Mountain Biking"))
    System.out.println(s"Average age of everyone in Scala Design Patterns: ${userService.getAverageAgeOfUsersInClass("Scala Design Patterns")}")
  }
}

// To test this:

/*
trait TestEnvironment
  extends UserComponent
    with DaoComponent
    with DatabaseComponent
    with MigrationComponent
    with MockitoSugar {
  override val dao: ApplicationComponentRegistry.Dao = mock[Dao]
  override val databaseService: DatabaseService = mock[DatabaseService]
  override val migrationService: ApplicationComponentRegistry.MigrationService = mock[MigrationService]
  override val userService: ApplicationComponentRegistry.UserService = mock[UserService]
}

class UserComponentTest extends FlatSpec with Matchers with MockitoSugar with TestEnvironment {
  val className = "A"
  val emptyClassName = "B"
  val people = List(
    Person(1, "a", 10),
    Person(2, "b", 15),
    Person(3, "c", 20)
  )
  override val userService = new UserService

  when(dao.getPeopleInClass(className)).thenReturn(people)
  when(dao.getPeopleInClass(emptyClassName)).thenReturn(List())

  "getAverageAgeOfUsersInClass" should "properly calculate the average of all ages." in {
    userService.getAverageAgeOfUsersInClass(className) should equal(15.0)
  }

  it should "properly handle an empty result." in {
    userService.getAverageAgeOfUsersInClass(emptyClassName) should equal(0.0)
  }
}
*/
