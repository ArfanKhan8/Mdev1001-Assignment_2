package com.study.room.data

import android.content.Context
import android.provider.CalendarContract.Instances
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Movie::class], version = 1)
abstract  class MovieDatabase:RoomDatabase() {

    abstract fun movieDao(): MovieDao

}