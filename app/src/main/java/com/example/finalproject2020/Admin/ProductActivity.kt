package com.example.finalproject2020.Admin

import android.app.ProgressDialog
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
import com.example.finalproject2020.R
import com.example.finalproject2020.model.product_view
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_product.*
import kotlinx.android.synthetic.main.activity_product.back
import kotlinx.android.synthetic.main.item_product.view.*

class ProductActivity : AppCompatActivity() {


    val productsCollectionRef = FirebaseFirestore.getInstance().collection("product")


    lateinit var auth: FirebaseAuth
    var storage: FirebaseStorage? = null
    var reference: StorageReference? = null
    lateinit var prog: ProgressDialog
    var path: String? = null
    var db: FirebaseFirestore? = null
    val idCat = ""

    var adapter: FirestoreRecyclerAdapter<product_view, productViewHolder>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product)

        auth = Firebase.auth
        storage = Firebase.storage
        reference = storage!!.reference
        prog = ProgressDialog(this)
        prog.setMessage("جاري التحميل")
        prog.setCancelable(false)
        db = Firebase.firestore

        val idCat = intent.getStringExtra("idCat")
        Log.e("docId", idCat.toString())

        getAllUser(idCat.toString())

        back.setOnClickListener {
            val intent = Intent(this, MainActivityAdmin::class.java)
            startActivity(intent)
        }

        add.setOnClickListener {
            val intent = Intent(this, AddProduct::class.java)
            intent.putExtra("idCat", idCat)
            startActivity(intent)
        }
    }

    fun getAllUser(docId: String) {
        val query = db!!.collection("categories").document(docId).collection("Product")

        val options =
            FirestoreRecyclerOptions.Builder<product_view>()
                .setQuery(query, product_view::class.java).build()

        adapter = object : FirestoreRecyclerAdapter<product_view, productViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productViewHolder {
                var view = LayoutInflater.from(this@ProductActivity)
                    .inflate(R.layout.item123, parent, false)
                return productViewHolder(
                    view
                )
            }

            override fun onBindViewHolder(
                holder: productViewHolder,
                position: Int,
                model: product_view
            ) {
                holder.name.text = model.name
                holder.price.text = model.price.toString()
                Glide.with(this@ProductActivity).load(model.image).into(holder.image)


                holder.itemView.setOnClickListener {
                    val doId = snapshots.getSnapshot(position).id
                    Log.d("hzm", docId)
                    Log.e("hzzzm", doId)
                    val intent = Intent(this@ProductActivity, ViewProductAdmin::class.java)
                    intent.putExtra("idProduct", doId)
                    intent.putExtra("idCat", docId)
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
        var price = view.productPrice
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }

    private fun getAllUsersByWhere() {
        val nameCat = intent.getStringExtra("nameCat")
        db!!.collection("users").whereGreaterThanOrEqualTo("${nameCat}", 10)
            .orderBy("${nameCat}", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    Log.e("hzm", "${document.id} => ${document.data}")
                }

            }
            .addOnFailureListener { exception ->
                Log.e("hzm", exception.message.toString())
            }
    }
}

