package ghar.learn.moviescomposed

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import ghar.learn.moviescomposed.ui.theme.MoviesComposedTheme
import dagger.hilt.android.AndroidEntryPoint
import ghar.learn.moviescomposed.movielist.presentation.MovieListViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesComposedTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val movieListViewModel = hiltViewModel<MovieListViewModel>()       // checking if backEnd call is working using functions in 'init' block of viewModel-class

                }
            }
        }
    }
}