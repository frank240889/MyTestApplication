package com.franco.mytestapplication.data

import android.app.Application
import com.franco.mytestapplication.MyTestApplication
import com.franco.mytestapplication.dagger.AndroidViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello
 * Created 25/09/21 at 2:07 p.m.
 */
@Component(modules = [
    AndroidInjectionModule::class,
    AndroidViewModelModule::class
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

    override fun inject(myTestApplication: MyTestApplication)
}