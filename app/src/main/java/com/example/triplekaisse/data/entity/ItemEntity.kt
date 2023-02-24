package com.example.triplekaisse.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "type") val type: Int,
    @ColumnInfo(name = "theme") val theme: Int,
    @ColumnInfo(name = "text") val text: String,
    @ColumnInfo(name = "nb_placeholders") val nbPlaceHolder: Int,
    @ColumnInfo(name = "active") val active: Int,
    @ColumnInfo(name = "format") val format: String,
)