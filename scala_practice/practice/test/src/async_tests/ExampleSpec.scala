package async_tests

import org.scalatest.flatspec.AsyncFlatSpec

import scala.concurrent.Future

class ExampleSpec extends AsyncFlatSpec {

  def addSoon(addends: Int*): Future[Int] = Future { addends.sum }

  behavior of "addSoon"

  it should "eventually compute a sum of passed Ints" in {
    val futureSum: Future[Int] = addSoon(1, 2)
    // Returns Future[Assertion]
    futureSum map { sum => assert(sum == 3) }
  }

  def addNow(addends: Int*): Int = addends.sum

  behavior of "addNow"

  it should "immediately compute a sum of passed Ints" in {
    val sum: Int = addNow(1, 2)
    // Returns Assertion, but is implicitly converted to Future[Assertion]
    assert(sum == 3)
  }
}
