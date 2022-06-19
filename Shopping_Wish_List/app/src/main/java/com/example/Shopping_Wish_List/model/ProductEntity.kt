package com.example.Shopping_Wish_List.model

import android.graphics.Bitmap
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int =0,
    val title : String?,
//                   val resId : Int?,
    val localization : String?,
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    val image: ByteArray
    )



