package ie.setu
import io.github.oshai.kotlinlogging.KotlinLogging



private val logger = KotlinLogging.logger {}


fun main() {

 playMenu()

}

fun mainMenu(){
    println( """
        > *********FOOTBALL MANAGEMENT SYSTEM*********         
        > |                                          |
        > | 1 -> Access Player Menu                  |                              
        > | 2 -> Access Team Menu                    |
        > |__________________________________________|  
    """.trimMargin(">"))
}

fun playerMenu(){
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
}

fun teamMenu() {
    println( """
        >  *******FOOTBALL MANAGEMENT SYSTEM********         
        > |            ***Team Menu***              |
        > |                                         |
        > | 1 -> Add a team                         |
        > | 2 -> Remove a team                      |
        > | 3 -> Update a team                      |
        > | 4 -> Show a team                        |
        > | 5 -> Show all Teams                     |
        > |                                         |
        > | 6 -> Save a team                        |
        > |_________________________________________|                                       
          """.trimMargin(">"))
}

fun playMenu() {

    playerMenu()
    mainMenu()
    teamMenu()
}

