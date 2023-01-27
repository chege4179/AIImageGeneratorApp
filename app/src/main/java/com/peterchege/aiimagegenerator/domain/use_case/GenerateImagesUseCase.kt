package com.peterchege.aiimagegenerator.domain.use_case

import com.peterchege.aiimagegenerator.domain.models.ImageResponse
import com.peterchege.aiimagegenerator.domain.models.RequestBody
import com.peterchege.aiimagegenerator.domain.repository.ImageRepository
import com.peterchege.aiimagegenerator.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

class GenerateImagesUseCase @Inject constructor(
    private val imageRepository: ImageRepository,
) {

    operator fun invoke(requestBody: RequestBody) : Flow<Resource<ImageResponse>> = flow {
        try {

            emit(Resource.Loading<ImageResponse>())
            val response = imageRepository.generateImages(requestBody = requestBody)
            emit(Resource.Success(response))

        }catch (e: HttpException){
            emit(
                Resource.Error<ImageResponse>(
                    message = e.localizedMessage ?: "Server error"))

        }catch (e: IOException){
            emit(
                Resource.Error<ImageResponse>(
                    message = "Could not reach server... Please check your internet connection"))

        }
    }
}