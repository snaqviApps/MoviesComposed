package ghar.learn.moviescomposed.details.presentation

import ghar.learn.moviescomposed.movielist.domain.model.Movie

data class DetailsState (
    val isLoading: Boolean = false,
    val detailedMovie: Movie? = null
)