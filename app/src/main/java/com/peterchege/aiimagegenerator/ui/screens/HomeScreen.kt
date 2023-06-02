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
package com.peterchege.aiimagegenerator.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.peterchege.aiimagegenerator.domain.downloader.AndroidDownloader
import com.peterchege.aiimagegenerator.ui.components.MyCustomDropDownMenu
import com.peterchege.aiimagegenerator.ui.components.PagerIndicator
import com.peterchege.aiimagegenerator.ui.viewModels.FormState
import com.peterchege.aiimagegenerator.ui.viewModels.HomeScreenUiState
import com.peterchege.aiimagegenerator.ui.viewModels.HomeScreenViewModel
import com.peterchege.aiimagegenerator.util.UiEvent
import com.peterchege.aiimagegenerator.util.imageCounts
import com.peterchege.aiimagegenerator.util.imageSizes
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.M)
@Composable
fun HomeScreen(
    navController:NavController,
    viewModel:HomeScreenViewModel = hiltViewModel()
){
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val formState by viewModel.formState.collectAsStateWithLifecycle()
    HomeScreenContent(
        uiState = uiState,
        formState = formState,
        eventFlow = viewModel.eventFlow,
        onChangePrompt = { viewModel.onChangePrompt(it) },
        onChangeSize = { viewModel.onChangeSelectedImageSizeIndex(it) },
        onChangeImageCount = { viewModel.onChangeSelectedImageCountIndex(it) },
        onSubmit = { viewModel.generateImages() }
    )

}


@RequiresApi(Build.VERSION_CODES.M)
@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreenContent(
    uiState: HomeScreenUiState,
    formState:FormState,
    eventFlow:SharedFlow<UiEvent>,
    onChangePrompt:(String) -> Unit,
    onChangeSize:(Int) -> Unit,
    onChangeImageCount: (Int) -> Unit,
    onSubmit:() -> Unit,
) {
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.uiText
                    )
                }
                is UiEvent.Navigate -> {

                }
            }
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(end = 15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Text(text = "AI Image Generator")

                    }
                }
            )
        },
    ) {

        Box(modifier = Modifier.fillMaxSize()){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Enter Image Description")
                    },
                    value = formState.prompt,
                    onValueChange = {
                        onChangePrompt(it)

                    })
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.4f)
                            .fillMaxHeight()

                    ) {
                        Text(
                            text = "Select Image Size",
                            fontSize = 14.sp
                        )
                        MyCustomDropDownMenu(
                            listItems = imageSizes,
                            selectedIndex = imageSizes.indexOf(formState.size) ,
                            onChangeSelectedIndex = {
                                onChangeSize(it)
                            },
                            width = 0.4f,
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(fraction = 0.4f)
                            .fillMaxHeight()

                    ) {
                        Text(
                            text = "No. of Images",
                            fontSize = 14.sp
                        )
                        MyCustomDropDownMenu(
                            listItems = imageCounts.map { it.toString() },
                            selectedIndex = imageCounts.indexOf(formState.imageCount) ,
                            onChangeSelectedIndex = {
                                onChangeImageCount(it)
                            },
                        width = 0.4f,
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Button(onClick = {
                            onSubmit()

                        }) {
                            Text(text = "Generate")
                        }
                    }

                }

                when(uiState){
                    is HomeScreenUiState.Idle -> {
                        Box(modifier = Modifier.fillMaxSize()){
                            Text(
                                text = "Start generating images",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                    is HomeScreenUiState.Loading -> {
                        Box(modifier = Modifier.fillMaxSize()){
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                    is HomeScreenUiState.Error -> {
                        Box(modifier = Modifier.fillMaxSize()){
                            Text(
                                text = "An unexpected error",
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                    is HomeScreenUiState.Success -> {
                        val images = uiState.images
                        val pagerState1 = rememberPagerState(initialPage = 0)
                        val coroutineScope = rememberCoroutineScope()
                        HorizontalPager(
                            count = images.size,
                            state = pagerState1
                        ) { image ->
                            Box(
                                modifier = Modifier.fillMaxWidth()
                            ){
                                SubcomposeAsyncImage(
                                    model = images[image].url,
                                    loading = {
                                        Box(modifier = Modifier.fillMaxSize()) {
                                            CircularProgressIndicator(
                                                modifier = Modifier.align(
                                                    Alignment.Center
                                                )
                                            )
                                        }
                                    },
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(300.dp),
                                    contentDescription = "Generated Images"
                                )
                                Box(
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .width(45.dp)
                                        .align(Alignment.TopEnd)
                                        .height(25.dp)
                                        .clip(RoundedCornerShape(15.dp))
                                        .background(Color.White)

                                ){
                                    Text(
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .padding(horizontal = 3.dp),
                                        textAlign = TextAlign.Start,
                                        fontSize = 17.sp,
                                        text = "${image + 1}/${images.size}"
                                    )
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(20.dp)
                        ) {
                            PagerIndicator(
                                modifier = Modifier.align(Alignment.Center),
                                pagerState = pagerState1
                            ) {
                                coroutineScope.launch {
                                    pagerState1.scrollToPage(it)
                                }
                            }
                        }

                        Text(
                            fontSize = 13.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            text = "NB: The images might load slowly because of the API so please be patient enough for them to load ")
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(90.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center,
                        ){
                            Button(
                                onClick = {
                                    val downloader = AndroidDownloader(context = context)
                                    images.map {
                                        downloader.downloadFile(url = it.url)
                                    }

                                }
                            ){
                                Text(text = "Download Images")
                            }
                        }
                    }
                }
            }
        }
    }
}