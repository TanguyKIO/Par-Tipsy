package com.example.triplekaisse.data


import com.example.triplekaisse.data.entity.ItemEntity
import com.example.triplekaisse.domain.model.*
import java.util.function.Predicate
import javax.inject.Inject

class GameRepository @Inject constructor(private val gameDao: GameDatabaseDao) {
    private var players: MutableList<Player> = mutableListOf()
    private var numberItem: Int = 0
    private var gameModeStrategy: GameModeStrategy = NormalGameMode()
    private var gmCode = 0
    fun getQuestions(): List<Item> {
        val items = when (gmCode) {
            0 -> gameDao.getActiveItems().map { itemEntity ->
                itemEntity.toItem() }
            1 -> gameDao.getActiveTeamItems().map { itemEntity ->
                itemEntity.toItem() }
            else -> gameDao.getActiveItems().map { itemEntity ->
                itemEntity.toItem() }
        }

        return gameModeStrategy.generateQuestion(players, items as MutableList<Item>, numberItem)
    }

    fun setGameMode(gameMode: GameMode) {
        when (gameMode) {
            GameMode.NORMAL -> {
                gameModeStrategy = NormalGameMode()
                gmCode = 0
            }
            GameMode.TEAM -> {
                gameModeStrategy = TeamGameMode()
                gmCode = 1
            }
        }
    }

    fun getItems(): List<Item> {
        val items = gameDao.getItems().map { itemEntity ->
            itemEntity.toItem()
        }
        return items
    }

    fun setNumberItem(number: Int) {
        numberItem = number
    }

    fun addItem(item: Item) {
        var itemEntity = item.toItemEntity()
        gameDao.insertItem(itemEntity)
    }

    fun delItem(id: Int) {
        gameDao.deleteItemById(id)
    }

    fun updateItem(item: Item) {
        var itemEntity = item.toItemEntity()
        gameDao.updateItem(itemEntity)
    }

    fun getPlayers(): List<Player> {
        return players
    }

    fun addPlayer(player: Player) {
        players.add(player)
    }

    fun removePlayer(player: Player) {
        players.remove(player)
    }

    fun removeTeam() {
        var predicate = Predicate<Player> {it.id == 0}
        players.removeIf(predicate)
    }
}

private fun ItemEntity.toItem() = Item(
    id = id,
    description = text,
    answer = null,
    type = when (type) {
        1 -> Type.ACTION
        2 -> Type.DUAL
        3 -> Type.QUIZZ
        4 -> Type.QUESTION
        5 -> Type.ROLE
        else -> Type.ACTION
    },
    theme = when (theme) {
        1 -> Theme.FUN
        2 -> Theme.HOT
        3 -> Theme.KNOWLEDGE
        4 -> Theme.TEAM
        else -> Theme.FUN
    },
    nbPlaceholder = nbPlaceHolder,
    active = active,
    format = format
)

private fun Item.toItemEntity() = ItemEntity(
    id = id,
    text = description,
    type = when (type) {
        Type.ACTION -> 1
        Type.DUAL -> 2
        Type.QUIZZ -> 3
        Type.QUESTION -> 4
        Type.ROLE -> 5
    },
    theme = when (theme) {
        Theme.FUN -> 1
        Theme.HOT -> 2
        Theme.KNOWLEDGE -> 3
        Theme.TEAM -> 4
        Theme.DRINK -> 5
        Theme.MUSIC -> 6
    },
    nbPlaceHolder = nbPlaceholder,
    active = active,
    format = format
)