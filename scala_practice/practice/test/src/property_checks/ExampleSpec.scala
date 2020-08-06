package property_checks

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalatestplus.scalacheck.ScalaCheckPropertyChecks

class ExampleSpec extends AnyFlatSpec with ScalaCheckPropertyChecks with Matchers {

  "Fractions" should "be normalized" in {
    // Uses a generator-driven property check to test that
    // passed values for numerator and denominator are
    // properly normalized

    forAll { (n: Int, d: Int) =>

      whenever (d != 0 && d != Integer.MIN_VALUE
        && n != Integer.MIN_VALUE) {

        val f = new Fraction(n, d)

        if (n < 0 && d < 0 || n > 0 && d > 0)
          f.numer should be > 0
        else if (n != 0)
          f.numer should be < 0
        else
          f.numer shouldEqual 0

        f.denom should be > 0
      }
    }
  }
}
