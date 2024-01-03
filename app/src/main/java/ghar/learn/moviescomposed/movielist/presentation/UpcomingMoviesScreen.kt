package ghar.learn.moviescomposed.movielist.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import ghar.learn.moviescomposed.movielist.presentation.component.MovieItem
import ghar.learn.moviescomposed.movielist.util.Category

@Composable
fun UpcomingMoviesScreen(
    movieListState: MovieListState,
    navHostController: NavHostController,     // takes to MainActivity, not Bottom-nav-controller
    onEvent: (MovieListUIEvent) -> Unit
) {
    if(movieListState.upcomingMovieList.isEmpty()) {    // if list is Empty
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        /**
         * "LazyVerticalGrid" is equivalent to 'RecyclerView' in XML-based vertical-Layout.
         *  It composes only visible rows of the grid
         */
        LazyVerticalGrid (
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 4.dp)
        ) {
            items (movieListState.upcomingMovieList.size) {index ->
                MovieItem(movie = movieListState.upcomingMovieList[index],
                    navHostController = navHostController
                )
                Spacer(modifier = Modifier.height(16.dp))

                /** Paginating */
                if(index >= movieListState.upcomingMovieList.size - 1 &&
                    !movieListState.isLoading) {
                    onEvent(MovieListUIEvent.Paginate(Category.UPCOMING))
                }

            }
        }
    }
}
