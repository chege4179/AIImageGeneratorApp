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