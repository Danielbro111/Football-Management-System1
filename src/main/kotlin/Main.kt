package ie.setu
import controllers.ManagerAPI
import ie.setu.models.Player
import ie.setu.models.Team
import io.github.oshai.kotlinlogging.KotlinLogging
import persistence.JSONSerializer
import utils.readNextDouble
import utils.readNextInt
import utils.readNextLine
import java.io.File
import java.lang.System.exit

/**
 * Football Management System
 *
 * This project is a comprehensive Football Management System implemented in Kotlin.
 * It allows users to manage teams, players, and perform various operations related
 * to football team management.
 *
 * @version 1.0-SNAPSHOT
 *
 * Key Features:
 * - Team management (add, list, update teams)
 * - Player management (add players to teams, list players)
 * - Data persistence using JSON and XML serialization
 * - User input utilities for robust console interaction
 *
 * Project Structure:
 * - controllers: Contains the ManagerAPI for business logic
 * - models: Defines data classes for Team and Player
 * - persistence: Handles data serialization and deserialization
 * - utils: Provides utility functions for user input
 *
 * Main Components:
 * @see controllers.ManagerAPI - Central API for managing teams and players
 * @see models.Team - Data class representing a football team
 * @see models.Player - Data class representing a football player
 * @see persistence.Serializer - Interface for data serialization
 * @see utils.UserInput - Utility functions for handling user input
 *
 * Usage:
 * The main function serves as the entry point, presenting a menu-driven interface
 * for users to interact with the system. Users can add teams and players, list team
 * information, and perform other management tasks.
 *
 * @author [Daniel Brophy ]
 * @since [01/11/2024]
 */

private val logger = KotlinLogging.logger {}

// private val ManagerAPI = ManagerAPI(XMLSerializer(File("TeamInformation .xml")))
private val ManagerAPI = ManagerAPI(JSONSerializer(File("TeamInformation.json")))

/**
* The main entry point of the Football Management System application.
*
* This function initializes the application and starts the main menu loop
* by calling the [playMenu] function. It serves as the starting point for
* user interaction with the system.
*
* @see playMenu
*/
fun main() {
    playMenu()
}

// /Menus:

/**
 * Displays the main menu of the Football Management System and prompts for user input.
 *
 * @return An integer representing the user's menu choice.
 */
fun mainMenu(): Int {
    println(
        """
         |===============================================|
         |           FOOTBALL MANAGEMENT SYSTEM          |
         |===============================================|
         |                                               |
         |  1. Access Player Menu                        |
         |  2. Access Team Menu                          |
         |  3. Game Simulation                           |
         |                                               |
         |  0. Logout                                    |
         |                                               |
         |===============================================|
        """.trimIndent()
    )

    return readNextInt("Enter your choice: ")
}

/**
 * Displays the player management menu and prompts for user input.
 *
 * This menu provides options for adding, removing, viewing, and updating player information.
 *
 * @return An integer representing the user's menu choice.
 */
fun playerMenu(): Int {
    println(
        """
        |===============================================|
        |           FOOTBALL MANAGEMENT SYSTEM          |
        |===============================================|
        |              **** PLAYER MENU ****            |
        |                                               |
        |  1. Add a Player                              |
        |  2. Remove a Player                           |
        |  3. Show All Players                          |
        |  4. Show a Player (Using Index Number)        |
        |                                               |
        |  5. Update a Player                           |
        |  6. Save Players to the System                |
        |  7. Load Players from the System              |
        |                                               |
        |  0. Return to the Main Menu                   |
        |                                               |
        |===============================================|
        """.trimIndent()
    )

    return readNextInt("Enter your choice: ")
}

/**
 * Displays the team management menu and prompts for user input.
 *
 * This menu provides options for adding, removing, viewing, and updating team information,
 * as well as managing players within teams.
 *
 * @return An integer representing the user's menu choice.
 */
fun teamMenu(): Int {
    println(
        """
        |================================================|
        |           FOOTBALL MANAGEMENT SYSTEM           |
        |================================================|
        |              **** TEAM MENU ****               |
        |                                                |
        |  1.  Add a Team                                |
        |  2.  Remove a Team                             |
        |  3.  Show Team Info                            |
        |  4.  Update a Team                             | 
        |                                                |
        |  5.  Add a Player to a Team                    |
        |  6.  Show all teams Information                |            
        |                                                |
        |  7.  Show Team Value                           |
        |  8.  Show Most Expensive Player in Team        |
        |                                                |      
        |  9.  Save Teams to the System                  |
        |  10. Load Teams from the System                |
        |                                                |
        |   0. Return to the Main Menu                   |
        |                                                |
        |================================================|
        """.trimIndent()
    )
    return readNextInt("Enter your choice: ")
}

/**
 * Displays the main menu and handles user interactions for the Football Management System.
 *
 * This function runs in a loop, continuously displaying the main menu and processing
 * user input until the user chooses to log out. It delegates to specific functions
 * based on the user's choice.
 *
 * @see mainMenu
 * @see doPlayer
 * @see doTeam
 * @see gameSimulation
 * @see logOut
 */
fun playMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1 -> doPlayer()
            2 -> doTeam()
            3 -> gameSimulation()

            0 -> logOut()
            else -> println(
                """ | $option is an invalid option
                    | Please enter a valid option (0-3) 
                """.trimMargin()
            )
        }
    } while (true)
}

fun logOut() {
    println("Logging out")
    exit(0)
}

// /Player functions:
/**
 * Handles the player management menu and its operations.
 *
 * This function displays the player menu and processes user input in a loop
 * until the user chooses to return to the main menu. It delegates to specific
 * functions based on the user's choice.
 *
 * @see playerMenu
 * @see addPlayer
 * @see removePlayer
 * @see listAllPlayers
 * @see listPlayerbyIndex
 * @see updatePlayer
 * @see TeamSave
 * @see TeamLoad
 */
fun doPlayer() {
    do {
        val option = playerMenu()
        when (option) {
            1 -> addPlayer()
            2 -> removePlayer()
            3 -> listAllPlayers()
            4 -> listPlayerbyIndex()
            5 -> updatePlayer()

            6 -> TeamSave()
            7 -> TeamLoad()

            0 -> return
            else -> println(
                """ |   Invalid option entered: $option
                    |   Please enter a valid option (0-7)
                """.trimMargin()
            )
        }
    } while (true)
}

fun addPlayer() {
    val name = readNextLine("Enter the Players name:")
    val number = readNextInt("Enter the Players number:")
    val height = readNextDouble("Enter the Players height (Metres):")
    val weight = readNextDouble("Enter the Players weight (Kilogram):")
    val position = readNextLine("Enter the Players position:")
    val nationality = readNextLine("Enter the Players nationality:")
    val value = readNextDouble("Enter the Players value (Million Euros):")
    val isAdded = ManagerAPI.addPlayer(Player(name, number, height, weight, position, nationality, value))
    if (isAdded) {
        println()
        println("Player Added Successfully")
        println()
    } else {
        println()
        println("Player Add Failed")
        println()
    }
}

fun removePlayer() {
    val index = readNextInt("Enter the Index of the player you wish to remove: ")
    val isRemoved = ManagerAPI.removePlayer(index)

    if (isRemoved != null) {
        println()
        println("Player Removed Successfully")
        println()
    }
}

fun listAllPlayers() {
    println(ManagerAPI.listAllPlayers())
}

fun listPlayerbyIndex() {
    val index = readNextInt("Enter the Players index you want to display:")
    val printPlayer = ManagerAPI.listPlayerbyIndex(index)
    println()
    println(printPlayer)
    println()
}

fun updatePlayer() {
    listAllPlayers()

    val indexToUpdate = readNextInt("Enter the index of the note to update: ")
    if (ManagerAPI.isValidListIndex(indexToUpdate, ManagerAPI.players)) {
        val name = readNextLine("Enter the players name: ")
        val number = readNextInt("Enter the Players number:")
        val height = readNextDouble("Enter the Players height (Metres):")
        val weight = readNextDouble("Enter the Players weight (Kilogram):")
        val position = readNextLine("Enter the Players position:")
        val nationality = readNextLine("Enter the Players nationality:")

        if (ManagerAPI.updatePlayer(indexToUpdate, Player(name, number, height, weight, position, nationality))) {
            println()
            println("Update Successful")
            println()
        } else {
            println()
            println("Update Failed")
            println()
        }
    } else {
        println()
        println("There are no Players for this index number")
        println()
    }
}

// /Team functions:
/**
 * Handles the team management menu and its operations.
 *
 * This function displays the team menu and processes user input in a loop
 * until the user chooses to return to the main menu. It delegates to specific
 * functions based on the user's choice.
 *
 * @see teamMenu
 * @see addTeam
 * @see removeTeam
 * @see listTeam
 * @see updateTeam
 * @see addPlayerToTeam
 * @see listFullTeam
 * @see showTeamValue
 * @see MostExpensivePlayer
 */
fun doTeam() {
    do {
        val option = teamMenu()
        when (option) {
            1 -> addTeam()
            2 -> removeTeam()
            3 -> listTeam()
            4 -> updateTeam()
            5 -> addPlayerToTeam()
            6 -> listFullTeam()
            7 -> showTeamValue()
            8 -> MostExpensivePlayer()

            9 -> TeamSave()
            10 -> TeamLoad()

            0 -> return
            else -> println(
                """ |   Invalid option entered: $option
                    |   Please enter a valid option (0-10)
                """.trimMargin()
            )
        }
    } while (true)
}

fun addTeam() {
    val tName = readNextLine("Enter the teams name:")
    val manager = readNextLine("Enter the Managers name:")
    val captain = readNextLine("Enter the Captains name:")
    val league = readNextLine("Enter current league name:")
    val trophies = readNextInt("Enter the amount of trophies theyve won:")
    val teamStength = readNextInt("Enter the teams strength:")
    val isAdded = ManagerAPI.addTeam(Team(tName, manager, captain, league, trophies, teamStength))
    if (isAdded) {
        println()
        println("Team Added Successfully")
        println()
        logger.info {
            """Team added: ${tName.uppercase()} 
                           Added by Manager: ${manager.uppercase()}
                              """
        }
    } else {
        println()
        println("Team  failed to be added")
        println()
    }
}

fun removeTeam() {
    if (ManagerAPI.teams.isEmpty()) {
        println("No teams available to remove.")
    }
    println("Available teams:")

    for (i in ManagerAPI.teams.indices) {
        println("$i: ${ManagerAPI.teams[i].tName}")
    }
    println()
    val teamIndex = readNextInt("Enter the index of the team you wish to remove: ")
    val isRemoved = ManagerAPI.removeTeam(teamIndex)
    if (isRemoved != null) {
        println()
        println("Team '${isRemoved.tName}' has been removed successfully")
        println()
    } else {
        println()
        println("Failed to remove the team. ")
        println()
    }
}

fun listTeam() {
    val printTeam = ManagerAPI.listTeam()

    println()
    println(printTeam)
    println()
}

fun addPlayerToTeam() {
    if (ManagerAPI.teams.isNotEmpty()) {
        println("No teams exist. Please create a team first.")
    }
    println("Available teams:")
    for (i in ManagerAPI.teams.indices) {
        println("$i: ${ManagerAPI.teams[i].tName}")
    }
    println()

    val teamIndex = readNextInt("Enter the index of the team you wish to add a player to: ")
    val team = ManagerAPI.teams[teamIndex]
    val name = readNextLine("Enter the Player's name:")
    val number = readNextInt("Enter the Player's number:")
    val height = readNextDouble("Enter the Player's height (Metres):")
    val weight = readNextDouble("Enter the Player's weight (Kilograms):")
    val position = readNextLine("Enter the Player's position:")
    val nationality = readNextLine("Enter the Player's nationality:")
    val value = readNextDouble("Enter the Players value (Million Euros):")

    val player = Player(name, number, height, weight, position, nationality, value)
    val isAdded = ManagerAPI.addPlayerToTeam(team, player)

    if (isAdded) {
        println()
        println("Player added to team successfully!")
        println()
        logger.info {
            val manager = team.manager
            """Player added: ${name.uppercase()} 
                   Added by Manager: ${manager.uppercase()}
                   Added to Team: ${team.tName.uppercase()}
                """
        }
    } else {
        println()
        println("Failed to add player to team")
    }
}

fun updateTeam() {
    if (ManagerAPI.teams.isEmpty()) {
        println("No teams found.")
        return
    }
    for (i in ManagerAPI.teams.indices) {
        println("$i: ${ManagerAPI.teams[i].tName}" )

    }

    println()
    val Index = readNextInt("Enter the index of the team you wish to add a player to: ")
    println("Enter new team details:")
    val tName = readNextLine("Enter the team's new name:")
    val manager = readNextLine("Enter the new Manager's name:")
    val captain = readNextLine("Enter the new Captain's name:")
    val league = readNextLine("Enter new league name:")
    val trophies = readNextInt("Enter the new amount of trophies they've won:")

    val updatedTeam = Team(tName, manager, captain, league, trophies)
    val isUpdated = ManagerAPI.updateTeam(Index, updatedTeam)

    if (isUpdated) {
        println("Team updated successfully!")
        println()
        logger.info {
            """Team updated: $tName 
                   Updated by Manager: $manager
                      """
        }
    } else {
        println("Failed to update team.")
    }
}

fun listFullTeam() {
    println(ManagerAPI.listFullTeam())
}

fun showTeamValue() {
    if (ManagerAPI.teams.isNotEmpty()) {
        for (i in ManagerAPI.teams.indices) {
            println("$i: ${ManagerAPI.teams[i].tName}")
        }
        println()

        val index = readNextInt("Enter the index of the team: ")
        val totalValue = ManagerAPI.TeamTotalValue(index)
        println()
        println("The total value of the team is: $totalValue million Euros")
        println()
    } else {
        println()
        println("No team exists. Please create a team first.")
    }
}

fun MostExpensivePlayer() {
    if (ManagerAPI.teams.isEmpty()) {
        println("No teams available.")
        return
    }
    val teamIndex = readNextInt("Enter the index of the team: ")
    println(ManagerAPI.getMostExpensivePlayerForTeam(teamIndex))
}

// Game Simulation:

// EVERYTHING IS SET UP TROPHIES,TEAM STRENGTH AND VALUE TO DETERMINE THE WINNER
fun gameSimulation() {
    if (ManagerAPI.teams.size < 2) {
        println("Please add at least two teams to play a game")
        return
    }

    println("Available teams:")
    for (i in ManagerAPI.teams.indices) {
        println("$i: ${ManagerAPI.teams[i].tName}")
    }

    val team1Index = readNextInt("Enter the index of the first team: ")
    val team2Index = readNextInt("Enter the index of the second team: ")

    if (team1Index == team2Index) {
        println("Please select two different teams.")
        return
    }

    val result = ManagerAPI.simulateGame(team1Index, team2Index)
    println(result)
}

// Saving and Loading:

fun TeamSave() {
    try {
        ManagerAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun TeamLoad() {
    try {
        ManagerAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}
