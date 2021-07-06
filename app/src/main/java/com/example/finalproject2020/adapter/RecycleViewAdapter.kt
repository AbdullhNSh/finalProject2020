package com.example.finalproject2020.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject2020.R
import com.example.finalproject2020.model.Model
import kotlinx.android.synthetic.main.recycle_item.view.*

class RecycleViewAdapter(val context: Context, val list: ArrayList<Model>, val click: onClick) :
    RecyclerView.Adapter<RecycleViewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflate = LayoutInflater.from(context).inflate(R.layout.recycle_item, parent, false)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name?.text = list[position].name
        holder.mobile?.text = list[position].mobile
        holder.img?.setImageResource(list[position].img)
        holder.card.setOnClickListener {
            click.onClickItem(holder.adapterPosition)
        }
    }


    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val img = item.img
        val name = item.tv_name
        val mobile = item.tv_mobile
        val card = item.cardview

    }

    interface onClick {
        fun onClickItem(position: Int)
    }

}