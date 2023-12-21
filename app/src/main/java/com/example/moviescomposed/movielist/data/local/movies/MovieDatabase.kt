package com.example.moviescomposed.movielist.data.local.movies

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract val movieDAO: MovieDAO
}