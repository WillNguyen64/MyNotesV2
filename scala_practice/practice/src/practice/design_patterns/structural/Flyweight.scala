package practice.design_patterns.structural

import scala.collection.mutable
import scala.collection.mutable.ListBuffer

// Use flyweight pattern to minimize the memory usage by using a object that shares data with other similar objects
// e.g., word processor can store positions of similar characters instead of representing each char with all of the
// meta info (like font, size, color, etc.)

sealed abstract class Color
case object Red extends Color
case object Green extends Color
case object Blue extends Color
case object Yellow extends Color
case object Magenta extends Color

// Flyweight objects
class Circle(color: Color) {
  println(s"Creating a circle with $color color.")

  override def toString(): String = s"Circle($color)"
}

object Circle {
  val cache = mutable.Map.empty[Color, Circle]

  def apply(color: Color): Circle = cache.getOrElseUpdate(color, new Circle(color))

  def circlesCreated(): Int = cache.size
}

class Graphic {
  val items = ListBuffer.empty[(Int, Int, Double, Circle)]

  def addCircle(x: Int, y: Int, radius: Double, circle: Circle): Unit = {
    items += ((x, y, radius, circle))
  }

  def draw(): Unit = {
    items.foreach {
      case (x, y, radius, circle) =>
        println(s"Drawing a circle at ($x, $y) with radius $radius: $circle")
    }
  }
}

object FlyweightExample extends App {
  val graphic = new Graphic
  graphic.addCircle(1, 1, 1.0, Circle(Green))
  graphic.addCircle(1, 2, 1.0, Circle(Blue))
  graphic.addCircle(2, 1, 1.0, Circle(Blue))
  graphic.draw()
  println(s"Total number of circle objects created: ${Circle.circlesCreated()}")
}
