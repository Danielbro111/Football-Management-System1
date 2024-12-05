package ie.setu
import io.github.oshai.kotlinlogging.KotlinLogging
import controllers.ManagerAPI
import ie.setu.models.Player
import ie.setu.models.Team
import persistence.JSONSerializer
import persistence.XMLSerializer
import utils.readNextDouble
import utils.readNextInt
import utils.readNextLine
import java.io.File
import java.lang.System.exit



private val logger = KotlinLogging.logger {}
//private val ManagerAPI = ManagerAPI(XMLSerializer(File("TeamInformation .xml")))
private val ManagerAPI = ManagerAPI(JSONSerializer(File("TeamInformation.json")))

fun main() {

    playMenu()
}

///Menus:

fun mainMenu(): Int {
    println(
        """
         |===============================================|
         |           FOOTBALL MANAGEMENT SYSTEM          |
         |===============================================|
         |                                               |
         |  1. Access Player Menu                        |
         |  2. Access Team Menu                          |
         |                                               |
         |  0. Logout                                    |
         |                                               |
         |===============================================|
        """.trimIndent())

    return readNextInt("Enter your choice: ")
}

fun playerMenu(): Int {
    println("""
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
    """.trimIndent())


    return readNextInt("Enter your choice: ")
}

fun teamMenu(): Int {
    println("""
        |===============================================|
        |           FOOTBALL MANAGEMENT SYSTEM          |
        |===============================================|
        |              **** TEAM MENU ****              |
        |                                               |
        |  1. Add a Team                                |
        |  2. Remove a Team                             |
        |  3. Show Team Info                            |
        |  4. Update a Team                             | 
        |                                               |
        |  5. Add a Player to a Team                    |
        |  6. Show All Players in a Team                |
        |                                               |
        |  7. Show Team Value                           |
        |  8. Show Most Expensive Player in Team        |
        |                                               |
        |  0. Return to the Main Menu                   |
        |                                               |
        |===============================================|
    """.trimIndent())
    return readNextInt("Enter your choice: ")
}
fun playMenu() {
    do {
        val option = mainMenu()
        when (option) {

            1 -> doPlayer()
            2 -> doTeam()


            0 -> logOut()
            else -> println(
                """ | $option is an invalid option
               |   Please enter a valid option (0-2) """.trimMargin()
            )
        }
    } while (true)
}

fun logOut() {
    println("Logging out")
    exit(0)
}

///Player functions:
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
                    |   Please enter a valid option (0-7)""".trimMargin()
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
    val isAdded = ManagerAPI.addPlayer(Player(name, number, height, weight, position, nationality,value))
    if (isAdded) {
        println("Player Added Successfully")
    } else {
        println("Player Add Failed")
    }
}

fun removePlayer() {
   val index = readNextInt("Enter the Index of the player you wish to remove: ")
    val isRemoved = ManagerAPI.removePlayer(index)

    if (isRemoved != null) {
        println("Player Removed Successfully")
    }
}

fun listAllPlayers() {
    println(ManagerAPI.listAllPlayers())

}


fun listPlayerbyIndex()  {
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

///Team functions:
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

            0 -> return
            else -> println(
                """ |   Invalid option entered: $option
                    |   Please enter a valid option (0-6)""".trimMargin()
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
            val isAdded = ManagerAPI.addTeam(Team(tName, manager, captain, league, trophies))
            if (isAdded) {
                println("Team Added Successfully")
                println()
                logger.info { """Team added: ${tName.uppercase()} 
                           Added by Manager: ${manager.uppercase()}
                              """ }
            } else {
                println("Team  failed to be added")
            }
        }

fun removeTeam() {
    val isRemoved = ManagerAPI.removeTeam(0)

    if (isRemoved != null) {
        println("Team has been Removed Successfully")
    } else {
        println("Team failed to be removed")
    }
}

    fun listTeam() {
      val printTeam =  ManagerAPI.listTeam()

        println(printTeam)
    }


private fun addPlayerToTeam() {
    if (ManagerAPI.teams.isNotEmpty()) {
        val team = ManagerAPI.teams[0]
        val name = readNextLine("Enter the Player's name:")
        val number = readNextInt("Enter the Player's number:")
        val height = readNextDouble("Enter the Player's height (Metres):")
        val weight = readNextDouble("Enter the Player's weight (Kilograms):")
        val position = readNextLine("Enter the Player's position:")
        val nationality = readNextLine("Enter the Player's nationality:")
        val value = readNextDouble("Enter the Players value (Million Euros):")
        
        val player = Player(name, number, height, weight, position, nationality,value)
        val isAdded = ManagerAPI.addPlayerToTeam(team, player)

        if (isAdded) {
            println("Player added to team successfully!")
            println()
            logger.info {
                val manager = team.manager
                """Player added: ${name.uppercase()} 
                   Added by Manager: ${manager.uppercase()}
                   Added to Team: ${team.tName.uppercase()}
                """ }
        } else {
            println("Failed to add player to team")
        }
    } else {
        println("No team exists. Please create a team first.")
    }
}

fun updateTeam() {
    if (ManagerAPI.teams.isEmpty()) {
        println("No teams found.")
        return
    }
    listTeam()
    println("Enter new team details:")
    val tName = readNextLine("Enter the team's new name:")
    val manager = readNextLine("Enter the new Manager's name:")
    val captain = readNextLine("Enter the new Captain's name:")
    val league = readNextLine("Enter new league name:")
    val trophies = readNextInt("Enter the new amount of trophies they've won:")

    val updatedTeam = Team(tName, manager, captain, league, trophies)
    val isUpdated = ManagerAPI.updateTeam(0, updatedTeam)

    if (isUpdated) {
        println("Team updated successfully!")
        println()
        logger.info { """Team updated: $tName 
                   Updated by Manager: $manager
                      """ }
    } else {
        println("Failed to update team.")
    }
}

fun listFullTeam() {
    println(ManagerAPI.listFullTeam())
}


fun showTeamValue() {
    if (ManagerAPI.teams.isNotEmpty()) {
        val totalValue = ManagerAPI.TeamTotalValue(0)
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



//Game Simulation:




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

