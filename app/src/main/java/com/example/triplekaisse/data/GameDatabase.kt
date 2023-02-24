package com.example.triplekaisse.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.triplekaisse.data.entity.ItemEntity
import com.example.triplekaisse.data.entity.PlayerEntity

@Database(entities = [ItemEntity::class, PlayerEntity::class], version = 1, exportSchema = true)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDatabaseDao
}