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

import com.peterchege.aiimagegenerator.domain.models.ImageItem
import com.peterchege.aiimagegenerator.domain.use_case.GenerateImagesUseCase
import com.peterchege.aiimagegenerator.repository.FakeImageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class HomeScreenViewModelTest {
    lateinit var  homeScreenViewModel: HomeScreenViewModel
    lateinit var generateImagesUseCase: GenerateImagesUseCase
    lateinit var imageRepository: FakeImageRepository

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setUp() {
        imageRepository = FakeImageRepository()
        generateImagesUseCase = GenerateImagesUseCase(imageRepository = imageRepository)
        homeScreenViewModel = HomeScreenViewModel(generateImagesUseCase)
    }

    @Test
    fun `Verify No Images are available on startup`(){
        assertEquals(emptyList<ImageItem>(),homeScreenViewModel.generatedImages.value)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `Verify Images are loaded into state `() = runTest {
        homeScreenViewModel.generateImages()
        assert(homeScreenViewModel.generatedImages.value.isNotEmpty())

    }
}

class MainDispatcherRule(
    val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
) : TestWatcher() {
    @OptIn(ExperimentalCoroutinesApi::class)
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}