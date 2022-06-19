package com.example.Shopping_Wish_List

interface Navigable {

    enum class Destination{
        List, Add, Edit, Pic, Paint, Snap
    }
    fun navigate(to: Destination)

}