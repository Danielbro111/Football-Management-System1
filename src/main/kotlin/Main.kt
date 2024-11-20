package ie.setu
import io.github.oshai.kotlinlogging.KotlinLogging
import controllers.ManagerAPI
import ie.setu.models.Player
import ie.setu.models.Team
import utils.readNextDouble
import utils.readNextInt
import utils.readNextLine
import java.lang.System.exit



private val logger = KotlinLogging.logger {}
private val ManagerAPI = ManagerAPI()

fun main() {

    playMenu()

}

fun mainMenu() : Int {
    println()
    println( """
        > *********FOOTBALL MANAGEMENT SYSTEM*********         
        > |                                          |
        > | 1 -> Access Player Menu                  |                              
        > | 2 -> Access Team Menu                    |
        > |                                          |   
        > | 0 -> Logout                              |
        > |__________________________________________|  
    """.trimMargin(">"))
    return readNextInt(" > ==>>")
}

fun playerMenu() : Int {
    println()
    println( """
        > ********FOOTBALL MANAGEMENT SYSTEM*********         
        > |            ***Player Menu***             |
        > | 1 -> Add a Player                        |
        > | 2 -> Remove a Player                     |
        > | 3 -> Show all Players                    |
        > | 4 -> Show a player (Using Index Number)  |
        > | 5 -> Update a Player                     |
        > |                                          |
        > | 0 -> Return to the MainMenu              |                                                        
        > |__________________________________________|  
          """.trimMargin(">"))
    return readNextInt(" > ==>>")
}

fun teamMenu() : Int {
    println()
    println( """
        >  *******FOOTBALL MANAGEMENT SYSTEM********         
        > |            ***Team Menu***              |
        > | 1 -> Add your team                      |
        > | 2 -> Remove your team                   |
        > | 3 -> Show team info                     |
        > | 4 -> Update your team                   |
        > | 5 -> Show all Teams                     |
        > |                                         |
        > | 0 -> Return to the MainMenu             |
        > |_________________________________________|                                       
          """.trimMargin(">"))
    return readNextInt(" > ==>>")
}
fun playMenu() {
    do {
        val option = mainMenu()
        when (option) {

            1 -> doPlayer()
            2 -> doTeam()


            3 -> logOut()
            else -> println(
                """ | $option is an invalid option
               |   Please enter a valid option (1-2) """.trimMargin()
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

            0 -> return
            else -> println(
                """ |   Invalid option entered: $option
                    |   Please enter a valid option (1-5)""".trimMargin()
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
    val isAdded = ManagerAPI.addPlayer(Player(name, number, height, weight, position, nationality))
    if (isAdded) {
        println("Player Added Successfully")
    } else {
        println("Player Add Failed")
    }
}

fun removePlayer() {
   val index = readNextInt("Enter the Index of the player you wish to remove: ")
    val isRemoved = ManagerAPI.removePlayer(index)

    if (isRemoved) {
        println("Player Removed Successfully")
    }
}

fun listAllPlayers() {
    val printAllPlayers = ManagerAPI.listAllPlayers()

    println(printAllPlayers)
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

//Team functions:
fun doTeam() {
    do {
        val option = teamMenu()
        when (option) {

            1 -> addTeam()
            2 -> removeTeam()
            3 -> listTeam()
            4 -> listPlayerbyIndex()
            5 -> updatePlayer()

            0 -> return
            else -> println(
                """ |   Invalid option entered: $option
                    |   Please enter a valid option (1-5)""".trimMargin()
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
                logger.info { "Team added: $tName by manager: $manager" }
            } else {
                println("Team has failed to be added")
            }
        }

fun removeTeam() {
    val isRemoved = ManagerAPI.removeTeam(0)

    if (isRemoved) {
        println("Team has been Removed Successfully")
    } else {
        println("Team has failed to be removed!")
    }
}

    fun listTeam() {
      val printTeam =  ManagerAPI.listTeam()

        println(printTeam)
    }

