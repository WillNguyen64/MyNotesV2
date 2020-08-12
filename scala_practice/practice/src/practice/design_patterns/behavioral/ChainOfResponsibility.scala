package practice.design_patterns.behavioral

// Chain of Responsibility - Decouples the sender of a request from its receiver by giving multiple objects the
// change to handle the request. This pattern is good for creating pipelines and handling events.

// Example: Create an ATM that dispenses money in different bills
// This example implements chain of responsibility using partial functions with andThen

// Note we can also implement this pattern using the Akka library, which can be used to do reactive
// programming in Scala

case class Money(amount: Int)

trait Dispenser {
  def dispense(dispenserAmount: Int): PartialFunction[Money, Money] = {
    case Money(amount) if amount >= dispenserAmount =>
      val notes = amount / dispenserAmount
      val left = amount % dispenserAmount
      println(s"Dispensing $notes note/s of $dispenserAmount")
      Money(left)
    case m @ Money(_) => m
  }
}

class ATM extends Dispenser {
  // Chain together multiple dispensers (e.g., $50, $20, etc..)
  val dispenser =
    dispense(50)
    .andThen(dispense(20))
    .andThen(dispense(10))
    .andThen(dispense(5))

  def requestMoney(money: Money): Unit = {
    if (money.amount % 5 != 0) {
      System.err.println("The smallest nominal is 5 and we cannot satisfy your request.")
    } else {
      dispenser(money)
    }
  }
}

object ATMExample extends App {
  val atm = new ATM
  atm.requestMoney(Money(125))
}
