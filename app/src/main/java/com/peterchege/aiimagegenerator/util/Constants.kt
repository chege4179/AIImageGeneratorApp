package com.peterchege.aiimagegenerator.util

import com.peterchege.aiimagegenerator.BuildConfig



object Constants {
     var OPEN_AI_API_KEY: String = BuildConfig.OPEN_AI_API_KEY

    const val BASE_URL = "https://api.openai.com/v1/images/"
}