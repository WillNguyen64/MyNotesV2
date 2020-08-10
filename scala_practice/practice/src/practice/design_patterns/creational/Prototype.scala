package practice.design_patterns.creational

// Create objects by cloning them from existing ones
// Be careful about shallow copies of objects, where actual references point to the original instances.
// Use only when object creating is very expensive

case class Cell(dna: String, proteins: List[String])

object PrototypeExample extends App {
  val initialCell = Cell("abcd", List("protein1", "protein2"))
  val copy1 = initialCell.copy()
  val copy2 = initialCell.copy()
  val copy3 = initialCell.copy(dna = "1234")
  println(s"The prototype is: $initialCell")
  println(s"Cell 1: $copy1")
  println(s"Cell 2: $copy2")
  println(s"Cell 3: $copy3")
  println(s"1 and 2 are equal in value: ${copy1 == copy2}")
  println(s"1 and 2 are the same object: ${copy1 eq copy2}")
}