package fixtures

import java.io.StringWriter

import org.scalatest.Outcome
import org.scalatest.flatspec.FixtureAnyFlatSpec
import org.scalatest.matchers.should

import scala.collection.mutable.ListBuffer

/* ===== Scenario 2: Most or all tests need same fixture ===== */

class FixturesTestSpec2 extends FixtureAnyFlatSpec with should.Matchers {

  case class FixtureParam(writer: StringWriter, buffer: ListBuffer[String])

  override protected def withFixture(test: OneArgTest): Outcome = {
    val writer = new StringWriter()
    val buffer = new ListBuffer[String]
    val theFixture = FixtureParam(writer, buffer)

    try {
      writer.write("ScalaTest is ")
      withFixture(test.toNoArgTest(theFixture))
    } finally writer.close()
  }

  "Testing using withFixture(OneArgTest)" should "be easy" in { f =>
    f.writer.write("easy!")
    f.writer.flush()
    assert(f.writer.toString.length === 18)
  }

  it should "be fun" in { f =>
    f.writer.write("fun!")
    f.writer.flush()
    assert(f.writer.toString.length === 17)
  }

}
