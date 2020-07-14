package practice.collections

object CommonOps extends App {

  println("======= Building =======")

  println("======= Factory Methods =======")

  println("======= Transformations =======")

  {
    println("Using comprehensions to transform collections ")
    val a = Array(1, 2, 3, 4)
    val a2 = for (i <- a) yield i * i
    println(a2.mkString(" "))
    val a3 = for (i <- a) yield "hello " + i
    println(a3.mkString(" "))
    val a4 = for (i <- a if i % 2 == 0) yield "hello" + i
    println(a4.mkString(" "))
    println
  }

  {
    println("Using comprehensions with multiple input arrays")
    val a = Array(1, 2); val b = Array("hello", "world")
    val flattened = for (i <- a; s <- b) yield s + i
    // Also equivalent
    val flattened2 = for {
      i <- a
      s <- b
    } yield s + i
    println(flattened.mkString(" "))
  }

  println("======= Queries =======")

  println("======= Aggregations =======")

  println("======= Combining Operations =======")

  println("======= Converters =======")

  println("======= Views =======")


}
