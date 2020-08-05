package fixtures

import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec

import scala.collection.mutable.ListBuffer

/* ===== Scenario 3c: Run fixture code before/after test using trait mixins
         and abort suite (not test) if fixture fails ===== */

// Another way to create stackable fixture traits

trait Builder3c extends BeforeAndAfterEach { this: TestSuite =>
  val builder = new StringBuilder

  override def beforeEach(): Unit = {
    builder.append("ScalaTest is ")
    // To be stackable, must call super.beforeEach
    super.beforeEach()
  }

  override def afterEach(): Unit = {
    try super.afterEach()
    finally builder.clear()
  }
}

trait Buffer3c extends BeforeAndAfterEach { this: TestSuite =>
  val buffer = new ListBuffer[String]

  override def afterEach(): Unit = {
    try super.afterEach()
    finally buffer.clear()
  }
}


class FixturesTestSpec3c extends AnyFlatSpec with Builder3c with Buffer3c {

  "Testing" should "be easy" in {
    builder.append("easy!")
    assert(builder.toString === "ScalaTest is easy!")
    assert(buffer.isEmpty)
    buffer += "sweet"
  }

  it should "be fun" in {
    builder.append("fun!")
    assert(builder.toString === "ScalaTest is fun!")
    assert(buffer.isEmpty)
    buffer += "clear"
  }

}
