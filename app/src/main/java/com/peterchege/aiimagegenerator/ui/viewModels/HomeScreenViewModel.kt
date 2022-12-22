package com.peterchege.aiimagegenerator.ui.viewModels

import android.util.Log
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.aiimagegenerator.api.OpenAIApi
import com.peterchege.aiimagegenerator.models.ImageItem
import com.peterchege.aiimagegenerator.models.RequestBody
import com.peterchege.aiimagegenerator.util.imageCounts
import com.peterchege.aiimagegenerator.util.imageSizes
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val api:OpenAIApi

): ViewModel(){
    private val _prompt = mutableStateOf("")
    val prompt: State<String> =_prompt

    private val _size = mutableStateOf("1024x1024")
    val size: State<String> =_size

    private val _imageCount = mutableStateOf(1)
    val imageCount: State<Int> =_imageCount

    private var _isLoading = mutableStateOf(false)
    var isLoading:State<Boolean> = _isLoading

    private var _selectedImageSizeIndex = mutableStateOf(0)
    var selectedImageSizeIndex:State<Int> = _selectedImageSizeIndex

    private var _selectedImageCountIndex = mutableStateOf(0)
    var selectedImageCountIndex:State<Int> = _selectedImageCountIndex

    private var _generatedImages = mutableStateOf<List<ImageItem>>(emptyList())
    var generatedImages:State<List<ImageItem>> = _generatedImages



    fun onChangeSelectedImageSizeIndex(index:Int){
        _selectedImageSizeIndex.value = index
        _size.value = imageSizes[index]
    }

    fun onChangeSelectedImageCountIndex(index:Int){
        _selectedImageCountIndex.value = index
        _imageCount.value = imageCounts[index]
    }


    fun onChangePrompt(text:String){
        _prompt.value = text
    }

    fun generateImages(scaffoldState: ScaffoldState){
        viewModelScope.launch {
            if (prompt.value == ""){
                scaffoldState.snackbarHostState.showSnackbar(
                    "Please enter an image description"
                )
                return@launch
            }
            _isLoading.value = true
            try {
                val response = api.generateImages(
                    RequestBody(prompt = _prompt.value,n = _imageCount.value,size = _size.value))
                _generatedImages.value = response.data
                Log.e("Image Generated",response.created)
                _isLoading.value = false

            }catch (e:HttpException){
                _isLoading.value=false
                Log.e("HTTP error", e.localizedMessage ?: "HTTP ERROR")
                scaffoldState.snackbarHostState.showSnackbar(
                    message = e.localizedMessage ?: "HTTP ERROR"
                )
            }catch (e:IOException){
                _isLoading.value=false
                Log.e("IO error", e.localizedMessage ?: "IO ERROR")
                scaffoldState.snackbarHostState.showSnackbar(
                    message = e.localizedMessage ?: "IO ERROR"
                )
            }
        }

    }

}