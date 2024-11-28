package ie.setu.models

data class Team (
    var tName: String,
    var manager: String,
    var captain: String,
    var league: String,
    var trophies: Int,
    var players : MutableSet<Player> = mutableSetOf(),
    var teamId: Int= 0,) {


    private var lastPlayerNumber = 0
    private fun getPlayerNumber() = lastPlayerNumber++

    fun addPlayer(player: Player) : Boolean {
        player.number = getPlayerNumber()
        return players.add(player)
    }


    fun findPlayer(number: Int): Player? {
        return players.find { player -> player.number == number }
    }

    fun deletePlayer(number: Int): Boolean {
        return players.removeIf { item -> item.number == number }
    }


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

    fun listPlayers(): Any {
        return if (players.isEmpty()) {
            println()
            "No teams found"
        } else {
            var listAllPlayers = ""
            for (player in players) {
                listAllPlayers += """
                    
       |*** Player Information ***
       | Name: ${player.name.uppercase()}                        
       | Number: ${player.number}                        
       | Height: ${player.height}                        
       | Weight: ${player.weight}                          
       | Position: ${player.position.uppercase()}    
       | Nationality: ${player.nationality.uppercase()}            
    """.trimIndent()
            }
            println()
            println(listAllPlayers.trimMargin())
            println()
        }
    }
}