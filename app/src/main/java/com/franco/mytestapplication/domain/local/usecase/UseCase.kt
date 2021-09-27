package com.franco.mytestapplication.domain.local.usecase
/**
 * This stuff works...
 *
 * @author Franco Omar Castillo Bello
 * Created 25/09/21 at 4:58 p.m.
 */
abstract class UseCase<Response> {

    abstract fun execute(): Response

    fun release() {}
}