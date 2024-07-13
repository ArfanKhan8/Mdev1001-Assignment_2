package com.study.room.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.study.room.R
import com.study.room.data.Movie
import com.study.room.data.MovieDao
import com.study.room.data.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStreamReader
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MovieAppModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(@ApplicationContext context: Context): MovieDatabase{
        if (context.getDatabasePath("moviesDB").exists()){
            return Room.databaseBuilder(
                context,
                MovieDatabase::class.java , "moviesDB"
            ).build()
        }
        val db = Room.databaseBuilder(
            context,
            MovieDatabase::class.java,
            "moviesDB"
        ).build()

        val inputStream = context.resources.openRawResource(R.raw.movies)
        val reader = InputStreamReader(inputStream)
        val itemType = object : TypeToken<List<Movie>>() {}.type
        val data = Gson().fromJson<List<Movie>>(reader, itemType)

        CoroutineScope(Dispatchers.IO).launch {
            db.movieDao().insertMovies(data)
        }
        return db
    }

    @Provides
    @Singleton
    fun provideMovieDao(movieDatabase: MovieDatabase): MovieDao{
        return movieDatabase.movieDao()
    }
}