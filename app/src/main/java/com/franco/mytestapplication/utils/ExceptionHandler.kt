package com.franco.mytestapplication.utils

import com.franco.mytestapplication.MyTestApplication
import com.franco.mytestapplication.R
import com.franco.mytestapplication.interfaces.ErrorHandler
import java.net.UnknownHostException
import javax.inject.Inject


/**
 * Implementation of [ErrorHandler]
 *
 * @author Franco Omar Castillo Bello
 * Created 26/09/2021 at 03:10 a. m.
 */
class ExceptionHandler @Inject constructor(
    private val application: MyTestApplication
): ErrorHandler {

    override fun resolveError(t: Throwable): String {
        return when(t) {
            is UnknownHostException -> application.getString(R.string.cannot_connect_to_server)
            else -> t.message ?: application.getString(R.string.default_error_message)
        }
    }
}