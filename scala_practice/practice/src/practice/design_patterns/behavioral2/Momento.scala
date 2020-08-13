package practice.design_patterns.behavioral2

// Momento - allows an undo action to restore object to a previous state
// This pattern has 3 components:
//   - Originator: object whose state we want to restore
//   - Caretaker: object that triggers changes to originator object, uses memento objects for rollback
//   - Memento: carries actual state of originator, can be used to restore to one of the previous stages. It is
//     only handled by the originator. Caretaker and other classes just store it.

// Be careful:
//   - Should save state in value objects (immutable)
//   - Should limit the number of changes saved

import scala.collection.mutable

trait Memento[T] {
  protected val state: T
  def getState(): T = state
}

trait Caretaker[T] {
  val states: mutable.Stack[Memento[T]] = mutable.Stack[Memento[T]]()
}

trait Originator[T] {
  def createMemento: Memento[T]
  def restore(memento: Memento[T])
}

class TextEditor extends Originator[String] {
  private var builder: StringBuilder = new StringBuilder

  def append(text: String): Unit = {
    builder.append(text)
  }

  def delete(): Unit = {
    if (builder.nonEmpty) {
      builder.deleteCharAt(builder.length - 1)
    }
  }

  override def createMemento: Memento[String] =
    new TextEditorMemento(builder.toString)

  override def restore(memento: Memento[String]): Unit =
    this.builder = new StringBuilder(memento.getState())

  def text(): String = builder.toString

  private class TextEditorMemento(val state: String) extends Memento[String]
}

class TextEditorManipulator extends Caretaker[String] {
  private val textEditor = new TextEditor

  def save(): Unit = {
    states.push(textEditor.createMemento)
  }

  def undo(): Unit = {
    if (states.nonEmpty) {
      textEditor.restore(states.pop())
    }
  }

  def append(text: String): Unit = {
    save()
    textEditor.append(text)
  }

  def delete(): Unit = {
    save()
    textEditor.delete()
  }

  def readText(): String = textEditor.text()
}

object TextEditorExample {
  def main(args: Array[String]): Unit = {
    val textEditorManipulator = new TextEditorManipulator
    textEditorManipulator.append("This is a chapter about memento.")
    System.out.println(s"The text is: '${textEditorManipulator.readText()}'")
    // delete 2 characters
    System.out.println("Deleting 2 characters...")
    textEditorManipulator.delete()
    textEditorManipulator.delete()
    // see the text
    System.out.println(s"The text is: '${textEditorManipulator.readText()}'")
    // undo
    System.out.println("Undoing...")
    textEditorManipulator.undo()
    System.out.println(s"The text is: '${textEditorManipulator.readText()}'")
    // undo again
    System.out.println("Undoing...")
    textEditorManipulator.undo()
    System.out.println(s"The text is: '${textEditorManipulator.readText()}'")
  }
}