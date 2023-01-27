package com.peterchege.aiimagegenerator.domain.repository

import com.peterchege.aiimagegenerator.domain.models.ImageResponse
import com.peterchege.aiimagegenerator.domain.models.RequestBody

interface ImageRepository {

    suspend fun generateImages(requestBody: RequestBody):ImageResponse


}