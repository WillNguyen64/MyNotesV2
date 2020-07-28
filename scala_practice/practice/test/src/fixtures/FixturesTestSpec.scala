package fixtures

import java.io.StringWriter
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.collection.mutable.ListBuffer

class FixturesTestSpec extends AnyFlatSpec with should.Matchers {

  /* ===== Scenario 1: Diff tests need diff fixtures ===== */

  def fixture =
    new {
      val builder = new StringBuilder("ScalaTest is ")
      val buffer = new ListBuffer[String]
    }

  "Testing with get-fixtures methods" should "be easy" in {
    val f = fixture
    f.builder.append("easy!")
    assert(f.builder.toString === "ScalaTest is easy!")
    f.buffer += "sweet"
  }

  it should "be fun" in {
    val f = fixture
    f.builder.append("fun!")
    assert(f.builder.toString === "ScalaTest is fun!")
    assert(f.buffer.isEmpty)
  }

  trait Builder {
    val builder = new StringBuilder("ScalaTest is ")
  }

  trait Buffer {
    val buffer = ListBuffer("ScalaTest", "is")
  }

  "Testing with fixture-context objects" should "be productive" in new Builder {
    // Only needs StringBuilder fixture
    builder.append("productive!")
    assert(builder.toString === "ScalaTest is productive!")
  }

  "Tests using fixture-context objects" should "be readable" in new Buffer {
    // Only needs ListBuffer[String] fixture
    buffer += ("readable!")
    assert(buffer === List("ScalaTest", "is", "readable!"))
  }

  it should "be clear and concise" in new Builder with Buffer {
    // Needs both StringBuilder and ListBuffer[String] fixtures
    builder.append("clear!")
    buffer += ("concise!")
    assert(builder.toString === "ScalaTest is clear!")
    assert(buffer === List("ScalaTest", "is", "concise!"))
  }

  // If you need to cleanup the fixtures at the end of a test, override ScalaTest's withFixture
  // lifecycle method

  /*
  override def withFixture(test: NoArgTest) = {
    // always call super.withFixture to run the test
    // use try-finally to ensure cleanup is run if exception is thrown
    // implementation can leverage the "test" parameter which contains TestData (testname and config map)
    super.withFixture(test) match {
      case failed: Failed =>
        info(s"Doing some cleanup action upon failure")
        failed
      case other => other
    }
  }
   */

  "This test using withFixture(NoArgTest)" should "succeed" in {
    assert(1 + 1 ===2 )
  }

  ignore should "fail" in {
    assert(1 + 1 === 3)
  }

  // Another way to cleanup the fixtures is to use the loan pattern - which allows diff tests needing diff fixtures that
  // require cleanup

  object DbServer {
    // Simulates a database server
    type Db = StringBuffer
    private val databases = new ConcurrentHashMap[String, Db]
    def createDb(name: String): Db = {
      val db = new StringBuffer
      databases.put(name, db)
      db
    }
    def removeDb(name: String): Unit = {
      databases.remove(name)
    }
  }

  import DbServer._

  def withDatabase(testCode: Db => Any): Unit = {
    val dbName = UUID.randomUUID.toString
    val db = createDb(dbName)
    try {
      db.append("ScalaTest is ")
      testCode(db)
    } finally removeDb(dbName)
  }

  def withFile(testCode: StringWriter => Any): Unit = {
    val writer = new StringWriter()
    try {
      writer.write("ScalaTest is ")
      testCode(writer)
    } finally writer.close()
  }

  "Testing with loan pattern" should "be productive" in withFile { writer =>
    // This test needs file fixture
    writer.write("productive!")
    writer.flush()
    assert(writer.toString.length === 24)
  }

  it should "be readable" in withDatabase { db =>
    // This test needs database fixture
    db.append("readable!")
    assert(db.toString === "ScalaTest is readable!")
  }

  it should "be clear and concise" in withDatabase { db =>
    withFile { writer =>
      db.append("clear!")
      writer.write("concise!")
      writer.flush()
      assert(db.toString === "ScalaTest is clear!")
      assert(writer.toString.length === 21)
    }
  }


}
