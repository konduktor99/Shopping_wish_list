package com.example.Shopping_Wish_List


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Bitmap.CompressFormat
import android.location.Geocoder
import android.location.Location
import java.io.ByteArrayOutputStream


class Conversion() {

    companion object{

        public fun bitmapToByteArray(bitmap: Bitmap) : ByteArray{

            val blob = ByteArrayOutputStream()
            bitmap.compress(CompressFormat.PNG, 0, blob)
            val bitmapdata: ByteArray = blob.toByteArray()

            return bitmapdata
        }

    }



}