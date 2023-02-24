package com.example.triplekaisse.domain.model

data class Game (
    val id: Int,
    val number_of_turn: Int,
    val players: List<Player>,
    val gameType: GameType
)

enum class GameType{
    PARTY,
    QUIZ,
    TEAM,
    EVER_NEVER
}