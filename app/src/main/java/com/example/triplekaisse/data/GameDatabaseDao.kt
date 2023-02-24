package com.example.triplekaisse.data

import androidx.room.*
import com.example.triplekaisse.data.entity.ItemEntity
import com.example.triplekaisse.data.entity.PlayerEntity
import com.example.triplekaisse.domain.model.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface GameDatabaseDao {

    @Insert
    fun insertItem(item: ItemEntity)

    @Insert
    fun insertPlayer(player: PlayerEntity)

    @Delete
    fun deleteItem(item: ItemEntity)

    @Delete
    fun deletePlayer(player: PlayerEntity)

    @Query("SELECT * FROM items WHERE id = :id")
    fun getItem(id: Int): ItemEntity

    @Query("SELECT * FROM items")
    fun getItems(): List<ItemEntity>

    @Query("SELECT * FROM items WHERE active > 0 and theme != 4")
    fun getActiveItems(): List<ItemEntity>

    @Query("SELECT * FROM items WHERE active > 0 and theme = 4")
    fun getActiveTeamItems(): List<ItemEntity>

    @Query("SELECT * FROM items WHERE type = :type")
    fun getItemsByType(type: Int): List<ItemEntity>

    @Query("DELETE FROM items WHERE id = :id")
    fun deleteItemById(id: Int)

    @Update
    fun updateItem(item: ItemEntity)

}