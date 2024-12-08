package controllers

import ie.setu.models.Player
import ie.setu.models.Team
import persistence.Serializer
import utils.readNextInt
/**
 * ManagerAPI is the central controller for the Football Management System.
 * It manages teams and players, providing functionality for adding, removing,
 * updating, and listing both teams and players. It also includes features for
 * team valuation, player analysis, and game simulation.
 *
 * @property players ArrayList of Player objects representing all players in the system.
 * @property teams ArrayList of Team objects representing all teams in the system.
 * @property serializer The Serializer used for data persistence.
 *
 * @constructor Creates a ManagerAPI with the specified serializer type.
 * @param serializerType The Serializer implementation to be used for data persistence.
 *
 * Key functionalities:
 * - Team management (add, remove, update, list)
 * - Player management (add, remove, update, list)
 * - Team valuation and analysis
 * - Player analysis (e.g., finding most expensive player)
 * - Game simulation
 * - Data persistence (load and store)
 *
 * @see Player
 * @see Team
 * @see Serializer
 */

class ManagerAPI(serializerType: Serializer) {
    var players = ArrayList<Player>()
    var teams = ArrayList<Team>()
    private var serializer: Serializer = serializerType

    /**
     * Returns the total number of teams currently managed by the system.
     *
     * This function provides a quick way to get the count of teams without
     * directly accessing the teams list.
     *
     * @return The number of teams in the system as an Int.
     */
    fun numberOfTeams() = teams.size

    /**
     * Calculates the total value of a team at the specified index.
     *
     * This function retrieves the team at the given index and calculates its total value.
     * If the index is invalid, it returns 0.0.
     *
     * @param index The index of the team in the teams list.
     * @return The total value of the team as a Double, or 0.0 if the index is invalid.
     */
    fun TeamTotalValue(index: Int): Double {
        return if (isValidListIndex(index, teams)) {
            teams[index].calculateTeamValue()
        } else {
            0.0
        }
    }

    /**
     * Retrieves information about the most expensive player in a specified team.
     *
     * This function finds the most expensive player in the team at the given index
     * and returns a formatted string with the player's details. If the team index
     * is invalid or if the team has no players, it returns an appropriate message.
     *
     * @param teamIndex The index of the team in the teams list.
     * @return A formatted string containing the most expensive player's details,
     *         or an error message if the team index is invalid or the team has no players.
     */
    fun getMostExpensivePlayerForTeam(teamIndex: Int): String {
        if (teamIndex < 0 || teamIndex >= teams.size) {
            return "Invalid team index: $teamIndex"
        }

        val team = teams[teamIndex]
        val player = team.mostExpensivePlayer()

        return if (player != null) {
            """
        |*** Most Expensive Player for ${team.tName.uppercase()} ***
        | Name: ${player.name.uppercase()}
        | Number: ${player.number}
        | Position: ${player.position.uppercase()}
        | Value: $${player.value} million
            """.trimMargin()
        } else {
            "No players in the team: ${team.tName}"
        }
    }

    fun addPlayer(player: Player): Boolean {
        return players.add(player)
    }

    /**
     * Adds a new player to the system.
     *
     * This function adds the given Player object to the list of players managed by the system.
     * It does not perform any duplicate checking or validation before adding the player.
     *
     * @param player The Player object to be added to the system.
     * @return Boolean indicating whether the player was successfully added (true) or not (false).
     *         This will typically return true unless there's an unexpected issue with the ArrayList.
     */
    fun addTeam(team: Team): Boolean {
        return teams.add(team)
    }

    /**
     * Checks if the given index is valid for the provided list.
     *
     * This function determines whether an index is within the bounds of a list.
     * It ensures that the index is not negative and is less than the size of the list.
     *
     * @param index The index to be validated.
     * @param list The list against which the index is being checked.
     * @return Boolean true if the index is valid (within the list bounds), false otherwise.
     */
    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    /**
     * Removes a player from the system at the specified index.
     *
     * This function attempts to remove a player from the [players] list at the given index.
     * It first checks if the index is valid using [isValidListIndex]. If the index is valid,
     * it removes and returns the player at that index. If the index is invalid, it prints
     * an error message and returns null.
     *
     * @param index The index of the player to be removed.
     * @return The removed [Player] object if successful, or null if the index is invalid.
     */
    fun removePlayer(index: Int): Player? {
        return if (isValidListIndex(index, players)) {
            players.removeAt(index)
        } else {
            println("Invalid index: $index")
            null
        }
    }

    /**
     * Lists all players currently in the system.
     *
     * This function provides a formatted string representation of all players
     * in the system. If there are no players, it returns a message indicating so.
     *
     * @return A String containing the list of all players with their indices,
     *         or a message indicating no players are found.
     */
    fun listAllPlayers(): Any {
        return if (players.isEmpty()) {
            "No players found"
        } else {
            var listAllPlayers = ""
            for (i in players.indices) {
                listAllPlayers += "$i: ${players[i]} \n"
            }
            println()
            println(listAllPlayers.trimMargin())
            println()
        }
    }

    /**
     * Retrieves a player at the specified index from the list of players.
     *
     * This function attempts to return the player at the given index. It performs
     * several checks before returning:
     * 1. If the players list is empty, it returns a message indicating no players are found.
     * 2. If the provided index is invalid, it returns a message prompting for a valid index.
     * 3. If the index is valid, it returns the Player object at that index.
     *
     * @param index The index of the player to retrieve.
     * @return Either a Player object if found, or a String message if no player is found or the index is invalid.
     */
    fun listPlayerbyIndex(index: Int): Any {
        return if (players.isEmpty()) {
            "No players found"
        } else if (!isValidListIndex(index, players)) {
            "Please enter a valid index"
        } else {
            players[index]
        }
    }

    /**
     * Finds and returns a player at the specified index in the players list.
     *
     * This function attempts to retrieve a player from the [players] list at the given index.
     * It first checks if the index is valid using [isValidListIndex]. If the index is valid,
     * it returns the Player at that index. If the index is invalid, it returns null.
     *
     * @param index The index of the player to find in the players list.
     * @return The [Player] object if found at the given index, or null if the index is invalid.
     */

    fun findPlayer(index: Int): Player? {
        return if (isValidListIndex(index, players)) {
            players[index]
        } else {
            null
        }
    }

    /**
     * Updates an existing player's information in the system.
     *
     * This function attempts to update the information of a player at the specified index
     * with the provided new player data. It first checks if the player exists at the given index
     * and if the new player data is not null.
     *
     * @param indexToUpdate The index of the player to be updated in the players list.
     * @param updatedPlayer The Player object containing the updated information. Can be null.
     * @return Boolean indicating whether the update was successful (true) or not (false).
     */
    fun updatePlayer(indexToUpdate: Int, players: Player?): Boolean {
        val playerFound = findPlayer(indexToUpdate)

        if ((playerFound != null) && (players != null)) {
            playerFound.name = players.name
            playerFound.number = players.number
            playerFound.height = players.height
            playerFound.weight = players.weight
            playerFound.position = players.position
            playerFound.nationality = players.nationality
            return true
        }
        return false
    }

    // Team Functions

    /**
     * Removes a team from the system at the specified index.
     *
     * This function attempts to remove a team from the [teams] list at the given index.
     * It checks if the index is within the valid range of the teams list.
     *
     * @param index The index of the team to be removed.
     * @return The removed [Team] object if successful, or null if the index is invalid.
     */
    fun removeTeam(index: Int): Team? {
        return if (index in teams.indices) {
            teams.removeAt(index)
        } else {
            null
        }
    }

    /**
     * Updates an existing team's information in the system.
     *
     * This function attempts to update the information of a team at the specified index
     * with the provided new team data. It first checks if the index is valid using the
     * [isValidListIndex] function.
     *
     * @param index The index of the team to be updated in the teams list.
     * @param updatedTeam The Team object containing the updated information.
     * @return Boolean indicating whether the update was successful (true) or not (false).
     */
    fun updateTeam(index: Int, updatedTeam: Team): Boolean {
        return if (index in teams.indices) {
            teams[index] = updatedTeam
            true
        } else {
            false
        }
    }

    /**
     * Returns the total number of players currently managed by the system.
     *
     * This function provides a quick way to get the count of players without
     * directly accessing the players list.
     *
     * @return The number of players in the system as an Int.
     */
    fun numberOfPlayers() = players.size

    /**
     * Lists all teams currently in the system.
     *
     * This function provides a formatted string representation of all teams
     * in the system. If there are no teams, it returns a message indicating so.
     *
     * @return A String containing the list of all teams with their details,
     *         or a message indicating no teams are found.
     */
    fun listTeam(): String {
        return if (teams.isEmpty()) {
            println()
            "No teams found"
        } else {
            var listAllTeams = ""
            for (team in teams) {
                listAllTeams += """
       |             
       |*** Team Information ***             
       | Team Name: ${team.tName.uppercase()}                        
       | Manager: ${team.manager.uppercase()}                        
       | Captain: ${team.captain.uppercase()}                        
       | League: ${team.league.uppercase()}                          
       | Trophies: ${team.trophies}    
       | Number of players: ${numberOfPlayers()}            
                """.trimIndent()
            }
            println()
            listAllTeams
        }
    }

    /**
     * Finds and returns a team at the specified index in the teams list.
     *
     * This function attempts to retrieve a team from the [teams] list at the given index.
     * It first checks if the index is valid using [isValidListIndex]. If the index is valid,
     * it returns the Team at that index. If the index is invalid, it returns null.
     *
     * @param index The index of the team to find in the teams list.
     * @return The [Team] object if found at the given index, or null if the index is invalid.
     */
    fun findTeam(index: Int): Team? {
        return if (isValidListIndex(index, teams)) {
            teams[index]
        } else {
            null
        }
    }

    /**
     * Adds a player to a specified team.
     *
     * This function attempts to add the given player to the specified team.
     * It uses the team's addPlayer method, which may have its own validation logic.
     *
     * @param team The [Team] object to which the player should be added.
     * @param player The [Player] object to be added to the team.
     * @return Boolean indicating whether the player was successfully added (true) or not (false).
     */
    fun addPlayerToTeam(team: Team, player: Player): Boolean {
        team.addPlayer(player)
        return true
    }

    /**
     * Lists the full details of a selected team, including all its players.
     */
    fun listFullTeam() {
        if (teams.isEmpty()) {
            println("No teams found.")
            return
        }

        // Display available teams
        for (i in teams.indices) {
            println("$i: ${teams[i].tName}")
        }
        println()

        // Get user input for team selection

        val teamIndex = readNextInt("Enter the index of the team you want to list: ")

        val team = teams[teamIndex]

        // Display team information
        println(
            """
           > ***    Team Information    ***
           > Team Name: ${team.tName.uppercase()}
           > Manager: ${team.manager.uppercase()}
           > Captain: ${team.captain.uppercase()}
           > League: ${team.league.uppercase()}
           > Trophies: ${team.trophies}
           > Number of players: ${team.players.size}
           >
           > Players:
""".trimMargin(">")
        )
// Display player information
        if (team.players.isEmpty()) {
            println("> No players are in the team.")
        } else {
            for (player in team.players) {
                println(
                    """
                >       
                >   Name: ${player.name.uppercase()}
                >   Number: ${player.number}
                >   Position: ${player.position.uppercase()}
                >   Height: ${player.height} m
                >   Weight: ${player.weight} kg
                >   Nationality: ${player.nationality.uppercase()}
                >   
            """.trimMargin(">")
                )
            }
        }
    }

    /**
     * Calculates the score for a given team.
     *
     * This function computes a team's score based on three factors:
     * 1. The team's strength
     * 2. The number of trophies the team has won
     * 3. The calculated value of the team
     *
     * The score is determined by multiplying these three factors together.
     *
     * @param team The Team object for which to calculate the score.
     * @return An integer representing the calculated score of the team.
     */
    fun calculateTeamScore(team: Team): Int {
        return team.teamStrength * team.trophies * team.calculateTeamValue().toInt()
    }

    /**
     * Simulates a game between two teams and returns the result.
     *
     * This function takes the indices of two teams, calculates their scores using [calculateTeamScore],
     * and determines the winner based on these scores. It returns a formatted string containing
     * the game simulation details and the final result.
     *
     * @param team1Index The index of the first team in the [teams] list.
     * @param team2Index The index of the second team in the [teams] list.
     * @return A formatted string containing the game simulation details and result.
     * @throws IndexOutOfBoundsException if either team index is invalid.
     */
    fun simulateGame(team1Index: Int, team2Index: Int): Any {
        val team1 = teams[team1Index]
        val team2 = teams[team2Index]

        val team1Score = calculateTeamScore(team1)
        val team2Score = calculateTeamScore(team2)

        return """
                        **Game Simulation**
            ${team1.tName.uppercase()} vs ${team2.tName.uppercase()}
            
            ${team1.tName.uppercase()} Scored $team1Score points!
            ${team2.tName.uppercase()} Scored $team2Score points!
            
                          **Final Result**
           ${
        if (team1Score > team2Score) {
            "${team1.tName} wins!"
        } else if (team2Score > team1Score) {
            "${team2.tName} wins!"
        } else {
            "It's a draw!"
        }
        }
        """.trimIndent()
    }

    /**
     * Loads player and team data from persistent storage.
     *
     * This function reads serialized data using the [serializer] and populates
     * the [players] and [teams] lists. It casts the read data to the appropriate
     * ArrayList types.
     *
     * @throws Exception if there's an error during the reading process.
     */
    @Throws(Exception::class)
    fun load() {
        players = serializer.read() as ArrayList<Player>
        teams = serializer.read() as ArrayList<Team>
        println("loaded successfully.")
    }

    /**
     * Stores player and team data to persistent storage.
     *
     * This function writes the current state of [players] and [teams] lists
     * to persistent storage using the [serializer].
     *
     * @throws Exception if there's an error during the writing process.
     */
    @Throws(Exception::class)
    fun store() {
        serializer.write(players)
        serializer.write(teams)
        println("saved successfully.")
    }
}
