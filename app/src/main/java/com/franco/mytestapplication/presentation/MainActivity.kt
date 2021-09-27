package com.franco.mytestapplication.presentation

import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.franco.mytestapplication.R
import com.franco.mytestapplication.network.NetworkChecker
import com.franco.mytestapplication.presentation.movies.MoviesFragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

/**
 * Main activity to contain fragments.
 *
 * @author Franco Omar Castillo Bello
 * Created 24/09/21 at 11:57 p.m.
 */
class MainActivity : AppCompatActivity(), HasAndroidInjector {

    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var networkChecker: NetworkChecker

    private val networkObserver: Observer<Boolean> by lazy {
        Observer { hasConnection ->
            if (!hasConnection) {
                Toast.makeText(
                    this@MainActivity,
                    getString(R.string.no_internet_connection),
                    Toast.LENGTH_SHORT
                ).apply {
                    setGravity(Gravity.CENTER, 0,0)
                }.show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setInitialScreen(savedInstanceState)
        setupNetworkChecker()

    }

    override fun androidInjector(): AndroidInjector<Any> = injector

    private fun setupNetworkChecker() = networkChecker.observe(this, networkObserver)

    private fun setInitialScreen(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fl_fragment_container, MoviesFragment.newInstance())
                .commitNow()
        }
    }
}