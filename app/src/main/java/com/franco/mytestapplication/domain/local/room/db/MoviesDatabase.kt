package com.franco.mytestapplication.domain.local.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.franco.mytestapplication.BuildConfig
import com.franco.mytestapplication.domain.local.room.db.MoviesDatabase.Companion.DB_CURRENT_VERSION
import com.franco.mytestapplication.domain.local.room.tables.Genres
import com.franco.mytestapplication.domain.local.room.tables.Movie

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello / youremail@domain.com
 * Created 25/09/21 at 10:48 p.m.
 */
@Database(
    entities = [
        Movie::class,
        Genres::class
    ],
    version = DB_CURRENT_VERSION
)
abstract class MoviesDatabase: RoomDatabase() {

    /**
     * Gets a reference to dao.
     */
    abstract fun myMoviesDao(): MoviesDao

    companion object {

        const val DB_CURRENT_VERSION = BuildConfig.DATABASE_VERSION


        private const val MOVIES_DB_NAME = "movies_db"
        private var db: MoviesDatabase? = null

        @Synchronized
        fun getInstance(context: Context) = run {
            if (db == null) {
                db = Room.databaseBuilder(
                    context,
                    MoviesDatabase::class.java,
                    MOVIES_DB_NAME
                ).build()
            }
            db!!
        }
    }
}