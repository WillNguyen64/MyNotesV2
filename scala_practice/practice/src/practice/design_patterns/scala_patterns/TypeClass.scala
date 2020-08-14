package practice.design_patterns.scala_patterns

// type class pattern - define some behavior in terms of operations that a type must support in order
// to be considered a member of the type class

// Uses 'implicitly' keyword which uses context bounds to implicitly converts one type to the type
// required by the operation

import Math.round

trait Number[T] {
  def plus(x: T, y: T): T
  def minus(x: T, y: T): T
  def divide(x: T, y: Int): T
  def multiply(x: T, y: T): T
  def sqrt(x: T): T
}

object Number {
  implicit object DoubleNumber extends Number[Double] {
    override def plus(x: Double, y: Double): Double = x + y
    override def divide(x: Double, y: Int): Double = x / y
    override def multiply(x: Double, y: Double): Double = x * y
    override def minus(x: Double, y: Double): Double = x - y
    override def sqrt(x: Double): Double = Math.sqrt(x)
  }

  implicit object IntNumber extends Number[Int] {
    override def plus(x: Int, y: Int): Int = x + y
    override def divide(x: Int, y: Int): Int =  round(x.toDouble / y.toDouble).toInt
    override def multiply(x: Int, y: Int): Int = x * y
    override def minus(x: Int, y: Int): Int = x - y
    override def sqrt(x: Int): Int = round(Math.sqrt(x)).toInt
  }
}
object Stats {
  //  same as
  //  def mean[T](xs: Vector[T])(implicit ev: Number[T]): T =
  //    ev.divide(xs.reduce(ev.plus(_, _)), xs.size)
  def mean[T: Number](xs: Vector[T]): T =
    implicitly[Number[T]].divide(
      xs.reduce(implicitly[Number[T]].plus(_, _)),
      xs.size
    )

  // assumes the vector is sorted
  def median[T: Number](xs: Vector[T]): T =
    xs(xs.size / 2)

  def variance[T: Number](xs: Vector[T]): T = {
    val simpleMean = mean(xs)
    val sqDiff = xs.map {
      case x =>
        val diff = implicitly[Number[T]].minus(x, simpleMean)
        implicitly[Number[T]].multiply(diff, diff)
    }
    mean(sqDiff)
  }

  def stddev[T: Number](xs: Vector[T]): T =
    implicitly[Number[T]].sqrt(variance(xs))
}

object StatsExample {
  import Stats._
  def main(args: Array[String]): Unit = {
    val intVector = Vector(1, 3, 5, 6, 10, 12, 17, 18, 19, 30, 36, 40, 42, 66)
    val doubleVector = Vector(1.5, 3.6, 5.0, 6.6, 10.9, 12.1, 17.3, 18.4, 19.2, 30.9, 36.6, 40.2, 42.3, 66.0)

    System.out.println(s"Mean (int): ${mean(intVector)}")
    System.out.println(s"Median (int): ${median(intVector)}")
    System.out.println(s"Std dev (int): ${stddev(intVector)}")

    System.out.println(s"Mean (double): ${mean(doubleVector)}")
    System.out.println(s"Median (double): ${median(doubleVector)}")
    System.out.println(s"Std dev (double): ${stddev(doubleVector)}")
  }
}

