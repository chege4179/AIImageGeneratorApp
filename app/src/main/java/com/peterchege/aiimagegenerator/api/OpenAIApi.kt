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