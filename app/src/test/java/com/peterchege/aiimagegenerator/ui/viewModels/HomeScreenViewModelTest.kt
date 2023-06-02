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
package com.peterchege.aiimagegenerator.ui.viewModels

import com.peterchege.aiimagegenerator.MainDispatcherRule
import com.peterchege.aiimagegenerator.data.api.NetworkResult
import com.peterchege.aiimagegenerator.domain.models.ImageItem
import com.peterchege.aiimagegenerator.domain.models.ImageResponse
import com.peterchege.aiimagegenerator.domain.repository.ImageRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test

val fakeImages = ('a'..'z').map {  c ->
    ImageItem(
        url = c.toString()
    )
}

class HomeScreenViewModelTest {
    lateinit var  homeScreenViewModel: HomeScreenViewModel
    val imageRepository = mockk<ImageRepository>(relaxed = true)

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        homeScreenViewModel = HomeScreenViewModel(repository = imageRepository)
    }

    @Test
    fun `When app is launched the state is idle `(){
        assert(homeScreenViewModel.uiState.value is HomeScreenUiState.Idle)
    }


    @Test
    fun `When the an error occurs, then an error state is loaded`() = runTest {
        coEvery { imageRepository.generateImages(any()) } returns
                NetworkResult.Error(code = 4000 ,message = "An unexpected error occurred")
        homeScreenViewModel.generateImages()
        assert(homeScreenViewModel.uiState.value is HomeScreenUiState.Error)

    }
    @Test
    fun `When the request is successful the images are loaded into state`() = runTest {
        coEvery { imageRepository.generateImages(any()) } returns
                NetworkResult.Success(data = ImageResponse(created = "", data = fakeImages))

        homeScreenViewModel.generateImages()
        assert(homeScreenViewModel.uiState.value is HomeScreenUiState.Success)
    }

}

