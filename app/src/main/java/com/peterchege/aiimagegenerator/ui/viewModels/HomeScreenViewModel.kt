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


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.aiimagegenerator.domain.models.ImageItem
import com.peterchege.aiimagegenerator.domain.models.RequestBody
import com.peterchege.aiimagegenerator.domain.use_case.GenerateImagesUseCase
import com.peterchege.aiimagegenerator.util.Resource
import com.peterchege.aiimagegenerator.util.UiEvent
import com.peterchege.aiimagegenerator.util.imageCounts
import com.peterchege.aiimagegenerator.util.imageSizes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(

    private val generateImagesUseCase: GenerateImagesUseCase,

    ) : ViewModel() {
    private val _prompt = mutableStateOf("")
    val prompt: State<String> = _prompt

    private val _size = mutableStateOf("1024x1024")
    val size: State<String> = _size

    private val _imageCount = mutableStateOf(1)
    val imageCount: State<Int> = _imageCount

    private var _isLoading = mutableStateOf(false)
    var isLoading: State<Boolean> = _isLoading

    private var _selectedImageSizeIndex = mutableStateOf(0)
    var selectedImageSizeIndex: State<Int> = _selectedImageSizeIndex

    private var _selectedImageCountIndex = mutableStateOf(0)
    var selectedImageCountIndex: State<Int> = _selectedImageCountIndex

    private var _generatedImages = mutableStateOf<List<ImageItem>>(emptyList())
    var generatedImages: State<List<ImageItem>> = _generatedImages

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onChangeSelectedImageSizeIndex(index: Int) {
        _selectedImageSizeIndex.value = index
        _size.value = imageSizes[index]
    }

    fun onChangeSelectedImageCountIndex(index: Int) {
        _selectedImageCountIndex.value = index
        _imageCount.value = imageCounts[index]
    }


    fun onChangePrompt(text: String) {
        _prompt.value = text
    }

    fun generateImages() {
        _isLoading.value = true
        val requestBody = RequestBody(prompt = _prompt.value, n = _imageCount.value, size = _size.value)
        generateImagesUseCase(requestBody = requestBody).onEach { result ->
            when (result) {
                is Resource.Success -> {

                    _isLoading.value = false
                    _generatedImages.value = result.data?.data ?: emptyList()

                }
                is Resource.Error -> {

                    _isLoading.value = false
                    _eventFlow.emit(UiEvent.ShowSnackbar(uiText = result.message ?: "An unexpected error occurred"))


                }
                is Resource.Loading -> {

                    _isLoading.value = true

                }
            }
        }.launchIn(viewModelScope)
    }

}