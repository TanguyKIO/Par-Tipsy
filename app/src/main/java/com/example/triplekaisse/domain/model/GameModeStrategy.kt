package com.example.triplekaisse.domain.model

import android.content.Context

interface GameModeStrategy {
    fun generateQuestion(players: MutableList<Player>, questions: MutableList<Item>, numberItem: Int): List<Item>
}