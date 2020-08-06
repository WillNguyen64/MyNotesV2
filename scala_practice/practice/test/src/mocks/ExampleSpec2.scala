package mocks

import org.scalamock.scalatest.MockFactory
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ExampleSpec2 extends AnyFlatSpec with MockFactory with Matchers {

  val winner = Player(222, "boris", Country.Russia)
  val loser = Player(333, "hans", Country.Germany)

  "ScalaMock" should "support mocking and stubbing objects" in {
    // Can mix expectations-first (mock) and record-then-verify (stub) styles
    val countryLeaderBoardMock = mock[CountryLeaderboard]
    val userDetailsServiceStub = stub[PlayerDatabase]

    // set expectations
    (countryLeaderBoardMock.addVictoryForCountry _).expects(Country.Russia)

    // configure stubs
    (userDetailsServiceStub.getPlayerById _).when(loser.id).returns(loser)
    (userDetailsServiceStub.getPlayerById _).when(winner.id).returns(winner)

    // run system under test
    val matchResultObserver = new MatchResultObserver(userDetailsServiceStub, countryLeaderBoardMock)
    matchResultObserver.recordMatchResult(MatchResult(winner = winner.id, loser = loser.id))
  }
}