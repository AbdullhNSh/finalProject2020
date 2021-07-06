package com.example.finalproject2020.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject2020.R
import com.example.finalproject2020.model.Student
import kotlinx.android.synthetic.main.item.view.*


class StudentAdapter(var activity : Activity, val data: MutableList<Student>): RecyclerView.Adapter<StudentAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val tvName = itemView.tvUser
        val tvPass = itemView.tvPass
        val tvEmail = itemView.tvEmail
        val tvNumber = itemView.tvNumber
        val login = itemView.tvlogin







    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(activity).inflate(R.layout.item, parent, false)
        return MyViewHolder(
            itemView
        )
    }
override fun getItemCount():Int{
    return data.size
}
    override fun onBindViewHolder(holder: MyViewHolder, position: Int){
        holder.tvName.text= data[position].username
        holder.tvPass.text=data[position].password
        holder.tvEmail.text=data[position].email
        holder.tvNumber.text=data[position].phonrnumber
        holder.login.text=data[position].login



    }


}


