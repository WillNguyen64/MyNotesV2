package mocks

import org.scalamock.scalatest.MockFactory
import org.scalatest.OneInstancePerTest
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

// Sharing mocks using fixture contexts

class SharingMocksSpec2 extends AnyFlatSpec with MockFactory with OneInstancePerTest with Matchers {

  // fixture context
  trait Test {
    // shared objects
    val waterContainerMock = mock[WaterContainer]
    val heaterMock = mock[Heater]
    val coffeeMachine = new CoffeeMachine(waterContainerMock, heaterMock)

    // common expectations
    (heaterMock.isReady _).expects().returning(true)

    // test setup
    coffeeMachine.powerOn
  }

  "CoffeeMachine" should "not turn on the heater when the water container is empty" in new Test {
    coffeeMachine.isOn shouldBe true
    (waterContainerMock.isEmpty _).expects().returning(true)
    (heaterMock.powerOn _).expects().never()

    coffeeMachine.makeCoffee
  }

  // Extend and combine fixture-contexts
  trait OverfullWaterContainerTest extends Test {
    (waterContainerMock.isEmpty _).expects().returning(false)
    (waterContainerMock.isOverfull _).expects().returning(true)
(true)
  }

  it should "not turn on the heater when the water container is overfull" in new OverfullWaterContainerTest {
    coffeeMachine.isOn shouldBe true
    (heaterMock.powerOn _).expects().never()

    coffeeMachine.makeCoffee
  }
}
