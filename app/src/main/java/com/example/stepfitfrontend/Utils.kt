package com.example.stepfitfrontend

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream


object Utils {
    fun Bitmap.bitmapToBase64():String{
        val byteArrayOutputStream = ByteArrayOutputStream()
        this.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream)
        val imageBytes:ByteArray = byteArrayOutputStream.toByteArray()
        return Base64.encodeToString(imageBytes,Base64.DEFAULT)
    }
}