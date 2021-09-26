package com.franco.mytestapplication.domain.local.room.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.franco.mytestapplication.domain.local.room.relations.MovieWithDetail
import com.franco.mytestapplication.domain.local.room.tables.Genres
import com.franco.mytestapplication.domain.local.room.tables.Movie
import com.franco.mytestapplication.domain.local.room.tables.MovieCast
import com.franco.mytestapplication.domain.local.room.tables.MovieCrew
import com.franco.mytestapplication.domain.local.room.tables.MovieDetail
import com.franco.mytestapplication.domain.local.room.tables.MovieGenre
import com.franco.mytestapplication.domain.local.room.tables.MovieProductionCompany
import com.franco.mytestapplication.domain.local.room.tables.MovieSpokenLanguage
import com.franco.mytestapplication.domain.local.room.tables.MovieVideo

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 25/09/21 at 7:31 p.m.
 */
@Dao
interface MoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovieVideo(movieVideo: List<MovieVideo>)

    @Query("SELECT COUNT(*) FROM Movie")
    abstract fun count(): Int

    @Query("SELECT * FROM Movie ORDER BY popularity DESC")
    abstract fun fetchPopularMovies(): PagingSource<Int, Movie>

    @Query("SELECT * FROM Movie ORDER BY vote_average DESC")
    abstract fun fetchTopRatedMovies(): PagingSource<Int, Movie>

    @Query("SELECT * FROM movie WHERE title LIKE :query or genre LIKE :query ")
    abstract fun search(query: String): PagingSource<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertGenres(genres: List<Genres>)

    @Query("SELECT * FROM Genres")
    abstract suspend fun getGenres(): List<Genres>

    @Transaction
    @Query("SELECT * FROM movie_detail WHERE id=:id")
    abstract suspend fun getDetail(id: Int): MovieWithDetail?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovieDetail(movieDetail: MovieDetail)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovieCast(movieCast: List<MovieCast>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovieCrew(movieCrew: List<MovieCrew>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovieGenres(movieGenres: List<MovieGenre>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovieCompanies(movieProductionCompany: List<MovieProductionCompany>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovieSpokenLanguages(movieSpokenLanguage: List<MovieSpokenLanguage>)
}