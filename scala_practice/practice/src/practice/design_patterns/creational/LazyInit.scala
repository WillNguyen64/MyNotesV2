package practice.design_patterns.creational

import java.util.Properties

object CircleUtils {
  val basicPi = 3.14
  lazy val precisePi: Double = {
    println("Reading properties for the precise PI.")
    val props = new Properties()
    // props.load(getClass.getResourceAsStream("pi.properties"))
    props.load(getClass.getClassLoader.getResourceAsStream("pi.properties"))
    props.getProperty("pi.high").toDouble
  }

  def area(radius: Double, isPrecise: Boolean = false): Double = {
    val pi: Double = if (isPrecise) precisePi else basicPi
    pi * Math.pow(radius, 2)
  }
}

object LazyInitExample extends App {
  println(s"The basic area for a circle with radius 2.5 is ${CircleUtils.area(2.5)}")
  println(s"The basic area for a circle with radius 6.78 is ${CircleUtils.area(6.78)}")
  println(s"The basic area for a circle with radius 6.78 is ${CircleUtils.area(6.78, true)}")
}
