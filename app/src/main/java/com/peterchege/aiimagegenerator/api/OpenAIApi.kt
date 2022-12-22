package com.peterchege.aiimagegenerator.api


import com.peterchege.aiimagegenerator.BuildConfig
import com.peterchege.aiimagegenerator.models.ImageResponse
import com.peterchege.aiimagegenerator.models.RequestBody
import com.peterchege.aiimagegenerator.util.Constants
import retrofit2.http.*

interface OpenAIApi {

    @POST("generations")
    suspend fun generateImages(@Body requestBody:RequestBody):ImageResponse

}