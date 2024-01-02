package ghar.learn.moviescomposed.movielist.data.repository

import android.net.http.HttpException
import android.os.Build
import androidx.annotation.RequiresExtension
import ghar.learn.moviescomposed.movielist.data.local.movies.MovieDatabase
import ghar.learn.moviescomposed.movielist.data.local.movies.MovieEntity
import ghar.learn.moviescomposed.movielist.data.mappers.toMovie
import ghar.learn.moviescomposed.movielist.data.mappers.toMovieEntity
import ghar.learn.moviescomposed.movielist.data.remote.MovieApi
import ghar.learn.moviescomposed.movielist.domain.model.Movie
import ghar.learn.moviescomposed.movielist.domain.repository.IMoviesListRepository
import ghar.learn.moviescomposed.movielist.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class MoviesListRepositoryImpl @Inject constructor(
    private val movieApi: MovieApi,
    private val movieDatabase: MovieDatabase
) : IMoviesListRepository {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override suspend fun getMoviesList(
        forceFetchRemoteMovies: Boolean,
        category: String,
        page: Int
    ): Flow<Resource<List<Movie>>> {

       // below flow executes multiple
       return flow {
           emit(Resource.Loading(true))                          // 1st-Action

           /**
            * if DB is not-Empty, and Forced-Remote call is not in place, (it won't be the case when
            * App is launched first time, then this block comes into place and App retrieves movies from
            * Database
            */
           val localMoviesList = movieDatabase.movieDAO.getMovieByCategory(category)
           val isLoadingFromLocalDB = localMoviesList.isNotEmpty() && !forceFetchRemoteMovies
           if(isLoadingFromLocalDB) {
               emit(Resource.Success(                                       // 2nd-Action, if executed
                   data = localMoviesList.map {movieEntity ->               // 'Mapper' to 'Movie'
                       movieEntity.toMovie(category)
                   }
               ))
               emit(Resource.Loading(false))                      // 3rd-Action, if executed
               return@flow
           }

           /**
            * Fetch MoviesList from Remote using API, if DB is empty
            * The screen however would still show the movie FROM Database,
            * as the API-fetched movies would still be saved to DB first, so
            * Singe-Source-of-Truth is the Database
            *
            */
           val moviesFromAPICall = try {
               movieApi.getMovieList(category, page)
           } catch (e: IOException) {
               e.printStackTrace()
               emit(Resource.Error(message = "Error loading page"))
               return@flow
           }
           catch (e: HttpException) {
               e.printStackTrace()
               emit(Resource.Error(message = "Error loading page"))
               return@flow
           }
           catch (e: Exception) {
               e.printStackTrace()
               emit(Resource.Error(message = "Error loading page"))
               return@flow
           }

           val movieEntities = moviesFromAPICall.results.let { moviesList ->
               moviesList.map {movieDTO ->
                   movieDTO.toMovieEntity(category)
               }
           }.apply {
               movieDatabase.movieDAO.upsertMoviesList(this)    // adding to Database
           }
           emit(Resource.Success(                                         // 2nd-Action, if executed
               movieEntities.map { it.toMovie(category = category) }
           ))
           emit(Resource.Loading(false))                        //3rd-Action, if executed

       }
    }

    override suspend fun getMovie(id: Int): Flow<Resource<Movie>> {

        return flow {
            emit(Resource.Loading(true))
            val movieEntity: MovieEntity = movieDatabase.movieDAO.getMovieById(id)
            movieEntity?.let {                      // might be avoided, but to make sure no nullable instance happened.
                emit(Resource.Success(it.toMovie(it.category)))
                emit(Resource.Loading(false))
                return@flow
            }
            emit(Resource.Error("no with Movie is available with this ID!"))
            emit(Resource.Loading(false))

        }

    }
}