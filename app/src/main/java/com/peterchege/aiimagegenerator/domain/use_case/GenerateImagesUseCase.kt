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