package fixtures

import org.scalatest._
import org.scalatest.flatspec.AnyFlatSpec

import scala.collection.mutable.ListBuffer

/* ===== Scenario 3b: Run fixture code before/after, this does not abort suite if fixture fails  ===== */

// This code allows mixing in multiple fixtures (via traits) that are initialized before each test and
// cleaned up afterwards.  Can change the initialization order by changing order in which traits are mixed.

// The code in this doc https://www.scalatest.org/user_guide/sharing_fixtures is out-dated, refer to link below
// for the fix: https://stackoverflow.com/questions/28845975/better-way-to-compose-test-fixtures-in-scalatest

trait Builder3b extends TestSuiteMixin { this: TestSuite =>
  // TODO: what does the "this:Suite =>" part do?
  val builder = new StringBuilder

  abstract override def withFixture(test: NoArgTest): Outcome = {
    builder.append("ScalaTest is ")
    // To be stackable, must call super.withFixture
    try super.withFixture(test)
    finally builder.clear()
  }
}

trait Buffer3b extends TestSuiteMixin { this: TestSuite =>
  val buffer = new ListBuffer[String]

  abstract override def withFixture(test: NoArgTest): Outcome = {
    // To be stackable, must call super.withFixture
    try super.withFixture(test)
    finally buffer.clear()
  }
}


class FixturesTestSpec3b extends AnyFlatSpec with Builder3b with Buffer3b {

  /* ===== Scenario 3: Abort suite (not test) if fixture fails ===== */

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
