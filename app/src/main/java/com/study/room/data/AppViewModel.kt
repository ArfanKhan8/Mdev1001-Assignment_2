package com.study.room.data

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

data class AppState(
    val movies: List<Movie> = mutableListOf(),
    val currentMovie: Movie? = null,
)


@HiltViewModel
class AppViewModel @Inject constructor(
    private val movieDao: MovieDao
): ViewModel() {
    private val _state = MutableStateFlow<AppState>(AppState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
                movieDao.getAllMovies().collect{ movies ->
                    _state.update {
                        it.copy(
                        movies = movies
                        )
                }
            }

            Log.d("logger", "Movies after viewModel init: ${state.value.movies.toString()}")
        }
    }

    fun onEvent(event: AppEvent){
        when(event){
            is AppEvent.AddMovie -> {

            }
            is AppEvent.GetMovieById -> {

            }

            is AppEvent.UpdateMovie -> {

            }
        }
    }

}