package com.franco.mytestapplication

import android.app.Application
import com.franco.mytestapplication.dagger.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import timber.log.Timber
import javax.inject.Inject

/**
 * The class to represent the whole application.
 *
 * @author Franco Omar Castillo Bello
 * Created 25/09/21 at 2:11 p.m.
 */
class MyTestApplication: Application(), HasAndroidInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
        // Inject the application component is required to enable the app
        // to inject dependencies into other components.
        DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = injector
}