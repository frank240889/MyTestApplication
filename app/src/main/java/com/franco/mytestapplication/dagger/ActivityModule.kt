package com.franco.mytestapplication.dagger

import com.franco.mytestapplication.presentation.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Creates injector for activities.
 *
 * @author Franco Omar Castillo Bello
 * Created 26/09/2021 at 02:31 a. m.
 */
@Module
abstract class ActivityModule {

    /**
     * Indicates this [MainActivity] is injectable by Dagger.
     */
    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity
}