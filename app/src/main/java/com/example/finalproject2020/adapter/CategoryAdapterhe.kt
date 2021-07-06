package com.example.finalproject2020.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.content.Context
import com.example.finalproject2020.R
import com.example.finalproject2020.model.categories
import kotlinx.android.synthetic.main.item_category.view.*


class CategoryAdapterhe(val context: Context, val list: ArrayList<categories>)://, val click: onClick) :
    RecyclerView.Adapter<CategoryAdapterhe.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflate = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       //  holder.tvImage?.setImageResource(list[position].image)
      //  holder.tvImage?.setImageResource(list[position].image!!.toInt()
               // holder.tvImage?.setImageResource(list[position].image)
       // holder.tvImage.setImageResource(list.get(position).image);



        /* val resourceId = activity.resources.getIdentifier(data[position].image, "drawable", activity.packageName)
         holder.tvImage?.setImageResource(resourceId)*/

        //  holder.tvImage?.setImageURI(list[position].image)
      //  holder.tvImage.setImageURI(Uri.parse(list[position].image))

        // holder.img?.

        /*holder.card.setOnClickListener {
            click.onClickItem(holder.adapterPosition)
        }*/
    }


    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {
      //  val tvImage =  itemView.categoryImage

    }

    interface onClick {
        fun onClickItem(position: Int)
    }

}