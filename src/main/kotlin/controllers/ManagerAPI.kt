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

    fun removePlayer(index: Int) : Boolean {
        return if (isValidListIndex(index, players)) {
            players.removeAt(index)
            true
        } else {
            println("Invalid index: $index")
            false
        }
    }
}



