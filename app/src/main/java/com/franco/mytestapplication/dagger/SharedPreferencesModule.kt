package com.franco.mytestapplication.dagger

import com.franco.mytestapplication.interfaces.AbstractSharedPreferences
import com.franco.mytestapplication.utils.AndroidSharedPreferences
import dagger.Binds
import dagger.Module


/**
 * Module to provides a Shared preferences wrapper.
 *
 * @author Franco Omar Castillo Bello
 * Created 26/09/2021 at 03:07 a. m.
 */
@Module
abstract class SharedPreferencesModule {

    @Binds
    abstract fun providesSharedPreferences(
        androidSharedPreferences: AndroidSharedPreferences
    ): AbstractSharedPreferences
}