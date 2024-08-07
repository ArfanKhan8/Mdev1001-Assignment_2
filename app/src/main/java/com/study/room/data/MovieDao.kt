package com.study.room.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.study.room.pages.MoviesAdapter
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Query("SELECT * From movie")
    fun getAllMovies(): Flow<List<Movie>>

    @Query("SELECT * FROM movie where id = :id")
    fun getMovieById(id: Int): List<Movie>

    @Upsert
    fun upsert(movie: Movie)

    @Insert
    fun insertMovies(movies: List<Movie>)
    @Delete
    fun delete(movie: Movie)


}