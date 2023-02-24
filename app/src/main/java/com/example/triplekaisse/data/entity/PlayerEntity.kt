package com.example.triplekaisse.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "players")
data class PlayerEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") val firstName: String,
    @ColumnInfo(name = "sex") val sex: Int?
)