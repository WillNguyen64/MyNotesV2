package mocks

import mocks.Greetings.Formatter
import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.language.postfixOps


class ExampleSpec extends AnyFlatSpec with MockFactory with Matchers {

  "ScalaMock" should "verify if a function is called correctly" in {

    // Mock function takes an int, returns a string
    val m = mockFunction[Int, String]
    m.expects(42).returning("Forty-Two")
    assert(m(42) == "Forty-Two")
  }

  it should "verify if an object's method is called correctly" in {
    val m = mock[Formatter]

    // Expectations-First mocking style (using mocks)
    (m.format _).expects("Mr Bond").returning("Ah, Mr Bond. I've been expecting you").once

    Greetings.sayHello("Mr Bond", m)
  }

  it should "defer verifying a method is called correctly" in {

    // Record-then-Verify mocking style (using stubs)
    // Similar to Mockito style
    val m = stub[Formatter]
    val bond = "Mr Bond"

    (m.format _).when(bond).returns("Ah, Mr Bond. I've been expected you.")

    Greetings.sayHello(bond, m)
    (m.format _).verify(bond).once()

  }

  it should "throw an exception in a mock" in {
    val m = mock[Formatter]
    (m.format _).expects(*).throwing(new NullPointerException).anyNumberOfTimes()

    intercept[NullPointerException] {
      Greetings.sayHello("Ezra", m)
    }
  }

  it should "response to a parameter with call handlers" in {
    val m = mock[Formatter]
    (m.format _).expects(*).onCall { s: String => s"G'day $s" }.twice()

    Greetings.sayHello("Wendy", m)
    Greetings.sayHello("Gray", m)
  }

  it should "verify parameters dynamically" in {
    val teamNatsu = Set("Natsu", "Lucy", "Happy", "Erza", "Gray", "Wendy", "Carla")
    val m = mock[Formatter]

    def assertTeamNatsu(s: String): Unit = {
      assert(teamNatsu.contains(s))
    }

    // Verify before the test
    (m.format _).expects(argAssert(assertTeamNatsu _)).onCall { s: String => s"Yo $s" }.once()

    // Verify after the test
    (m.format _).expects(where { s: String => teamNatsu contains(s) }).onCall { s: String => s"Yo $s" }.twice()

    Greetings.sayHello("Carla", m)
    Greetings.sayHello("Happy", m)
    Greetings.sayHello("Lucy", m)
  }

  it should "verify calls in any order" in {
    val m = mock[Formatter]

    inAnyOrder {
      (m.format _).expects("Mr Bond").returns("Ah, Mr Bond. I've been expecting you")
      (m.format _).expects("Natsu").returns("Not now Natsu!").atLeastTwice()
    }

    Greetings.sayHello("Natsu", m)
    Greetings.sayHello("Natsu", m)
    Greetings.sayHello("Mr Bond", m)
    Greetings.sayHello("Natsu", m)
  }

  it should "verify calls in order" in {
    val m = mock[Formatter]

    inSequence {
      (m.format _).expects("Mr Bond").returns("Ah, Mr Bond. I've been expecting you")
      (m.format _).expects("Natsu").returns("Not now Natsu!")
    }

    Greetings.sayHello("Mr Bond", m)
    Greetings.sayHello("Natsu", m)
  }

  it should "match arguments using wildcards" in {
    val mockedFunction = mockFunction[String, Any, Unit]
    mockedFunction expects("foo", *) atLeastOnce

    mockedFunction("foo", 42)
    mockedFunction("foo", "abc")
  }

  it should "match arguments using epsilon matching" in {
    val mockedFunction = mockFunction[Double, Unit]
    mockedFunction expects(~42.0) atLeastOnce

    mockedFunction(42.0)
    mockedFunction(42.0001)
    mockedFunction(41.9999)
  }

  it should "match arguments using predicate matching" in {
    val mockedFunction = mockFunction[Double, Double, Unit]
    mockedFunction expects where { _ < _ }

    mockedFunction(1.0, 2.0)
  }


}