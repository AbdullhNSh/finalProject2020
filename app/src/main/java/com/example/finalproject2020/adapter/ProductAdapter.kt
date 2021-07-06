package com.example.finalproject2020.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject2020.R
import com.example.finalproject2020.model.product
import com.example.finalproject2020.model.product1
import com.example.finalproject2020.model.product_view
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.item_product.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ProductAdapter(val activity : Activity, val list: ArrayList<product_view>):
    RecyclerView.Adapter<ProductAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var inflate = LayoutInflater.from(parent?.context).inflate(R.layout.item_product, parent, false)
        return ViewHolder(inflate)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       // holder?.bindThought(list[position])

        //  holder.tvName.text = list[position].name
        //  holder.tvImage?.setImageResource(list[position].image)
        //  holder.tvImage?.setImageResource(list[position].image!!.toInt()
        // holder.tvImage?.setImageResource(list[position].image)
        //holder.tvImage.setImageResource(list.get(position).image);


       /* val resourceId =
            activity.resources.getIdentifier(data[position].image, "drawable", activity.packageName)
        holder.tvImage?.setImageResource(resourceId) * */

        //  holder.tvImage?.setImageURI(list[position].image)
        //  holder.tvImage.setImageURI(Uri.parse(list[position].image))

        //

     /*   holder.card.setOnClickListener {
            click.onClickItem(holder.adapterPosition)
            holder.img?.
        }*/
    }

    class ViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        val name = itemView?.findViewById<TextView>(R.id.productName)
        val image = itemView?.findViewById<ImageView>(R.id.productImage)




        fun bindThought(product: product1){

            name.text = product.name
           // holder.tvImage?.setImageResource(product.image)
           // val tvName =itemView.productName
            val image =  product.image

          /*  val dateFormatter = SimpleDateFormat("MMM d, h:mm a", Locale.getDefault())
            //  val dateString = dateFormatter.format(thought.timestamp)
            //  timestamp?.text = dateString.toString()

            LikesImage.setOnClickListener {
                FirebaseFirestore.getInstance().collection(THOUGHTS_REF).document(thought.documentId)
                    .update(NUM_LIKES,thought.numLikes +1)*/
            }








    }}