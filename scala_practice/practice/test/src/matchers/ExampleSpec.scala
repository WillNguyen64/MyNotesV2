package matchers

import java.io.File

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalactic.StringNormalizations._

class ExampleSpecs extends AnyFlatSpec with Matchers {

  "Matchers" should "check for equality" in {
    val result = 3
    result should equal (3) // can customize equality
    result should === (3) // can customize equality, enforce type constraints (see TypeCheckedTripeEquals)
    result should !== (4)
    result should be (3) // cannot customize equality, so fastest to compile
    result shouldEqual 3 // can customize equality, no parentheses required
    result shouldBe 3 // cannot customize equality, so fastest to compile, no parentheses required

    // Comparing arrays
    Array(1, 2) should equal (Array(1, 2))
    val myArray = Array(1, 2)
    val myArray2 = myArray
    myArray should be theSameInstanceAs myArray2

    // Customize meaning of equality
    "Hi" should equal ("hi") (after being lowerCased and trimmed)
  }

  "Matchers" should "check for length and size" in {
    val result = List(1,2,3)
    result should have length 3
    result should have size 3
  }

  "Matchers" should "check for substring patterns" in {
    val string = "Hello world"
    string should startWith ("Hello")
    string should endWith ("world")
    string should include ("ello")

    string should startWith regex ("Hel*o")
    string should endWith regex ("wo.ld")
    string should include regex ("wo.ld")

    string should fullyMatch regex """Hel*o wo.ld"""

    "abbccxxx" should startWith regex ("a(b*)(c*)" withGroups("bb", "cc"))
  }

  "Matchers" should "check for greater and less than" in {
    val one  = 1
    one should be < 7
    one should be > 0
    one should be <= 7
    one should be >= 0
  }

  "Matchers" should "check for boolean properties with be" in {
    val result = new File("/tmp")
    result should be a 'directory
  }

  "Matchers" should "check two references refer to exact same object" in {
    val result = "abc"
    val result2 = result
    result should be theSameInstanceAs result2
  }

  "Matchers" should "check an object is an instance of a class or trait" in {
    val result = 1
    result shouldBe an [Int]
    result should not be a [String]

    val result2 = List(1,2,3)
    result2 shouldBe a [List[_]]
  }

  "Matchers" should "check for numbers in a range" in {
    val result = 7.0
    result should equal (6.9 +- 0.2)
  }

  "Matchers" should "work with containers" in {
    val emptyList = List.empty
    val list = List("one", "two", "three")
    val list2 = List("one", "two", "one", "one", "three")

      emptyList shouldBe empty
    list should contain ("three")
    list should contain oneOf ("three", "four", "five")
    list should contain noneOf ("four", "five", "six")
    list should contain atLeastOneOf ("one", "two", "three")
    list should contain atMostOneOf ("three", "four", "five")
    list should contain allOf ("one", "two", "three")
    list2 should contain only ("one", "two", "three")
    list2 should contain theSameElementsAs List("one", "one", "one", "two", "three")
    list2 should contain theSameElementsInOrderAs  List("one", "two", "one", "one", "three")

    List(1, 2, 2, 3, 3, 3) should contain inOrderOnly (1, 2, 3)
    List(0, 1, 2, 99, 3, 3, 5) should contain inOrder (1, 2, 3)
    List(1, 2, 3) shouldBe sorted
  }

  "Matchers" should "check arbitrary properties" in {
    case class Book(title: String, author: List[String], pubYear: Int)
    val book = Book("Programming in Scala", List("Odersky", "Spoon", "Venners"), 2008)
    book should have (
     'title ("Programming in Scala"),
     'author (List("Odersky", "Spoon", "Venners")),
     'pubYear (2008)
    )
  }
}
