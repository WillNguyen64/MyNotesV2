package practice.design_patterns.behavioral

import scala.collection.mutable.ListBuffer

// Command pattern is used to pass info to other objects about how to run some action
// Useful for supporting undo actions, implementing parallel processing, optimizing code by deferring, possibly
// avoiding code execution.

// It consists of:
//   - Command: encapsulates the commands via an interface (i.e., receiver object, method, parameters)
//   - Receiver: knows how to execute the command
//   - Invoker: runs command via interface
//   - Client: use invoker to run commands
//

case class Robot() {
  def cleanUp(): Unit = println("Cleaning up.")
  def pourJuice(): Unit = println("Pouring juice.")
  def makeSandwich(): Unit = println("Making a sandwich.")
}

trait RobotCommand {
  def execute(): Unit
}

case class MakeSandwichCommand(robot: Robot) extends RobotCommand {
  override def execute(): Unit = robot.makeSandwich()
}

case class PourJuiceCommand(robot: Robot) extends RobotCommand {
  override def execute(): Unit = robot.pourJuice()
}

case class CleanUpCommand(robot: Robot) extends RobotCommand {
  override def execute(): Unit = robot.cleanUp()
}

class RobotController {
  val history = ListBuffer[() => Unit]()

  def issueCommand(command: => Unit): Unit = {
    //command _ +=: history
    // The "command _" means to convert the by-name parameter to a function
    history.prepend(command _)
    command
  }

  def showHistory(): Unit = {
    // This prints out the function, how do we get the command?
    history.foreach(println)
  }

}

object RobotExample extends App {
  val robot = Robot()
  val robotController = new RobotController
  robotController.issueCommand(MakeSandwichCommand(robot).execute())
  robotController.issueCommand(PourJuiceCommand(robot).execute())
  println("I'm eating and having some juice.")
  robotController.issueCommand(CleanUpCommand(robot).execute())
  println("Here is what I asked my robot to do:")
  robotController.showHistory()
}
