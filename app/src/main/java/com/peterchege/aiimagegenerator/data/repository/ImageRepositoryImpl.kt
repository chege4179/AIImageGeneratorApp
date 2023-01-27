package com.peterchege.aiimagegenerator.data.repository

import com.peterchege.aiimagegenerator.data.api.OpenAIApi
import com.peterchege.aiimagegenerator.domain.models.ImageResponse
import com.peterchege.aiimagegenerator.domain.models.RequestBody
import com.peterchege.aiimagegenerator.domain.repository.ImageRepository
import javax.inject.Inject

class ImageRepositoryImpl @Inject constructor(
    private val api:OpenAIApi
):ImageRepository {


    override suspend fun generateImages(requestBody: RequestBody): ImageResponse {
        return api.generateImages(requestBody = requestBody)
    }

}