package com.example.Shopping_Wish_List.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {

    @Query("SELECT * FROM product ORDER BY id DESC;" )
    fun getAll(): List<ProductEntity>

    @Insert
    fun addProduct(newProduct: ProductEntity)

    @Query("UPDATE  product SET title = :title, localization = :localization,image = :image WHERE id=:id")
    fun updateProduct(id: Int,title: String, localization : String, image: ByteArray)

    @Query("DELETE FROM product WHERE id = :id")
    fun deleteProduct(id:Int)

    @Query("Select * FROM product WHERE id = :id")
    fun getProduct(id:Int) : ProductEntity
}
