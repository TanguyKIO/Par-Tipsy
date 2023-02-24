package com.example.triplekaisse.di

import android.content.Context
import androidx.room.Room
import com.example.triplekaisse.data.GameDatabase
import com.example.triplekaisse.data.GameDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Provides
    fun provideGameDatabseDao(db: GameDatabase): GameDatabaseDao {
        return db.gameDao()
    }

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): GameDatabase {
        return Room.databaseBuilder(
            appContext,
            GameDatabase::class.java,
            "GameDB")
            .createFromAsset("game.db")
            .build()
    }
}