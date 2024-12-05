package controllers

import ie.setu.models.Player
import ie.setu.models.Team
import persistence.Serializer


class ManagerAPI(serializerType: Serializer) {
    var players = ArrayList<Player>()
    var teams = ArrayList<Team>()
    private var serializer: Serializer = serializerType


    fun numberOfTeams() = teams.size


    fun TeamTotalValue(index: Int): Double {
        return if (isValidListIndex(index, teams)) {
            teams[index].calculateTeamValue()
        } else {
            0.0
        }
    }

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

    fun addTeam(team: Team): Boolean {
        return teams.add(team)
    }

    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    fun removePlayer(index: Int): Player? {
        return if (isValidListIndex(index, players)) {
            players.removeAt(index)

        } else {
            println("Invalid index: $index")
            null
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
    fun removeTeam(index: Int): Team? {
        return if (index in teams.indices) {
            teams.removeAt(index)
        } else {
            null
        }
    }

    fun updateTeam(index: Int, updatedTeam: Team): Boolean {
        return if (index in teams.indices) {
            teams[index] = updatedTeam
            true
        } else {
            false
        }
    }


    fun numberOfPlayers() = players.size


    fun listTeam(): String {
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
            listAllTeams
        }
    }


fun findTeam(index: Int): Team? {
    return if (isValidListIndex(index, teams)) {
        teams[index]
    } else null
}

    fun addPlayerToTeam(team: Team, player: Player): Boolean {
        team.addPlayer(player)
        return true
    }

    fun listFullTeam() {
        if(teams.isEmpty()) {
            println("No teams found.")
            return
        }

        val team = teams[0]
        println("""
           > ***    Team Information    ***
           > Team Name: ${team.tName.uppercase()}
           > Manager: ${team.manager.uppercase()}
           > Captain: ${team.captain.uppercase()}
           > League: ${team.league.uppercase()}
           > Trophies: ${team.trophies}
           > Number of players: ${team.players.size}
           >
           > Players:
""".trimMargin(">"))

        if (team.players.isEmpty()) {
            println("> No players are in the team.")
        } else {
            for (player in team.players) {
                println("""
                >       
                >   Name: ${player.name.uppercase()}
                >   Number: ${player.number}
                >   Position: ${player.position.uppercase()}
                >   Height: ${player.height} m
                >   Weight: ${player.weight} kg
                >   Nationality: ${player.nationality.uppercase()}
                >   
            """.trimMargin(">"))
            }
        }
    }




    @Throws(Exception::class)
    fun load() {
        players = serializer.read() as ArrayList<Player>
        teams = serializer.read() as ArrayList<Team>
        println("loaded successfully.")
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(players)
        serializer.write(teams)
        println("saved successfully.")
    }
}