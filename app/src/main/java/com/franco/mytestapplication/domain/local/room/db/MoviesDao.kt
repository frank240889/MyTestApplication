package com.franco.mytestapplication.domain.local.room.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.franco.mytestapplication.domain.local.room.tables.Genres
import com.franco.mytestapplication.domain.local.room.tables.Movie

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 25/09/21 at 7:31 p.m.
 */
@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGenres(genres: List<Genres>)

    @Query("SELECT * FROM Movie ORDER BY Movie.popularity DESC")
    fun fetchMovies(): PagingSource<Int, Movie>
}