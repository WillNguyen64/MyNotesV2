package shared_tests

import org.scalatest.flatspec.AnyFlatSpec

// Contains a set of shared tests to test stacks
trait StackBehaviors { this: AnyFlatSpec =>

  // A behavior function that runs tests on a non-empty stack fixture
  def nonEmptyStack(newStack: => Stack[Int], lastItemAdded: Int): Unit = {
    it should "be non-empty" in {
      assert(!newStack.empty)
    }

    it should "return the top item on peek" in {
      assert(newStack.peek === lastItemAdded)
    }

    it should "not remove the top item on peek" in {
      val stack = newStack
      val size = stack.size
      assert(stack.peek === lastItemAdded)
      assert(stack.size === size)
    }

    it should "remove the top item on pop" in {
      val stack = newStack
      val size = stack.size
      assert(stack.pop === lastItemAdded)
      assert(stack.size === size - 1)
    }
  }

  // A behavior function that runs tests on a non-full stack fixture
  def nonFullStack(newStack: => Stack[Int]): Unit = {
    it should "not be full" in {
      assert(!newStack.full)
    }

    it should "add to the top on push" in {
      val stack = newStack
      val size = stack.size
      stack.push(7)
      assert(stack.size === size + 1)
      assert(stack.peek === 7)
    }
  }

}
