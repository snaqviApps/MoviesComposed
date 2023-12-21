package com.example.moviescomposed.movielist.data.mappers

import com.example.moviescomposed.movielist.data.local.movies.MovieEntity
import com.example.moviescomposed.movielist.data.remote.response.MovieDTO
import com.example.moviescomposed.movielist.domain.model.Movie

/**
 * maps data from Movie to @MovieEntity here (return-type)
 */
fun MovieDTO.toMovieEntity (
    category: String
): MovieEntity {
    return MovieEntity(
        adult = adult ?: false,
        backdropPath = backdropPath ?: "",
        originalLanguage = originalLanguage ?: "",
        originalTitle = originalTitle ?: "",
        overview = overview ?: "",
        popularity = popularity ?: 0.0,
        posterPath = posterPath ?: "",
        releaseDate = releaseDate ?: "",
        title = title ?: "",
        video = video ?: false,
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
        id = id ?: -1,
        category = category,
        genreIds = try {
            genreIds?.joinToString(",") ?: "-1,-2"
        } catch (e: Exception) {
            "-1,-2"
        }
    )
}

/**
 * mapper to map data from Database
 * to 'Movie'
 * */

fun MovieEntity.toMovie(
    category: String
): Movie {
    return Movie (
        adult = adult,
        backdropPath = backdropPath,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount,
        id = id,
        category = category,
        genreIds = try {
            genreIds.split (",").map { it.toInt() }
        } catch (e: Exception){
            listOf(-1, -2)
        }
    )
}