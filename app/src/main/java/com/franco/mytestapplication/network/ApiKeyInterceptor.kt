package com.franco.mytestapplication.network

import okhttp3.Interceptor
import javax.inject.Inject


/**
 * Injects the api key into request. This way is useful and more secure because we do not use the
 * api key directly in the declaration of the web service path in API interface defined for Retrofit.
 *
 * @author Franco Omar Castillo Bello
 * Created 26/09/2021 at 02:34 a. m.
 */
class ApiKeyInterceptor @Inject constructor(private val apiKey: String): Interceptor {

    override fun intercept(chain: Interceptor.Chain) = run {
        val original = chain.request()
        val originalUrl = original.url

        val urlWithApiKey = originalUrl
            .newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()

        val request = original.newBuilder()
            .url(urlWithApiKey)
            .build()

        chain.proceed(request)
    }
}