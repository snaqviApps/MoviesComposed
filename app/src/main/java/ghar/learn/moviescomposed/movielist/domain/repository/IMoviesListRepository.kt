package ghar.learn.moviescomposed.movielist.domain.repository

import ghar.learn.moviescomposed.movielist.domain.model.Movie
import ghar.learn.moviescomposed.movielist.util.Resource
import kotlinx.coroutines.flow.Flow


interface IMoviesListRepository {
    suspend fun getMoviesList(
        forceFetchRemoteMovies : Boolean,
        category: String,
        page: Int
    ) : Flow<Resource<List<Movie>>>

    suspend fun getMovie(id: Int) : Flow<Resource<Movie>>       // for Details-screen
}