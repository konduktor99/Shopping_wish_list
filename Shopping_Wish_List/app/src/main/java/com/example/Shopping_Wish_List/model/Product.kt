package com.example.Shopping_Wish_List.model
import android.graphics.Bitmap
import java.time.LocalDateTime

data class Product(

                    val id: Int,
                    val title : String?,
//                   val resId : Int?,
                    val localization : String?,
                    val img: Bitmap){



}

