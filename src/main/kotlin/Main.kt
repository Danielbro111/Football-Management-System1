package ie.setu
import io.github.oshai.kotlinlogging.KotlinLogging
import controllers.ManagerAPI
import ie.setu.models.Player
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
    println( """
        > *********FOOTBALL MANAGEMENT SYSTEM*********         
        > |                                          |
        > | 1 -> Access Player Menu                  |                              
        > | 2 -> Access Team Menu                    |
        > |__________________________________________|  
    """.trimMargin(">"))
    return readNextInt(" > ==>>")
}

fun playerMenu() : Int {
    println( """
        > ********FOOTBALL MANAGEMENT SYSTEM*********         
        > |            ***Player Menu***             |
        > | 1 -> Add a Player                        |
        > | 2 -> Remove a Player                     |
        > | 3 -> Update a Player                     |
        > | 4 -> Show a player (Using Player Number) |
        > | 5 -> Show all Players                    |
        > |                                          |
        > | 6 -> Save a Player                       |                                                        
        > |__________________________________________|  
          """.trimMargin(">"))
    return readNextInt(" > ==>>")
}

fun teamMenu() : Int {
    println( """
        >  *******FOOTBALL MANAGEMENT SYSTEM********         
        > |            ***Team Menu***              |
        > | 1 -> Add a team                         |
        > | 2 -> Remove a team                      |
        > | 3 -> Update a team                      |
        > | 4 -> Show a team                        |
        > | 5 -> Show all Teams                     |
        > |                                         |
        > | 6 -> Save a team                        |
        > |_________________________________________|                                       
          """.trimMargin(">"))
    return readNextInt(" > ==>>")
}


fun playMenu() {
    do {
        val option = mainMenu()
        when (option) {

            1 -> doPlayer()
            //2 -> printTeam()


            3 -> logOut()
            else -> println(
           """ | $option is an invalid option
               |   Please enter a valid option (1-2) """.trimMargin()
            )
        }
    } while (true)
}

fun doPlayer() {
    do {
        val option = playerMenu()
        when (option) {

            1 -> addPlayer()
            2 -> removePlayer()



            0 -> logOut()
            else -> println(
                """ |   Invalid option entered: $option
               |   Please enter a valid option (1-5)""".trimMargin()
            )
        }
    } while (true)
}



fun addPlayer() {
    val name = readNextLine("Enter the Players name:")
    val number = readNextLine("Enter the Players number:")
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

fun logOut() {
    println("Logging out")
    exit(0)
}
