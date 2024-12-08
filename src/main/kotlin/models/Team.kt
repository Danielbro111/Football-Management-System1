package ie.setu.models

/**
 * Represents a football team.
 *
 * @property tName The name of the team.
 * @property manager The name of the team's manager.
 * @property captain The name of the team's captain.
 * @property league The league in which the team plays.
 * @property trophies The number of trophies won by the team. Defaults to 0.
 * @property teamStrength The overall strength of the team. Defaults to 0.
 * @property players A mutable set of Player objects representing the team's roster.
 * @property teamId A unique identifier for the team. Defaults to 0.
 */
data class Team(
    var tName: String,
    var manager: String,
    var captain: String,
    var league: String,
    var trophies: Int = 0,
    var teamStrength: Int = 0,
    var players: MutableSet<Player> = mutableSetOf(),
    var teamId: Int = 0
) {

    private var lastPlayerNumber = 0

    /**
     * Generates and returns the next available player number.
     *
     * @return The next available player number.
     */
    private fun getPlayerNumber() = lastPlayerNumber++

    /**
     * Adds a new player to the team.
     *
     * @param player The Player object to be added to the team.
     * @return true if the player was successfully added, false otherwise.
     */
    fun addPlayer(player: Player): Boolean {
        player.number = getPlayerNumber()
        return players.add(player)
    }

    /**
     * Calculates the total value of the team based on the sum of all players' values.
     *
     * @return The total value of the team as a Double.
     */
    fun calculateTeamValue(): Double {
        return players.sumOf { it.value }
    }

    /**
     * Finds the most expensive player in the team.
     *
     * @return The Player object with the highest value, or null if the team has no players.
     */
    fun mostExpensivePlayer(): Player? {
        return players.maxByOrNull { it.value }
    }
}
