package com.example.finalproject2020.ClickListeners

import android.view.View
import com.google.firestore.v1.StructuredQuery

/**
 * Created by yousra on 04/10/2017.
 */
interface RecyclerViewClickListenerOrder {
    fun onClick(view: View?, product: StructuredQuery.Order?)
}