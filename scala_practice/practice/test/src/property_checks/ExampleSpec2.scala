package property_checks

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.prop.TableDrivenPropertyChecks

class ExampleSpec2 extends AnyFlatSpec with TableDrivenPropertyChecks with Matchers {

  "Fractions" should "throw exception for invalid values" in {
    val invalidCombos =
      Table(
        ("n",               "d"),
        (Integer.MIN_VALUE, Integer.MIN_VALUE),
        (1,                 Integer.MIN_VALUE),
        (Integer.MIN_VALUE, 1),
        (Integer.MIN_VALUE, 0),
        (1,                 0)
      )

    forAll (invalidCombos) { (n: Int, d: Int) =>
      an [IllegalArgumentException] should be thrownBy {
        new Fraction(n, d)
      }
    }
  }
}
