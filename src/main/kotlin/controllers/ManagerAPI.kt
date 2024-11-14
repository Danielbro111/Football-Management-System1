package controllers

import ie.setu.models.Player


class ManagerAPI {
    private var players = ArrayList<Player>()

    fun addPlayer(player: Player): Boolean {
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
        }




















