package ghar.learn.moviescomposed.movielist.data.remote

import ghar.learn.moviescomposed.BuildConfig.API_KEY
import ghar.learn.moviescomposed.movielist.data.remote.response.MovieListDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("movie/{category}")
    suspend fun getMovieList (
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_key") apikey: String = API_KEY
    ) : MovieListDTO

    companion object {
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }
}