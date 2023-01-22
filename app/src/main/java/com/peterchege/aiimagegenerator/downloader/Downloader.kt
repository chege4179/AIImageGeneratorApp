package com.peterchege.aiimagegenerator.downloader

interface Downloader {
    fun downloadFile(url: String): Long
}