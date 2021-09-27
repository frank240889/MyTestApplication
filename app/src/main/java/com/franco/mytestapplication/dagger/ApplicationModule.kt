package com.franco.mytestapplication.dagger

import android.app.Application
import com.franco.mytestapplication.MyTestApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


/**
 * Module to provide context.
 *
 * @author Franco Omar Castillo Bello
 * Created 26/09/2021 at 02:30 a. m.
 */
@Module
class ApplicationModule {

    @Singleton
    @Provides
    fun provideContext(application: Application) = application as MyTestApplication
}