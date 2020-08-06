package mocks

import mocks.Country.Country

case class Player(id: PlayerId, nickname: String, country: Country)

case class MatchResult(winner: PlayerId, loser: PlayerId)

case class CountryLeaderboardEntry(country: Country, points: Int)

trait CountryLeaderboard {
  def addVictoryForCountry(country: Country): Unit
  def getTopCountries(): List[CountryLeaderboardEntry]
}

class MatchResultObserver(playerDatabase: PlayerDatabase, countryLeaderBoard: CountryLeaderboard) {
  def recordMatchResult(result: MatchResult): Unit = {
    val player = playerDatabase.getPlayerById(result.winner)
    countryLeaderBoard.addVictoryForCountry(player.country)
  }
}

trait PlayerDatabase {
  def getPlayerById(playerId: PlayerId): Player
}