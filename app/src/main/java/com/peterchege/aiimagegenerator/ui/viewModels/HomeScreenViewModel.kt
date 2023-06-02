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
import com.peterchege.aiimagegenerator.data.api.NetworkResult
import com.peterchege.aiimagegenerator.domain.models.ImageItem
import com.peterchege.aiimagegenerator.domain.models.RequestBody
import com.peterchege.aiimagegenerator.domain.repository.ImageRepository
import com.peterchege.aiimagegenerator.util.Resource
import com.peterchege.aiimagegenerator.util.UiEvent
import com.peterchege.aiimagegenerator.util.imageCounts
import com.peterchege.aiimagegenerator.util.imageSizes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

data class FormState(
    val prompt:String = "",
    val size:String = "1024x1024",
    val imageCount:Int = 1
)

sealed interface HomeScreenUiState {
    object Idle : HomeScreenUiState
    object Loading : HomeScreenUiState
    data class Error(val message: String) : HomeScreenUiState
    data class Success(val images: List<ImageItem>, ) : HomeScreenUiState
}

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: ImageRepository,
    ) : ViewModel() {

    private val _formState = MutableStateFlow(FormState())
    val formState = _formState.asStateFlow()


    private val _uiState = MutableStateFlow<HomeScreenUiState>(HomeScreenUiState.Idle)
    val uiState = _uiState.asStateFlow()


    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()


    fun onChangeSelectedImageSizeIndex(index: Int) {
        _formState.value = _formState.value.copy(size = imageSizes[index])

    }
    fun onChangeSelectedImageCountIndex(index: Int) {
        _formState.value = _formState.value.copy(imageCount = imageCounts[index])
    }

    fun onChangePrompt(text: String) {
        _formState.value = _formState.value.copy(prompt = text)
    }

    fun generateImages() {
        _uiState.value = HomeScreenUiState.Loading

        val requestBody = RequestBody(
            prompt = _formState.value.prompt,
            n = _formState.value.imageCount,
            size = _formState.value.size
        )
        viewModelScope.launch {
            when (val response = repository.generateImages(requestBody = requestBody)) {
                is NetworkResult.Success -> {
                    _uiState.value = HomeScreenUiState.Success(images = response.data.data)
                }
                is NetworkResult.Error -> {
                    _uiState.value = HomeScreenUiState.Error(
                        message = response.message ?: "An unexpected error occurred")
                    _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "${response.code} ${response.message}"))
                }
                is NetworkResult.Exception -> {
                    _uiState.value = HomeScreenUiState.Error(
                        message = response.e.message ?: "An unexpected error occurred")
                    _eventFlow.emit(UiEvent.ShowSnackbar(uiText = "${response.e.message}"))
                }
            }
        }

    }

}