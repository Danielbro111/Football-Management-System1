package controllers

import ie.setu.models.Player
import ie.setu.models.Team
import persistence.Serializer


class ManagerAPI(serializerType: Serializer) {
    var players = ArrayList<Player>()
    var teams = ArrayList<Team>()
    private var serializer: Serializer = serializerType


    fun numberOfTeams() = teams.size

    fun addPlayer(player: Player): Boolean {
        return players.add(player)

    }

    fun addTeam(team: Team): Boolean {
        return teams.add(team)
    }

    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun removePlayer(index: Int): Boolean {
        return if (isValidListIndex(index, players)) {
            players.removeAt(index)
            true
        } else {
            println("Invalid index: $index")
            false
        }

    }

    fun listAllPlayers(): Any {
        return if (players.isEmpty()) {
            "No players found"
        } else {
            var listAllPlayers = ""
            for (i in players.indices) {
                listAllPlayers += "${i}: ${players[i]} \n"
            }
            println()
            println(listAllPlayers.trimMargin())
            println()
        }


    }

    fun listPlayerbyIndex(index: Int): Any {
        return if (players.isEmpty()) {
            "No players found"

        } else if (!isValidListIndex(index, players)) {
            "Please enter a valid index"

        } else

            players[index]

    }


    fun findPlayer(index: Int): Player? {
        return if (isValidListIndex(index, players)) {
            players[index]
        } else null
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

    //Team Functions
    fun removeTeam(index: Int): Boolean {
        return if (index in teams.indices) {
            teams.removeAt(index)
            true
        } else {
            false
        }
    }

    fun numberOfPlayers() = players.size


    fun listTeam(): Any {
        return if (teams.isEmpty()) {
            println()
            "No teams found"
        } else {
            var listAllTeams = ""
            for (team in teams) {
                listAllTeams += """
                    
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
            println(listAllTeams.trimMargin())
            println()
        }
    }





    @Throws(Exception::class)
    fun load() {
        players = serializer.read() as ArrayList<Player>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(players)
    }


    fun addPlayerToTeam(team: Team, player: Player): Boolean {
        team.addPlayer(player)
        return true
    }

}









