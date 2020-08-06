package object mocks {

  type PlayerId = Int

  object Country extends Enumeration {
    type Country = Value
    val Russia, Poland, Germany = Value
  }
}
