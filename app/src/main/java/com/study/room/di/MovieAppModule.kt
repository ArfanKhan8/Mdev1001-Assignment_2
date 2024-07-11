package com.study.room.di

import android.content.Context
import androidx.room.Room
import com.study.room.data.MovieDao
import com.study.room.data.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieAppModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): MovieDatabase{
        return Room.databaseBuilder(
            context.applicationContext,
            MovieDatabase::class.java,
            "movie_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao{
        return movieDatabase.movieDao()
    }
}