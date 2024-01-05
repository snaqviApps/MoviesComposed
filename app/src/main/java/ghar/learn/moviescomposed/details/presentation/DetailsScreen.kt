package ghar.learn.moviescomposed.details.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ImageNotSupported
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import ghar.learn.moviescomposed.R
import ghar.learn.moviescomposed.movielist.data.remote.MovieApi
import ghar.learn.moviescomposed.movielist.util.RatingBar

/**
 * Details-Screen in needs a'BackStackEntry' (NavBackStackEntry)
 * to get the Id for the movie-data, and more info
 *
 * here we are presenting only:
 * 1. backdrop_image
 * 2. image (poster)
 * 3. tile
 * 4. rating
 * 5. language
 * 6. release-date
 * 7. votes
 * 8. Overview
 *
 * more data can be retrieved but would need more 'api-calls', like running 'trailers'
 * from YouTube for the movie (Github link for this app has that info
 * (with video-Ids)
 * Github:
 * https://github.com/ahmed-guedmioui/Movies-App-Tutorial/tree/part-4
 */
@Composable
fun DetailsScreen() {
    val detailsViewModel = hiltViewModel<DetailsViewModel>()
    val detailsState = detailsViewModel.detailedState.collectAsState().value

    val backDropImageState = rememberAsyncImagePainter (
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieApi.IMAGE_BASE_URL + detailsState.detailedMovie?.backdropPath)
            .size(Size.ORIGINAL)
            .build()
    ).state

    /** Poster Image State */
    val posterImageState = rememberAsyncImagePainter (
        model = ImageRequest.Builder(LocalContext.current)
            .data(MovieApi.IMAGE_BASE_URL + detailsState.detailedMovie?.posterPath)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if (backDropImageState is AsyncImagePainter.State.Error) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(70.dp),
                    imageVector = Icons.Rounded.ImageNotSupported,
                    contentDescription = detailsState.detailedMovie?.title,
                )
            }
        }
        if(backDropImageState is AsyncImagePainter.State.Success) {
            Image(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(22.dp)),
                painter = backDropImageState.painter,
                contentDescription = detailsState.detailedMovie?.title,
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Row(                        // for 2nd ('Poster') Image and other Info like tile and onward
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Box(modifier = Modifier
                .width(160.dp)
                .height(240.dp))
            {                                               // for 2nd Image (Poster)
                if (posterImageState is AsyncImagePainter.State.Error) {
                    Box(
                        Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.primaryContainer),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            modifier = Modifier.size(70.dp),
                            imageVector = Icons.Rounded.ImageNotSupported,
                            contentDescription = detailsState.detailedMovie?.title,
                        )
                    }
                }
                if (posterImageState is AsyncImagePainter.State.Success) {
                    Image(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp)),
                        painter = posterImageState.painter,
                        contentDescription = detailsState.detailedMovie?.title,
                        contentScale = ContentScale.Crop
                    )
                }
            }

            detailsState.detailedMovie?.let { movie ->
                    Column (
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = movie.title,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(
                            modifier = Modifier
                                .padding(start = 16.dp)
                        ) {
                            RatingBar(
                                starsModifier = Modifier.size(18.dp),
                                rating = movie.voteAverage / 2
                            )
                            Text(
                                modifier = Modifier.padding(start = 4.dp),
                                text = movie.voteAverage.toString().take(3),
                                color = Color.LightGray,
                                fontSize = 14.sp,
                                maxLines = 1,
                            )
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = stringResource(R.string.language) + movie.originalLanguage
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = stringResource(R.string.releaseDate) + movie.releaseDate
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            modifier = Modifier.padding(start = 16.dp),
                            text = movie.voteAverage.toString() + stringResource(R.string.voteAverage)
                        )

                    }
                }
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(R.string.overview),
            fontSize = 19.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        detailsState.detailedMovie?.let {
            Text(
                modifier = Modifier.padding(start = 16.dp),
                text = it.overview,
                fontSize = 16.sp
                )
        }


    }


    }

