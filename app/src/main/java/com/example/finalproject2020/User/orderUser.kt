package com.example.finalproject2020.User

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject2020.Admin.CatrgoryEdit
import com.example.finalproject2020.Admin.MainActivityAdmin
import com.example.finalproject2020.Admin.ProductActivity
import com.example.finalproject2020.R
import com.example.finalproject2020.model.categories
import com.example.finalproject2020.model.order
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_order_user.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.item_category1.view.*
import kotlinx.android.synthetic.main.item_order.view.*

class orderUser : AppCompatActivity() {

    var db: FirebaseFirestore? = null
    lateinit var auth: FirebaseAuth
    var idCat = ""
    var idProduct = ""
    var adapter: FirestoreRecyclerAdapter<order, orderViewHolder>? = null
    var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_user)

        db = Firebase.firestore
        auth = Firebase.auth

        db!!.collection("users").whereEqualTo("email", auth.currentUser!!.email).get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    id = document.id
                    //  db!!.collection("users").document(document.id).collection("order")
                    // //     .get()
                    //  Log.d("TAG", "${document.id} => ${document.data}")
                }

            }

        getAllUser(id)
    }


    fun getAllUser(id: String) {

        /* db!!.collection("users").whereEqualTo("email", auth.currentUser!!.email).get()
             .addOnSuccessListener { querySnapshot ->
                 for (document in querySnapshot) {

                     db!!.collection("users").document(document.id).collection("order")
                         .get()
                     Log.d("TAG", "${document.id} => ${document.data}")
                 }

             }*/

        /* db!!.collection("users").whereEqualTo("email", auth.currentUser!!.email).get()
             .addOnSuccessListener { querySnapshot ->
                 for (document in querySnapshot) {

 */
        val query = db!!.collection("users").document("OoKVEjOxsQxHp3QRCQvq").collection("order")
        val options =
            FirestoreRecyclerOptions.Builder<order>().setQuery(query, order::class.java)
                .build()

        adapter = object : FirestoreRecyclerAdapter<order, orderViewHolder>(options) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): orderViewHolder {
                var view = LayoutInflater.from(this@orderUser)
                    .inflate(R.layout.item_order, parent, false)
                return orderViewHolder(
                    view
                )
            }

            override fun onBindViewHolder(
                holder: orderViewHolder,
                position: Int,
                model: order
            ) {
                holder.name.text = model.name
                Glide.with(this@orderUser).load(model.image).into(holder.image)
                holder.num.text = model.num.toString()
                holder.price.text = model.price.toString()
                //holder.image.set(model.imgOrder)


            }


        }

        recyclerOrder.layoutManager = GridLayoutManager(this, 1)
        recyclerOrder.adapter = adapter

        //    }}
    }

    class orderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.tv_orderName

        var image = view.img_order
        var price = view.tv_price
        var num = view.tv_num
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }


    fun addOrder(idCat: String, idProduct: String, num: Int) {

        getIdAuth()
        val order = hashMapOf(
            "idCat" to idCat,
            "idProduct" to idProduct,
            "num" to num

        )
        //getIdAuth()
        db!!.collection("users").whereEqualTo("email", auth.currentUser!!.email).get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {

                    db!!.collection("users").document(document.id).collection("order")
                        .get()
                    Log.d("TAG", "${document.id} => ${document.data}")
                }

            }
    }

    fun getIdAuth(): String {

        var id: String = ""
        db!!.collection("users").whereEqualTo("email", auth.currentUser!!.email).get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    id = document.id
                    Log.d("TAG", "${document.id} => ${document.data}")
                }

            }.addOnFailureListener { exception ->

            }
        return id
    }
}


/*/*holder.itemView.setOnClickListener {
                    val idCat = snapshots.getSnapshot(position).id

                    Log.d("hzm", idCat)


                    val docRef = db!!.collection("categories").document(idCat)
                    docRef.get()
                        .addOnSuccessListener { document ->
                            if (document != null) {
                                Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                                val intent = Intent(
                                    this@orderUser,
                                    ProductActivity::class.java
                                )

                                val name = document.getString("name")
                                Toast.makeText(this@orderUser, "$name", Toast.LENGTH_LONG)
                                    .show()
                                intent.putExtra("idCat", idCat)
                                intent.putExtra("Catproduct", name)
                                startActivity(intent)


                            }
                        }
                }*/

                holder.itemView.setOnLongClickListener(View.OnLongClickListener {


                    val docId = snapshots.getSnapshot(position).id
                    Log.d("hzm", docId)

                    val intent = Intent(
                        this@orderUser,
                        CatrgoryEdit::class.java
                    )

                    intent.putExtra("id", docId)
                    startActivity(intent)

                    //    startActivity(intent)

                    /* val docRef = db!!.collection("categories").document(docId)
                     docRef.get()
                         .addOnSuccessListener { document ->
                             if (document != null) {
                                 Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                                 val intent= Intent(this@MainActivityAdmin,ProductActivity::class.java)

                                 val name =     document.getString("name")
                                 Toast.makeText(this@MainActivityAdmin,"$name",Toast.LENGTH_LONG).show()
                                 intent.putExtra("Catproduct",name)
                                 startActivity(intent)


                             }
                         }*/



                    true
                })*/