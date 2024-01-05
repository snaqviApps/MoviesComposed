package ghar.learn.moviescomposed.movielist.presentation

import ghar.learn.moviescomposed.movielist.domain.model.Movie

data class MovieListState (
    val isLoading: Boolean = false,
    val isCurrentPopularScreen: Boolean = false,

    val upcomingMovieListPage: Int = 1,
    val popularMovieListPage: Int = 1,
    val popularMovieList: List<Movie> = emptyList(),
    val upcomingMovieList: List<Movie> = emptyList(),
)