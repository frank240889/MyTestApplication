package com.franco.mytestapplication.interfaces
/**
 * A error handler to translate a exception into a human-readable message.
 *
 * @author Franco Omar Castillo Bello
 * Created 25/09/21 at 4:52 p.m.
 */
interface ErrorHandler {

    /**
     * Returns a message based on the exception.
     */
    fun resolveError(t: Throwable): String
}