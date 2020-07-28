
import org.scalatest.Tag
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should
import org.scalatest.tagobjects.Slow

import scala.collection.mutable

class BasicTestSpec extends AnyFlatSpec with should.Matchers {

  "A Stack" should "pop values in last-in-first-out order" in {
    val stack = new mutable.Stack[Int]

    // Verify with assert
    stack.push(1)
    stack.push(2)
    assert(stack.pop() == 2)
    assert(stack.pop() == 1)
    assert(stack.isInstanceOf[mutable.Stack[Int]])
    assert(stack.isEmpty, "Stack must be empty after all items are popped")
    // Advanced: other useful traits
    //   - there's a === operator to customize Euqality,
    //   - perform equality checks with Tolerance
    //   - enforce type constraits

    // Verify with assertResult
    stack.push(1)
    stack.push(2)
    assertResult(2) {
      stack.pop()
    }
    assertResult(1) {
      stack.pop()
    }

    // Verify with matchers DSL
    stack.push(1)
    stack.push(2)
    stack.pop() should be (2)
    stack.pop() should be (1)
  }

  it should "throw NoSuchElementException if an empty stack is popped" in {
    val emptyStack = new mutable.Stack[Int]

    // Can cancel a test if a required resource is not available at test time
    assume(emptyStack.isEmpty, "The stack must be empty")

    a [NoSuchElementException] should be thrownBy {
      emptyStack.pop()
    }
    // Verify with assertThrows
    assertThrows[NoSuchElementException] {
      emptyStack.pop()
    }

    // prepend a clue to the detailed exception message
    withClue("An empty stack should return an exception when popped") {
      assertThrows[NoSuchElementException] {
        emptyStack.pop()
      }
    }

    // Intercept the assertThrows for further inspection
    val caught = intercept[NoSuchElementException] {
      emptyStack.pop()
    }
    assert(caught.getMessage.indexOf("empty collection") != -1)
  }

  // Use "ignore" to temporarily disable tests
  ignore should "do something successfully" in {
    succeed
  }

  // Can arbitrarily define test categories
  // TODO: how do you filter tests using mill?
  object DbTest extends Tag("com.mycompany.tags.DbTest")
  it should "allow multiple elements to be pushed onto it" taggedAs(Slow, DbTest) in {
    val stack = new mutable.Stack[Int]
    stack.pushAll(Seq(1,2,3))
  }

}

