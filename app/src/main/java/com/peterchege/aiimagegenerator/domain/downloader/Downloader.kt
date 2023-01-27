package com.peterchege.aiimagegenerator.domain.downloader

interface Downloader {
    fun downloadFile(url: String): Long
}