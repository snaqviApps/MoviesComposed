package ghar.learn.moviescomposed.core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Movie
import androidx.compose.material.icons.rounded.Upcoming
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ghar.learn.moviescomposed.R
import ghar.learn.moviescomposed.movielist.presentation.MovieListUIEvent
import ghar.learn.moviescomposed.movielist.presentation.MovieListViewModel
import ghar.learn.moviescomposed.movielist.util.Screen

/**
 * First UI (landing) that user will see with category-popular,
 * when launching the app
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {

    val movieListViewModel = hiltViewModel<MovieListViewModel>()
    val movieState = movieListViewModel.movieListState.collectAsState().value

    /** Bottom Navigation */
    val bottomNavController = rememberNavController()

    /**
     * @Scaffold implements the basic material design visual layout structure.
     */
    Scaffold (
        bottomBar = {
                 BottomNavigationBar(
                     bottomNavController = bottomNavController,
                     onEvent = movieListViewModel::onEvent)
        },
        topBar = {
            TopAppBar (
                title = {
                    Text(text = if (movieState.isCurrentPopularScreen) {
                        stringResource(id = R.string.popular)
                    } else {
                        stringResource(id = R.string.upcoming)
                    },
                        fontSize = 20.sp
                    )
                },
                modifier = Modifier.shadow(2.dp),
                colors = topAppBarColors(
                    MaterialTheme.colorScheme.inverseOnSurface
                )
            )
        }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(it)
        ) {

            /** NavController for the two screens (Popular and Upcoming) */
            NavHost(
                navController = bottomNavController,
//                graph =
                startDestination = Screen.PopularMovieList.rout
            ) {
               composable(Screen.PopularMovieList.rout){
//                   PopularMoviesScreen()
               }
                composable(Screen.PopularMovieList.rout){
//                   UpcomingMoviesScreen()
               }
            }
        }
    }
}

@Composable
fun BottomNavigationBar (
    bottomNavController: NavHostController,
    onEvent: (MovieListUIEvent) -> Unit
) {
    val items = listOf(
        BottomItem(
            title = stringResource(R.string.popular),
            icon = Icons.Rounded.Movie
        ),
        BottomItem(
            title = stringResource(R.string.upcoming),
            icon = Icons.Rounded.Upcoming
        )
    )

    /** saves states, for configuration changes */
    val selected = rememberSaveable() {
        mutableIntStateOf(0)            // default: 1st movie will be selected
    }
    NavigationBar (modifier = Modifier.background(MaterialTheme.colorScheme.inverseOnSurface))
    {
        items.forEachIndexed { index, bottomItem ->
            NavigationBarItem (
                selected = selected.intValue == index,
                onClick = {
                    selected.intValue = index     // changing the 'clicked-item' as 'selected-one'
                    when(selected.intValue) {
                        0 -> {
                            onEvent(MovieListUIEvent.Navigate)
                            // remove selectedOne from backStack, to be saved from re-routing to same destination
                            bottomNavController.popBackStack()
                            bottomNavController.navigate(Screen.PopularMovieList.rout)
                        }
                        1 -> {
                            onEvent(MovieListUIEvent.Navigate)
                            bottomNavController.popBackStack()      // remove from backStack
                            bottomNavController.navigate(Screen.UpcomingMovieList.rout)
                        }
                    }
                },
                icon = {
                    Icon (
                        imageVector = bottomItem.icon,
                        contentDescription = bottomItem.title,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                },
                label = {
                    Text(text = bottomItem.title,
                        color = MaterialTheme.colorScheme.onBackground)
                }
            )
        }
    }
}

data class BottomItem(
    val title: String,
    val icon: ImageVector
)