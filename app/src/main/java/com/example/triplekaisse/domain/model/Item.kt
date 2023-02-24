package com.example.triplekaisse.domain.model

data class Item (
    val id: Int?,
    val type: Type,
    val theme: Theme,
    var description: String,
    val answer: String?,
    val nbPlaceholder: Int,
    var active: Int,
    val format: String
)

enum class Type {
    ACTION,
    QUESTION,
    QUIZZ,
    ROLE,
    DUAL
}

enum class Theme {
    HOT,
    FUN,
    KNOWLEDGE,
    TEAM,
    DRINK,
    MUSIC
}