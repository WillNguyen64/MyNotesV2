package practice.design_patterns.functional

import java.io.{File, PrintWriter}

import scala.io.Source

// Monad - a container or custom-defined class which defines a map, unit and flatMap method. It also
// obeys some monday laws (identity law, unit law, composition). Monads also have a zero element which
// represents some kind of emptiness and follows some extra laws (zero identity, reverse zero, commutativity).

// Examples of built-in Scala Monads: List, Array, Option, etc.

// Monads allow you to create complex computations using map, flatMap and for comprehensions. It builds up computations
// and only executes until the last moment.
// e.g.,
//    val l1 = List(1, 2, 3, 4)
//    val l2 = List(5, 6, 7, 8)
//    val result = for {
//      x <- l1
//      y <- l2
//    } yield x * y
//
// The above is the same as:
//    val result = l1.flatMap(i => l2.map(_ * i))

// Monads can hide details about state, exception handling, specific operations, etc. They can be used to
// implement some of the earlier patterns to better handle states, rollbacks, etc.

// Example - Use Monads to chain multiple IO operations. It reads a file, performs some text transformation and
// writes the result to a file. Each IO operation performed will cause the state to change.
// The state consists of an integer that is incremented after each IO operation.

// Simple state that returns the next stage when moving between different IO operations.
sealed trait State {
  def next: State
}

// Abstract IO class that reads a file, performs some text transformation and writes the result to a file.
// The user extends this class and defines an IOAction that was composed from multiple IOActions. We can
// think of the IOAction as a graph of computations to be executed.
abstract class FileIO {

  // Hide the state so that users can't create a state
  private class FileIOState(id: Int) extends State {
    override def next: State = new FileIOState(id + 1)
  }

  // Runs the IOAction, takes an input and output file as params
  def run(args: Array[String]): Unit = {
    val action = runIO(args(0), args(1))
    // Run the action with the initial state
    action(new FileIOState(0))
  }

  def runIO(readPath: String, writePath: String): IOAction[_]
}

// Monad representing an IO action
sealed abstract class IOAction[T] extends (State => (State, T)) {
  def unit[Y](value: Y): IOAction[Y] = IOAction(value)

  def flatMap[Y](f: T => IOAction[Y]): IOAction[Y] = {
    val self = this
    new IOAction[Y] {
      override def apply(state: State): (State, Y) = {
        val (state2, res) = self(state)
        val action2 = f(res)
        action2(state2)
      }
    }
  }

  def map[Y](f: T => Y): IOAction[Y] =
    flatMap(i => unit(f(i)))
}

// Factory to create Monads
object IOAction {
  def apply[T](result: => T): IOAction[T] =
    new SimpleAction[T](result)

  def unit[T](value: T): IOAction[T] = new EmptyAction[T](value)

  private class SimpleAction[T](result: => T) extends IOAction[T] {
    override def apply(state: State): (State, T) =
      (state.next, result)
  }

  private class EmptyAction[T](value: T) extends IOAction[T] {
    override def apply(state: State): (State, T) =
      (state, value)
  }
}

// IO operations, these are concrete implementations of Monads using the above factory
object IOOperations {
  def readFile(path: String) =
    IOAction(Source.fromFile(path).getLines())

  def writeFile(path: String, lines: Iterator[String]) =
    IOAction({
      val file = new File(path)
      printToFile(file) { p => lines.foreach(p.println)}
    })

  private def printToFile(file: File)(writeOp: PrintWriter => Unit): Unit = {
    val writer = new PrintWriter(file)
    try {
      writeOp(writer)
    } finally {
      writer.close()
    }
  }
}

// Driver program
object FileIOExample extends FileIO {
  import IOOperations._

  def main(args: Array[String]): Unit = {
    run(args)
  }

  override def runIO(readPath: String, writePath: String): IOAction[_] =
    for {
      lines <- readFile(readPath)
      _ <- writeFile(writePath, lines.map(_.toUpperCase))
    } yield ()
}
