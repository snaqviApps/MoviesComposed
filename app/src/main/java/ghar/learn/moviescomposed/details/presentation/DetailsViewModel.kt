package ghar.learn.moviescomposed.details.presentation

import androidx.lifecycle.SavedStateHandle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import ghar.learn.moviescomposed.movielist.domain.repository.IMoviesListRepository
import ghar.learn.moviescomposed.movielist.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val movieListRepository: IMoviesListRepository,

    /**
     * A handle to saved state passed down to androidx.lifecycle.ViewModel.
     * You should use "SavedStateViewModelFactory" if you want to receive this object
     * in ViewModel's constructor.
     */
    savedStateHandle: SavedStateHandle

) : ViewModel() {

    // retrieve info that came from MainActivity
    private val movieId = savedStateHandle.get<Int>("movieId")

    private var _detailedState: MutableStateFlow<DetailsState> = MutableStateFlow(DetailsState())
    val detailedState = _detailedState.asStateFlow()                // backing- field

    init {
        getMovie(movieId ?: -1)
    }
    private fun getMovie(id: Int) {
        viewModelScope.launch {
            _detailedState.update { detailsState ->
                detailsState.copy(isLoading = true)
            }
            movieListRepository.getMovie(id).collectLatest { resource ->
                when(resource) {
                    is Resource.Error -> {
                        _detailedState.update {
                            it.copy(isLoading = false)
                        }
                    }
                    is Resource.Loading -> {
                        _detailedState.update {
                            it.copy(isLoading = resource.isLoading)
                        }
                    }
                    is Resource.Success -> {
                        resource.data?.let { movieRcvd ->
                            _detailedState.update {
                                it.copy(detailedMovie = movieRcvd)
                            }
                        }
                    }
                }
            }
        }
    }

}