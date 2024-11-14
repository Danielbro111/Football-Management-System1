package controllers

import ie.setu.models.Player


class ManagerAPI {
    var players = ArrayList<Player>()

    fun addPlayer(player: Player):  Boolean {
        return players.add(player)

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
                println(listAllPlayers.trimMargin() )
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


        if ((playerFound  != null) && (players != null)) {
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
    }























