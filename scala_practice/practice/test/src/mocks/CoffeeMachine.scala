package mocks

class CoffeeMachine(waterContainer: WaterContainer, heater: Heater) {
  private var isOnState = false

  def powerOn: Unit = {
    isOnState = true
  }

  def powerOff: Unit = {
    isOnState = false
  }

  def isOn: Boolean = isOnState

  def makeCoffee: Unit = {
    if (heater.isReady && !(waterContainer.isEmpty || waterContainer.isOverfull)) {
      heater.powerOn
    }
  }
}

trait WaterContainer {
  def isEmpty: Boolean
  def isOverfull: Boolean
}

trait Heater {
  def isReady: Boolean

  def powerOn: Unit

  def powerOff: Unit

  def isOn
}