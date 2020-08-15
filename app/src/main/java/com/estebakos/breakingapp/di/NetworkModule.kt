package com.estebakos.breakingapp.di

import com.estebakos.breakingapp.BuildConfig
import com.estebakos.breakingapp.base.Constants
import com.estebakos.breakingapp.data.remote.api.BreakingAppApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object NetworkModule {

    @Provides
    fun providesLoggingInterceptor(): HttpLoggingInterceptor? =
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        } else null

    @Provides
    internal fun providesOkHttpClientBuilder(
        loggingInterceptor: HttpLoggingInterceptor?
    ): OkHttpClient.Builder =
        OkHttpClient.Builder().apply {
            loggingInterceptor?.also {
                addInterceptor(it)
            }
        }

    @Provides
    internal fun providesIntegratorOkHttpClient(
        builder: OkHttpClient.Builder
    ): OkHttpClient = builder.build()

    @Provides
    @Singleton
    internal fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

    @Provides
    @JvmStatic
    internal fun providesBreakingApi(retrofit: Retrofit): BreakingAppApi =
        retrofit.create(BreakingAppApi::class.java)

}