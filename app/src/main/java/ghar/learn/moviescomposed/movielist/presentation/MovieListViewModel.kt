package ghar.learn.moviescomposed.movielist.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ghar.learn.moviescomposed.movielist.domain.model.Movie
import ghar.learn.moviescomposed.movielist.domain.repository.IMoviesListRepository
import ghar.learn.moviescomposed.movielist.util.Category
import ghar.learn.moviescomposed.movielist.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(
    private val iMovieListRepository: IMoviesListRepository
) : ViewModel() {
    private val _movieListState = MutableStateFlow(MovieListState())
    val movieListState: StateFlow<MovieListState> = _movieListState.asStateFlow()

    init {
        getPopularMovieList(false)
        getUpcomingMovieList(false)
    }

    /**
     * To receive 'event' from User
     */
    fun onEvent(event: MovieListUIEvent) {
        when(event){
            MovieListUIEvent.Navigate -> {
                _movieListState.update {
                    it.copy(
                        isCurrentPopularScreen = !movieListState.value.isCurrentPopularScreen
                    )
                }
            }
            is MovieListUIEvent.Paginate -> {
                if(event.category == Category.POPULAR) {
                    getPopularMovieList(true)
                } else if (event.category == Category.UPCOMING) {
                    getUpcomingMovieList(true)
                }
            }
        }
    }
    private fun getPopularMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update { movieListState ->
                movieListState.copy(isLoading = true)
            }
            iMovieListRepository.getMoviesList(
                forceFetchFromRemote,
                Category.POPULAR,
                movieListState.value.popularMovieListPage
            ).collectLatest { result: Resource<List<Movie>> ->
                when(result){
                    is Resource.Error -> {
                        _movieListState.update { movieListState ->
                            movieListState.copy(isLoading = false)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { popularReceivedList->
                            _movieListState.update { popMovieListState ->
                                popMovieListState.copy(
                                    popularMovieList = movieListState.value.popularMovieList        // popularMovieList is read-only
                                            + popularReceivedList.shuffled(),
                                    popularMovieListPage = movieListState.value.popularMovieListPage + 1
                                )
                            }
                        }
                    }
                    is Resource.Loading -> {
                        _movieListState.update { movieListState ->
                            movieListState.copy(isLoading = result.isLoading)
                        }
                    }
                }
            }
        }
    }

    private fun getUpcomingMovieList(forceFetchFromRemote: Boolean) {
        viewModelScope.launch {
            _movieListState.update { movieListState ->
                movieListState.copy(isLoading = true)
            }

            iMovieListRepository.getMoviesList(
                forceFetchFromRemote,
                Category.UPCOMING,
                movieListState.value.upcomingMovieListPage
            ).collectLatest { result ->
                when(result) {
                    is Resource.Error -> {
                        _movieListState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is Resource.Success -> {
                        result.data?.let { upcomingReceivedList ->
                            _movieListState.update { upMovieListState ->
                                upMovieListState.copy(
                                    upcomingMovieList = movieListState.value.upcomingMovieList
                                    + upcomingReceivedList.shuffled(),
                                    upcomingMovieListPage = movieListState.value.upcomingMovieListPage + 1
                                )
                            }
                        }
                    }
                    is Resource.Loading -> {
                        _movieListState.update {
                            it.copy(isLoading = result.isLoading)
                        }
                    }
                }
            }

        }
    }

}