package com.peterchege.aiimagegenerator.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.peterchege.aiimagegenerator.BuildConfig
import com.peterchege.aiimagegenerator.api.OpenAIApi
import com.peterchege.aiimagegenerator.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    val client =  OkHttpClient.Builder()
        .addInterceptor(OAuthInterceptor("Bearer", BuildConfig.OPEN_AI_API_KEY))
        .build()
    @Provides
    @Singleton
    fun provideOpenAIApi():OpenAIApi{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .build()
            .create(OpenAIApi::class.java)
    }

}

class OAuthInterceptor(private val tokenType: String, private val acceessToken: String):
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
        var request = chain.request()
        request = request.newBuilder().header("Authorization", "$tokenType $acceessToken").build()

        return chain.proceed(request)
    }
}