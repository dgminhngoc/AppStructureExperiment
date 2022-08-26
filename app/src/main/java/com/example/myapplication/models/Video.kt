package com.example.myapplication.models

data class Video (val qualities: List<VideoQuality>)

data class VideoQuality(val width: Int, val quality: String, val url: String)