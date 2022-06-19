package com.example.Shopping_Wish_List.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ProductDao {

    @Query("Select * from product;" )
    fun getAll(): List<ProductEntity>

    @Insert
    fun addProduct(newProduct: ProductEntity)

    @Update
    fun updateProduct(newProduct: ProductEntity)

    @Query("DELETE FROM product WHERE id = :id")
    fun deleteProduct(id:Int)

    @Query("Select * FROM product WHERE id = :id")
    fun getProduct(id:Int) : ProductEntity
}
