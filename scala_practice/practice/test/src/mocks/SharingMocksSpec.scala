package mocks

import org.scalamock.scalatest.MockFactory
import org.scalatest.OneInstancePerTest
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

// Sharing mocks using isolated test cases

class SharingMocksSpec extends AnyFlatSpec with MockFactory with OneInstancePerTest with Matchers {

    // shared objects
  val waterContainerMock = mock[WaterContainer]
  val heaterMock = mock[Heater]
  val coffeeMachine = new CoffeeMachine(waterContainerMock, heaterMock)

  // set common expectations
  (heaterMock.isReady _).expects().returning(true)

  // common test setup
  coffeeMachine.powerOn

  "CoffeeMachine" should "not turn on the heater when the water container is empty" in {
    coffeeMachine.isOn shouldBe true
    (waterContainerMock.isEmpty _).expects().returning(true)
    (heaterMock.powerOn _).expects().never()

    coffeeMachine.makeCoffee
  }

  it should "not turn on the heater when the water container is overfull" in {
    coffeeMachine.isOn shouldBe true
    (waterContainerMock.isEmpty _).expects().returning(false)
    (waterContainerMock.isOverfull _).expects().returning(true)
    (heaterMock.powerOn _).expects().never()

    coffeeMachine.makeCoffee
  }
}
