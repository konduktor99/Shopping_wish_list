package com.example.Shopping_Wish_List.data

import android.graphics.Bitmap
import com.example.Shopping_Wish_List.model.Product

object DataSource {

    var weekTaskCounter=0


    val products = mutableListOf<Product>(
//        Product("słuchawki"," ul. koszykowa, Warszawa, Polska", Bitmap.createBitmap(100,400, Bitmap.Config.ARGB_8888)),
//        Product("kask", " ul. koszykowa, Warszawa, Polska",Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888)),
//        Product("indian pickled garlick", " ul. koszykowa, Warszawa, Polska",Bitmap.createBitmap(100,100, Bitmap.Config.ARGB_8888))


    )



    fun getPreaparedProductList(): MutableList<Product> {


        return products
    }
}