package ghar.learn.moviescomposed.movielist.presentation

sealed interface MovieListUIEvent {
    data class Paginate(val category: String) : MovieListUIEvent
    object Navigate : MovieListUIEvent

}
