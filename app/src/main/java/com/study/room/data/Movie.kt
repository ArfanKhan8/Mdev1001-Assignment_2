package com.study.room.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Movie(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val title: String,
    val studio: String?,
    val posterUrl: String?,
    val rating: Double?,
)
