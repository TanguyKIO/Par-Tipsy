package com.example.triplekaisse.domain.model

class NormalGameMode : GameModeStrategy {

    final val gmCode = 0
    override fun generateQuestion(
        players: MutableList<Player>,
        questions: MutableList<Item>,
        numberItem: Int
    ): List<Item> {
        var nbQuest = numberItem
        if (numberItem > questions.size) {
            nbQuest = questions.size
        }
        val items = mutableListOf<Item>()
        var item: Item
        questions.shuffle()
        var playersBuffer = players.toMutableList()
        val choosedPlayers = mutableListOf<Player>()
        var rand: Int
        for (i in 0 until nbQuest) {
            item = questions[i]
            if (item.nbPlaceholder <= players.size) {
                choosedPlayers.clear()
                if (item.nbPlaceholder <= players.size) {
                    for (j in 0 until item.nbPlaceholder) {
                        if (playersBuffer.size < item.nbPlaceholder) playersBuffer =
                            players.toMutableList()
                        rand = (0 until playersBuffer.size).random()
                        while (choosedPlayers.contains(playersBuffer[rand])) {
                            rand = (0 until playersBuffer.size).random()
                        }
                        choosedPlayers.add(playersBuffer[rand])
                        playersBuffer.removeAt(rand)
                    }
                    item.description = when (item.nbPlaceholder) {
                        0 -> item.description
                        1 -> String.format(item.description, choosedPlayers[0].name)
                        2 -> String.format(
                            item.description,
                            choosedPlayers[0].name,
                            choosedPlayers[1].name
                        )
                        3 -> String.format(
                            item.description,
                            choosedPlayers[0].name,
                            choosedPlayers[1].name,
                            choosedPlayers[2].name
                        )
                        4 -> String.format(
                            item.description,
                            choosedPlayers[0].name,
                            choosedPlayers[1].name,
                            choosedPlayers[2].name,
                            choosedPlayers[3].name
                        )
                        else -> item.description
                    }
                    items.add(item)
                }
            }
        }
        return items
    }
}