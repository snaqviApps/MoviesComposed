package ghar.learn.moviescomposed.core.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ghar.learn.moviescomposed.ui.theme.MoviesComposedTheme
import dagger.hilt.android.AndroidEntryPoint
import ghar.learn.moviescomposed.movielist.util.Screen

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MoviesComposedTheme {
                // A surface container using the 'background' color from the theme
                SetSlideBarColor(MaterialTheme.colorScheme.inverseOnSurface)
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // val movieListViewModel = hiltViewModel<MovieListViewModel>()       // checking if backEnd call is working using functions
                    // in 'init' block of viewModel-class

                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.rout
                    ){
                        composable(Screen.Home.rout) {
                            HomeScreen(navController)
                        }

                        composable(Screen.Details.rout + "/{movieId}",
                            arguments = listOf(
                                navArgument("movieId") {
                                    type = NavType.IntType
                                }
                            )
                        ){ navBackStackEntry ->
//                            DetailsScreen(navBackStackEntry)              coming soon
                        }
                    }

                }
            }
        }
    }

    @Composable
    private fun SetSlideBarColor(color: Color) {
        val systemUiController = rememberSystemUiController()
        LaunchedEffect(key1 = color) {
            systemUiController.setSystemBarsColor(color)
        }
    }


}