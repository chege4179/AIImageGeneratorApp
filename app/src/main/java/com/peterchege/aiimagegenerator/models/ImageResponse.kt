package com.peterchege.aiimagegenerator.models

data class ImageResponse (
    val created:String,
    val data:List<ImageItem>
        )

data class ImageItem(
    val url:String
)