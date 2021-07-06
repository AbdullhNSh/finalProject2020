package com.example.finalproject2020.Admin

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject2020.R
import com.example.finalproject2020.adapter.RecyclerItemClickListener
import com.example.finalproject2020.model.categories
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_cat.*
import kotlinx.android.synthetic.main.activity_cat.add
import kotlinx.android.synthetic.main.activity_cat.back
import kotlinx.android.synthetic.main.item_category1.view.*


class CatActivity : AppCompatActivity() {

    var db: FirebaseFirestore? = null
    var adapter: FirestoreRecyclerAdapter<categories, categoriesViewHolder>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat)
        db = Firebase.firestore

        getAllUser()

        add.setOnClickListener {
            val intent = Intent(this, AddCateg::class.java)
            startActivity(intent)
        }

        back.setOnClickListener {
            val intent = Intent(this, MainActivityAdmin::class.java)
            startActivity(intent)
        }
    }

    fun getAllUser() {
        val query = db!!.collection("categories")
        val options =
            FirestoreRecyclerOptions.Builder<categories>().setQuery(query, categories::class.java)
                .build()

        adapter = object : FirestoreRecyclerAdapter<categories, categoriesViewHolder>(options) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): categoriesViewHolder {
                var view = LayoutInflater.from(this@CatActivity)
                    .inflate(R.layout.item_category1, parent, false)
                return categoriesViewHolder(
                    view
                )
            }

            override fun onBindViewHolder(
                holder: categoriesViewHolder,
                position: Int,
                model: categories
            ) {
                holder.name.text = model.name



                Glide.with(this@CatActivity).load(model.image).into(holder.image)

                holder.itemView.setOnClickListener {


                    db!!.collection("product").get()
                        .addOnSuccessListener { querySnapshot ->


                            val intent = Intent(this@CatActivity, ProductActivity::class.java)
                            intent.putExtra(
                                "nameCat",
                                querySnapshot.documents.get(0).get("name").toString()
                            )


                            Toast.makeText(
                                this@CatActivity,
                                querySnapshot.documents.get(0).get("name").toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                            startActivity(intent)

                        }.addOnFailureListener { exception ->

                        }


                }
                holder.itemView.setOnLongClickListener {

                    registerForContextMenu(holder.itemView)
                    true
                }
            }


        }

        recyclerCate.layoutManager = GridLayoutManager(this, 2)
        recyclerCate.adapter = adapter


    }

    class categoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.categoryName

        var image = view.categoryImage
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
