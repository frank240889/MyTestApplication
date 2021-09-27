package com.franco.mytestapplication.dagger

import com.franco.mytestapplication.interfaces.ErrorHandler
import com.franco.mytestapplication.utils.ExceptionHandler
import dagger.Binds
import dagger.Module


/**
 * Module to provide error handler.
 *
 * @author Franco Omar Castillo Bello
 * Created 26/09/2021 at 03:14 a. m.
 */
@Module
abstract class ErrorHandlerModule {

    @Binds
    abstract fun providesErrorHandler(exceptionHandler: ExceptionHandler): ErrorHandler
}