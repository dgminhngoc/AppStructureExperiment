package com.example.myapplication.models

import java.util.*

data class  News (
    val id: Int,
    val title: String,
    val discarded: Boolean,
    val text: String,
    val imageId: Int,
    val timeUpdated: Date,
    val timeCreated: Date,
    val timePublished: Date
)