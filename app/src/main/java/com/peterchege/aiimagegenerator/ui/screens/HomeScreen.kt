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
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.SubcomposeAsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.peterchege.aiimagegenerator.ui.components.MyCustomDropDownMenu
import com.peterchege.aiimagegenerator.ui.components.PagerIndicator
import com.peterchege.aiimagegenerator.ui.viewModels.HomeScreenViewModel
import com.peterchege.aiimagegenerator.util.imageCounts
import com.peterchege.aiimagegenerator.util.imageSizes
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController:NavController,
    viewModel:HomeScreenViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier
            .fillMaxSize(),

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
            if (viewModel.isLoading.value) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
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
                        Text("Enter Image Description")
                    },
                    value = viewModel.prompt.value,
                    onValueChange = {
                        viewModel.onChangePrompt(it)

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
                            selectedIndex = viewModel.selectedImageSizeIndex.value,
                            onChangeSelectedIndex = {
                                Log.d("Index1", it.toString())
                                viewModel.onChangeSelectedImageSizeIndex(it)
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
                            selectedIndex = viewModel.selectedImageCountIndex.value,
                            onChangeSelectedIndex = {
                                Log.d("Index2", it.toString())
                                viewModel.onChangeSelectedImageCountIndex(it)
                            },
                        width = 0.4f,
                        )
                    }
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Button(onClick = {
                            viewModel.generateImages(scaffoldState = scaffoldState)
                        }) {
                            Text(text = "Generate")
                        }
                    }

                }

                if (viewModel.generatedImages.value.isNotEmpty()){

                    val pagerState1 = rememberPagerState(initialPage = 0)
                    val coroutineScope = rememberCoroutineScope()
                    HorizontalPager(
                        count = viewModel.generatedImages.value.size,
                        state = pagerState1
                    ) { image ->
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ){
                            SubcomposeAsyncImage(
                                model = viewModel.generatedImages.value[image].url,
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
                                contentDescription = "Product Images"
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
                                    text = "${image + 1}/${viewModel.generatedImages.value.size}"
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

                }
            }
        }
    }
}