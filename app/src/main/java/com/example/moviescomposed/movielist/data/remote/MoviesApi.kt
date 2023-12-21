package com.example.moviescomposed.movielist.data.remote

import com.example.moviescomposed.BuildConfig.API_KEY
import com.example.moviescomposed.movielist.data.remote.response.MovieListDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET
    suspend fun getMovieList(
        @Path("category") category: String,
        @Query("page") page: Int,
        @Query("api_Key") apikey: String = API_KEY
    ) : MovieListDTO

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500 "
    }
}