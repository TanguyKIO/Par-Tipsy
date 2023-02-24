package com.example.triplekaisse.di

import com.example.triplekaisse.data.GameDatabaseDao
import com.example.triplekaisse.data.GameRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DataModule {
    @Provides
    @Singleton
    fun provideRepo(gameDatabaseDao: GameDatabaseDao): GameRepository {
        return GameRepository(gameDatabaseDao)
    }
}