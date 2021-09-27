package com.franco.mytestapplication.dagger

import android.app.Application
import com.franco.mytestapplication.domain.local.room.db.MoviesDatabase
import dagger.Module
import dagger.Provides


/**
 * Module to provide DB.
 *
 * @author Franco Omar Castillo Bello
 * Created 26/09/2021 at 02:33 a. m.
 */
@Module
class DatabaseModule {
    @Provides
    fun provideDb(application: Application) = MoviesDatabase.getInstance(application)
}