package com.franco.mytestapplication.dagger

import com.franco.mytestapplication.BuildConfig
import com.franco.mytestapplication.data.remote.TheMovieDbApi
import com.franco.mytestapplication.network.ApiKeyInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/**
 * Module to provide the API built by Retrofit.
 *
 * @author Franco Omar Castillo Bello
 * Created 26/09/2021 at 02:32 a. m.
 */
@Module
class NetworkModule {

    @Provides
    fun provideHttpInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    @Provides
    fun provideApiKeyInterceptor() = ApiKeyInterceptor(BuildConfig.API_KEY)

    @Provides
    fun provideOkHttpClient(
        apiKeyInterceptor: ApiKeyInterceptor,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.MY_MOVIES_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): TheMovieDbApi =
        retrofit.create(TheMovieDbApi::class.java)
}