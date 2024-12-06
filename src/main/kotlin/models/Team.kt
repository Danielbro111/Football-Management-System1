package ie.setu.models



data class Team(
    var tName: String,
    var manager: String,
    var captain: String,
    var league: String,
    var trophies: Int = 0,
    var teamStrength: Int = 0,
    var players:  MutableSet<Player> = mutableSetOf(),
    var teamId: Int= 0,) {


    private var lastPlayerNumber = 0
    private fun getPlayerNumber() = lastPlayerNumber++

    fun addPlayer(player: Player): Boolean {
        player.number = getPlayerNumber()
        return players.add(player)
    }
    fun calculateTeamValue(): Double {
        return players.sumOf { it.value}
    }

    fun mostExpensivePlayer(): Player? {
        return players.maxByOrNull { it.value }
    }

}


