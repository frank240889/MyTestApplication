package com.franco.mytestapplication.dagger

import android.app.Application
import com.franco.mytestapplication.MyTestApplication
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


/**
 * Main component for injection.
 *
 * @author Franco Omar Castillo Bello
 * Created 26/09/2021 at 02:47 a. m.
 */
@Component(modules = [
    AndroidInjectionModule::class,
    ApplicationModule::class,
    AndroidViewModelModule::class,
    ActivityModule::class,
    FragmentModule::class,
    NetworkModule::class,
    DatabaseModule::class,
    RepositoryModule::class,
    SharedPreferencesModule::class,
    ErrorHandlerModule::class
])
@Singleton
interface ApplicationComponent : AndroidInjector<MyTestApplication> {

    /**
     * The component that inject the dependencies into Application object.
     */
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }

    override fun inject(myMoviesApplication: MyTestApplication)
}