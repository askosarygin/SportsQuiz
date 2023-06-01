package com.example.common

import android.graphics.Bitmap

data class Wallpaper(
    val id: Long,
    val name: String,
    val price: Int,
    val imageResource: Bitmap
) : java.io.Serializable