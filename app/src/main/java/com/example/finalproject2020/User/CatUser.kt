package com.example.finalproject2020.User

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject2020.R
import com.example.finalproject2020.adapter.RecyclerItemClickListener
import com.example.finalproject2020.model.categories
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_cat_user.*
import kotlinx.android.synthetic.main.item_category1.view.*

class CatUser : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    var storage: FirebaseStorage? = null
    var reference: StorageReference? = null
    lateinit var progressDialog: ProgressDialog
    var path: String? = null

    var db: FirebaseFirestore? = null
    var adapter: FirestoreRecyclerAdapter<categories, categoriesViewHolder>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cat_user)

        db = Firebase.firestore
        auth = Firebase.auth
        storage = Firebase.storage
        reference = storage!!.reference


        getAllUser()

        backCatUser.setOnClickListener {
            val intent = Intent(
                this,
                MainActivityUser::class.java
            )
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
                var view = LayoutInflater.from(this@CatUser)
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



                Glide.with(this@CatUser).load(model.image).into(holder.image)



                recyclerCateUser.addOnItemTouchListener(
                    RecyclerItemClickListener(
                        this@CatUser,
                        recyclerCateUser,
                        object : RecyclerItemClickListener.OnItemClickListener {
                            //    val c = db.recyclerCate(order)

                            // val ProductID = c.getInt(c.getColumnIndex("id"))
                            override fun onItemClick(view: View, position: Int) {
                                //     Toast.makeText(this@AllUser, "YESSSS", Toast.LENGTH_SHORT).show()

                                db!!.collection("product").get()
                                    .addOnSuccessListener { querySnapshot ->
                                        //  address.setText(querySnapshot.documents.get(0).get("address").toString())
                                        //   email.setText(auth.currentUser!!.uid)


                                        val intent = Intent(
                                            this@CatUser,
                                            product_user::class.java
                                        )
                                        intent.putExtra(
                                            "nameCat",
                                            querySnapshot.documents.get(0).get("name").toString()
                                        )


                                        Toast.makeText(
                                            this@CatUser,
                                            querySnapshot.documents.get(0).get("name").toString(),
                                            Toast.LENGTH_SHORT
                                        ).show()
                                        startActivity(intent)

                                    }.addOnFailureListener { exception ->

                                    }


                                //startActivity(intent)
                            }

                            override fun onLongItemClick(view: View, position: Int) {
                                //    registerForContextMenu(cardview)

                            }
                        }
                    ))

            }


        }

        recyclerCateUser.layoutManager = GridLayoutManager(this, 2)
        recyclerCateUser.adapter = adapter


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

    private fun showDialog() {
        progressDialog = ProgressDialog(MainActivity@this)
        progressDialog!!.setMessage("Uploading image ...")
        progressDialog!!.setCancelable(false)
        progressDialog!!.show()
    }

    private fun hideDialog(){
        if(progressDialog!!.isShowing)
            progressDialog!!.dismiss()
    }
}


