package ghar.learn.moviescomposed.movielist.data.local.movies

import androidx.room.Entity
import androidx.room.PrimaryKey

//@Entity(tableName = "movie")
/**
 * Instructor didn't take this approach (no movie-table in database, here)
  */
@Entity
data class MovieEntity (
    val adult: Boolean,
    val backdropPath: String,

    /** original DTO it is 'List<Int>',
     * but for that can't be put into database, so here (Entity for DB)
     *  it is used as 'String' type, and conversion of List<Int> to String is handled in
     *  'mappers'
     * */
    val genreIds: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int,

    @PrimaryKey         // not "autoGenerate", as it is coming in api-
    val id: Int,
    val category: String
)