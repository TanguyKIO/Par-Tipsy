package com.example.triplekaisse.domain.model

class TeamGameMode : GameModeStrategy {

    private var team1Buffer: MutableList<Player> = mutableListOf()
    private var team2Buffer: MutableList<Player> = mutableListOf()

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
        val team1Name = players[players.size - 2].name
        val team2Name = players[players.size - 1].name
        players.removeAt(players.size-1)
        players.removeAt(players.size-1)
        var team1players = mutableListOf<Player>()
        var team2players = mutableListOf<Player>()
        val count = players.size
        for (i in 0 until count) {
            if (i % 2 == 0) {
                team1players.add(players[i])
            } else {
                team2players.add(players[i])
            }
        }
        var randTeam: Int

        team1Buffer.addAll(team1players)
        team2Buffer.addAll(team2players)
        for (i in 0 until nbQuest) {
            item = questions[i]
            val format = item.format
            val placeholders = mutableListOf<String>()
            if (format.length <= players.size + 2) {
                // Random pour alterner les remplissages en fonction des équipes
                randTeam = (0..1).random()
                var team1 = Team(team1Name, team1players)
                var team2 = Team(team2Name, team2players)
                if(randTeam==1) {
                    team1 = Team(team2Name, team2players)
                    team2 = Team(team1Name, team1players)
                }
                val chosedPlayers = playerAutoChooser(format, team1, team2)
                var aCount = 0
                var bCount = 0
                for (j in format.indices) {
                    when(format[j]) {
                        'A' -> {
                            placeholders.add(chosedPlayers.first[aCount].name)
                            aCount++
                        }
                        'B' -> {
                            placeholders.add(chosedPlayers.second[bCount].name)
                            bCount++
                        }
                        '1' -> placeholders.add(team1.name)
                        '2' -> placeholders.add(team2.name)
                    }
                }
                item.description = when (item.nbPlaceholder) {
                    0 ->
                        item.description
                    1 -> String.format(item.description, placeholders[0])
                    2 -> String.format(
                        item.description,
                        placeholders[0],
                        placeholders[1]
                    )
                    3 -> String.format(
                        item.description,
                        placeholders[0],
                        placeholders[1],
                        placeholders[2]
                    )
                    4 -> String.format(
                        item.description,
                        placeholders[0],
                        placeholders[1],
                        placeholders[2],
                        placeholders[3]
                    )
                    5 -> String.format(
                        item.description,
                        placeholders[0],
                        placeholders[1],
                        placeholders[2],
                        placeholders[3],
                        placeholders[4]
                    )
                    6 -> String.format(
                        item.description,
                        placeholders[0],
                        placeholders[1],
                        placeholders[2],
                        placeholders[3],
                        placeholders[4],
                        placeholders[5]
                    )
                    7 -> String.format(
                        item.description,
                        placeholders[0],
                        placeholders[1],
                        placeholders[2],
                        placeholders[3],
                        placeholders[4],
                        placeholders[5],
                        placeholders[6]
                    )
                    8 -> String.format(
                        item.description,
                        placeholders[0],
                        placeholders[1],
                        placeholders[2],
                        placeholders[3],
                        placeholders[4],
                        placeholders[5],
                        placeholders[6],
                        placeholders[7]
                    )
                    else -> item.description
                }
                items.add(item)
            }
        }
        return items
    }

    private fun playerAutoChooser(
        format: String,
        team1: Team,
        team2: Team
    ): Pair<MutableList<Player>, MutableList<Player>> {
        var rand: Int
        val choosedTeam1Players = mutableListOf<Player>()
        val choosedTeam2Players = mutableListOf<Player>()
        val team1PlayerCount = format.filter { it == 'A' }.count()
        val team2PlayerCount = format.filter { it == 'B' }.count()
        for (j in 0 until team1PlayerCount) {
            if (team1Buffer.size < team1PlayerCount) {
                team1Buffer.clear()
                team1Buffer.addAll(team1.players as MutableList<Player>)
            }
            rand = (0 until team1Buffer.size).random()
            while (choosedTeam1Players.contains(team1Buffer[rand])) {
                rand = (0 until team1Buffer.size).random()
            }
            choosedTeam1Players.add(team1Buffer[rand])
            team1Buffer.removeAt(rand)
        }
        for (j in 0 until team2PlayerCount) {
            if (team2Buffer.size < team2PlayerCount) {
                team2Buffer.clear()
                team2Buffer.addAll(team2.players as MutableList<Player>)
            }
            rand = (0 until team2Buffer.size).random()
            while (choosedTeam2Players.contains(team2Buffer[rand])) {
                rand = (0 until team2Buffer.size).random()
            }
            choosedTeam2Players.add(team2Buffer[rand])
            team2Buffer.removeAt(rand)
        }
        return Pair(choosedTeam1Players, choosedTeam2Players)
    }
}