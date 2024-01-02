package ghar.learn.moviescomposed.movielist.data.local.movies

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

/**
 * This accesses Room-DB
 */
@Dao
interface MovieDAO {

    @Upsert     /** update or insert */
    suspend fun upsertMoviesList(moviesList: List<MovieEntity>)

//    @Query("Select * from movie")                   /** added by me, needs to test it */
//    suspend fun observeMovies() : List<Movie>

    @Query("Select * From MovieEntity where id = :id")
    suspend fun getMovieById(id: Int) : MovieEntity


    @Query("Select * From MovieEntity where category = :category")
    suspend fun getMovieByCategory(category: String) : List<MovieEntity>

    /**
     * to enable after solving issue of 'table (movie)'
     */
//    @Query("Select * From movie where releaseDate = :releaseDate")
//    suspend fun getMovieByReleaseDate(releaseDate: String): List<MovieEntity>

}