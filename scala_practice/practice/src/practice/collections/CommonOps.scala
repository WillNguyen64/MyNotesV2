package practice.collections

object CommonOps extends App {

  println("======= Building =======")

  {
    println("Using builders to construct collection of unknown length")
    val b = Array.newBuilder[Int]
    b += 1
    b += 2
    // Freeze array into the result you want
    println(b.result().mkString(" "))
    println
  }

  println("======= Factory Methods =======")

  {
    println("Using factory methods to build pre-populated collections")
    println(Array.fill(5)("hello").mkString(" "))
    println(Array.tabulate(5)(n => s"hello $n").mkString(" "))
    println((Array(1, 2, 3) ++ Array(4, 5, 6)).mkString(" "))
    println
  }

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
    println(flattened.mkString(" "))
    // Also equivalent
    val flattened2 = for {
      i <- a
      s <- b
    } yield s + i
    println(flattened2.mkString(" "))
  }

  {
    println("Transform elements in collection")
    println(Array(1, 2, 3).map(i => i * 2).mkString(" "))
    println(Array(1, 2, 3).filter(i => i % 2 == 1).mkString(" "))
    println(Array(1, 2, 3).take(2).mkString(" ")) // keep first two elements
    println(Array(1, 2, 3).drop(2).mkString(" ")) // discard first two elements
    println(Array(1, 2, 3, 4, 5).slice(1, 4).mkString(" ")) // Keep elements from index 1-4
    println(Array(1, 2, 3, 4, 5, 4, 3, 2, 1).distinct.mkString(" "))
    // NOTE: Transformations perform copies. If copying leads to bottleneck, convert map/filter/etc transformation
    // code into mutating operations over raw Arrays or In-place operations over Mutable collections
  }

  println("======= Queries =======")

  {
    println("Finding an element")
    println(Array(1, 2, 3, 4, 5, 6, 7).find(i => i % 2 == 0 && i > 4))
    println(Array(1, 2, 3, 4, 5, 6, 7).find(i => i % 2 == 0 && i > 10))

    println("Check if an element exists")
    println(Array(1, 2, 3).exists(x => x > 1))
    println(Array(1, 2, 3).exists(_ < 0))

  }

  println("======= Aggregations =======")

  {
    println("Folding elements in a collection")
    println(Array(1, 2, 3, 4, 5, 6, 7).foldLeft(1)(_ * _))

    println("Grouping elements into a Map")
    println(Array(1, 2, 3, 4, 5, 6, 7).groupBy(_ % 2))

  }


  println("======= Combining Operations =======")

  println("======= Converters =======")

  println("======= Views =======")


}
