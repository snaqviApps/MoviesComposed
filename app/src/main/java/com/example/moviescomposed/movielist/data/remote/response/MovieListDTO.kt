package com.example.moviescomposed.movielist.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * DTO: Data Transfer Object
 */
data class MovieListDTO(
    val page: Int,
    val results: List<MovieDTO>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int
)