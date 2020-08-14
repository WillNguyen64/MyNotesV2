package practice.design_patterns

// Pimp my library
// To extend a third-party library that you can't modify, you need to:
//   - Create an implicit class, in the package object
//   - Have the class extend AnyVal

package object scala_patterns {
  implicit class StringExtensions(val s: String) extends AnyVal {

    def isAllUpperCase: Boolean =
      (0 to s.size - 1).find {
        case index =>
          !s.charAt(index).isUpper
      }.isEmpty

  }

  implicit class PersonSeqExtensions(val seq: Iterable[Person]) extends AnyVal {

    def saveToDatabase(): Unit = {
      seq.foreach {
        case person =>
          System.out.println(s"Saved: ${person} to the database.")
      }
    }

  }
}
