package com.peterchege.aiimagegenerator.models

data class RequestBody (
    val prompt:String,
    val n:Int,
    val size:String,

        )