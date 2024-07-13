package com.study.room.data

sealed class AppEvent {
    data class GetMovieById(val id: Int): AppEvent()
    data class AddMovie(val movie: Movie): AppEvent()
    data class UpdateMovie(val movie: Movie): AppEvent()
    data class DeleteMovie(val movie: Movie): AppEvent()
    data object OnEditCancel: AppEvent()
}