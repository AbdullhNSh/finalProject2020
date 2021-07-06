package com.example.finalproject2020.ClickListeners

import android.view.View
import com.example.finalproject2020.model.categories

interface RecyclerViewClickListener {
    fun onClick(view: View?, categories: categories?)
}