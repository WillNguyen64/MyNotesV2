package practice.design_patterns.structural

import scala.collection.mutable.ListBuffer

// Group of objects that should be treated the same way as a single one
// e.g., tree sturctures like filesystems

trait Node {
  def print(prefix: String): Unit
}

class Leaf(data: String) extends Node {
  override def print(prefix: String): Unit =
    println(s"${prefix}${data}")
}

class Tree extends Node {
  private val children = ListBuffer.empty[Node]

  override def print(prefix: String): Unit = {
    println(s"${prefix}(")
    children.foreach(_.print(s"${prefix}${prefix}"))
    println(s"${prefix})")
  }

  def add(child: Node): Unit = {
    children += child
  }

  def remove(): Unit = {
    if (children.nonEmpty) {
      children.remove(0)
    }
  }
}

object CompositeExample extends App {

  val tree = new Tree
  tree.add(new Leaf("leaf 1"))
  val subtree1 = new Tree
  subtree1.add(new Leaf("leaf 2"))
  subtree1.add(new Leaf("leaf 2"))
  val subtree2 = new Tree
  subtree2.add(new Leaf("leaf 3"))
  subtree2.add(new Leaf("leaf 4"))
  subtree1.add(subtree2)
  tree.add(subtree1)
  val subtree3 = new Tree
  val subtree4 = new Tree
  subtree4.add(new Leaf("leaf 5"))
  subtree4.add(new Leaf("leaf 6"))
  subtree3.add(subtree4)
  tree.add(subtree3)
  tree.print("-")
}
