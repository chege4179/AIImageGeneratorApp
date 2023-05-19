/*
 * Copyright 2023 AI Image Generator App Peter Chege Mwangi
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.peterchege.aiimagegenerator.data.di

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.peterchege.aiimagegenerator.BuildConfig
import com.peterchege.aiimagegenerator.data.api.OpenAIApi
import com.peterchege.aiimagegenerator.data.repository.ImageRepositoryImpl
import com.peterchege.aiimagegenerator.domain.repository.ImageRepository
import com.peterchege.aiimagegenerator.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun provideChuckerCollector(
        @ApplicationContext context: Context
    ):ChuckerCollector{
        return ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )
    }

    @Provides
    @Singleton
    fun provideChuckerInterceptor(
        chuckerCollector: ChuckerCollector,
        @ApplicationContext context: Context
    ):ChuckerInterceptor{
        return ChuckerInterceptor.Builder(context)
            .collector(collector = chuckerCollector)
            .maxContentLength(250_000L)
            .alwaysReadResponseBody(enable = true)
            .build()

    }

    @Provides
    @Singleton
    fun provideOkhttpClient(
        chuckerInterceptor: ChuckerInterceptor,
    ):OkHttpClient{
        return OkHttpClient.Builder()
            .addInterceptor(OAuthInterceptor("Bearer", BuildConfig.OPEN_AI_API_KEY))
            .addInterceptor(chuckerInterceptor)
            .build()
    }




    @Provides
    @Singleton
    fun provideOpenAIApi(
        client:OkHttpClient
    ): OpenAIApi {
        return Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .client(client)
            .build()
            .create(OpenAIApi::class.java)
    }

    @Provides
    @Singleton
    fun provideImageRepository(api:OpenAIApi): ImageRepository {
        return ImageRepositoryImpl(api = api)
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