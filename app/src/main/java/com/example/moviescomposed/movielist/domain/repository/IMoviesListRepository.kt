package com.example.moviescomposed.movielist.domain.repository

import com.example.moviescomposed.movielist.domain.model.Movie
import com.example.moviescomposed.movielist.util.Resource
import kotlinx.coroutines.flow.Flow


interface IMoviesListRepository {
    suspend fun getMoviesList(
        forceFetchRemoteMovies : Boolean,
        category: String,
        page: Int
    ) : Flow<Resource<List<Movie>>>

    suspend fun getMovie(id: Int) : Flow<Resource<Movie>>       // for Details-screen
}