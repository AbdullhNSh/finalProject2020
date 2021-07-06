package com.example.finalproject2020

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject2020.Admin.ViewProductAdmin
import com.example.finalproject2020.model.productmorebought
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_view_productmorebought.*

import kotlinx.android.synthetic.main.itemproductmorepought.view.*

class ViewProductmorebought : AppCompatActivity(){

val productsCollectionRef = FirebaseFirestore.getInstance().collection("product")
    var adapter: FirestoreRecyclerAdapter<productmorebought, productViewHolder>? = null
    var db: FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_productmorebought)

        db = Firebase.firestore
getAllUser()
    }


    fun getAllUser() {

       // val Cat = intent.getStringExtra("Catproduct")


        val query =   productsCollectionRef//.orderBy("numpay", Query.Direction.DESCENDING)
        val options =
            FirestoreRecyclerOptions.Builder<productmorebought>().setQuery(query, productmorebought::class.java).build()

        adapter = object : FirestoreRecyclerAdapter<productmorebought, productViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productViewHolder {
                var view = LayoutInflater.from(this@ViewProductmorebought)
                    .inflate(R.layout.itemproductmorepought, parent, false)
                return productViewHolder(view)
            }

            override fun onBindViewHolder(holder: productViewHolder, position: Int, model: productmorebought) {
                holder.name.text = model.name
                holder.price.text = model.numPay.toString()
                Glide.with(this@ViewProductmorebought).load(model.image).into(holder.image)


                holder.itemView.setOnClickListener {
                    val docId = snapshots.getSnapshot(position).id
                    Log.d("hzm", docId)
                    val intent= Intent(this@ViewProductmorebought,
                        ViewProductAdmin::class.java)
                    intent.putExtra("docId",docId)
                    startActivity(intent)
                }
            }
        }

        RecyclerProduct.layoutManager = LinearLayoutManager(this)
        RecyclerProduct.adapter = adapter


    }


    class productViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.productName
        var image = view.productImage
        var price = view.productNum
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }
}