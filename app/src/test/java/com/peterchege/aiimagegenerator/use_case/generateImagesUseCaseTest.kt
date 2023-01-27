package com.peterchege.aiimagegenerator.use_case




import com.peterchege.aiimagegenerator.domain.models.RequestBody
import com.peterchege.aiimagegenerator.domain.use_case.GenerateImagesUseCase
import com.peterchege.aiimagegenerator.repository.FakeImageRepository
import com.peterchege.aiimagegenerator.util.Resource
import com.peterchege.aiimagegenerator.util.imageSizes
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class GetFeedUseCaseTest {
    private lateinit var generateImagesUseCase: GenerateImagesUseCase
    private lateinit var fakeImageRepository: FakeImageRepository
    @Before
    fun setUp(){
        fakeImageRepository = FakeImageRepository()
        generateImagesUseCase = GenerateImagesUseCase(imageRepository = fakeImageRepository)


    }
    val dummyRequest = RequestBody( n = 8, prompt = "dummy", size = imageSizes[0])
    @Test
    fun `Ensure the state is loading first before `() = runBlocking {

        val result = generateImagesUseCase.invoke(requestBody = dummyRequest).first()

        assert((result is Resource.Loading))
    }

    @Test
    fun `get generated Posts and make sure the Images are not empty`() = runBlocking {
        val result =  generateImagesUseCase.invoke(requestBody = dummyRequest).last()
        val data  = result.data


        if (data != null) {
            assert((result is Resource.Success) && data.data.isNotEmpty())
        }

    }






}