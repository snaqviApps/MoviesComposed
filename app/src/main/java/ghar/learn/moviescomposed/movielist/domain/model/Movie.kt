package ghar.learn.moviescomposed.movielist.domain.model

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class Movie (
    val adult: Boolean,

    @SerializedName("backdrop_path")    // POJO has as: "backdrop_path"
    val backdropPath: String,

    val genreIds: List<Int>,
    @SerializedName("original_language")
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,

    @SerializedName("poster_path")    // POJO has as: "poster_path"
    val posterPath: String,

    @SerializedName("release_Date")
    val releaseDate: String,
    val title: String,
    val video: Boolean,

    @SerializedName("vote_average")
    val voteAverage: Double,
    val voteCount: Int,

    @PrimaryKey
    val id: Int,
    val category: String
)