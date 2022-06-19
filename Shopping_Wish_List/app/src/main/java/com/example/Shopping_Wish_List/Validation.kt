package com.example.Shopping_Wish_List

import java.time.LocalDateTime

class Validation {



    companion object {


        fun normalizeTitle(title: String): String {
            val newTitle = title.replace("\\s".toRegex(), "")
            if (newTitle.isEmpty()) {
                return "untitled"
            } else {
                return title
            }
        }

    }


}