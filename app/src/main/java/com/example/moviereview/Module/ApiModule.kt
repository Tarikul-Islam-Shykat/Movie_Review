package com.example.moviereview.Module

import androidx.viewbinding.BuildConfig
import com.example.moviereview.api.ApiServices

import com.example.moviereview.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideBaseUrl() = Constants.BASE_URL

    @Provides
    @Singleton
    fun ConnectionTimeOut() = Constants.NETWORK_TIMEOUT

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig. DEBUG){
        val logginIntercpter = HttpLoggingInterceptor()
        logginIntercpter.setLevel(HttpLoggingInterceptor.Level.HEADERS)
        logginIntercpter.setLevel(HttpLoggingInterceptor.Level.BODY)

        val requestInterceptor = Interceptor{ chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("api_key", Constants.API_KEY)
            .build()

            val request = chain.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor chain.proceed(request)
        }

        OkHttpClient
            .Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(logginIntercpter)
            .build()

    }
    else{
        OkHttpClient.Builder().build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl : String, gson: Gson, client: OkHttpClient) : ApiServices =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServices::class.java)

}