package com.peterchege.aiimagegenerator.repository

import com.peterchege.aiimagegenerator.domain.models.ImageItem
import com.peterchege.aiimagegenerator.domain.models.ImageResponse
import com.peterchege.aiimagegenerator.domain.models.RequestBody
import com.peterchege.aiimagegenerator.domain.repository.ImageRepository

class FakeImageRepository :ImageRepository {
    val fakeImages = ('a'..'z').map {  c ->
        ImageItem(
            url = c.toString()
        )
    }
    override suspend fun generateImages(requestBody: RequestBody): ImageResponse {
        return ImageResponse(
            created = "Test",
            data = fakeImages
        )
    }
}