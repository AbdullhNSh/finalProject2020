package com.example.finalproject2020.User

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject2020.R
import com.example.finalproject2020.adapter.RecyclerItemClickListener
import com.example.finalproject2020.model.product_view
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_product_user.*
import kotlinx.android.synthetic.main.item_product.view.*


class product_user : AppCompatActivity() {

/// val Card = findViewById<CardView>(R.id.cardview)
val thoughtsCollectionRef = FirebaseFirestore.getInstance().collection("product")

    lateinit var Card: CardView
    var db: FirebaseFirestore? = null
    var adapter: FirestoreRecyclerAdapter<product_view, productViewHolder>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_user)
        db = Firebase.firestore

   getAllUser()

        back.setOnClickListener {
            val intent = Intent(this,
                MainActivityUser::class.java)
            startActivity(intent)
            finish()
        }

    }

    fun getAllUser() {
         //  val Cat = intent.getStringExtra("Catproduct")


        //val query =getAllUsersByWhere(Cat)

        /*val query=    db!!.collection("product").whereGreaterThanOrEqualTo("categroy", Cat)
            .orderBy("categroy", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    Log.e("TAG", "${document.id} => ${document.data}")
                }

            }
            .addOnFailureListener { exception ->
                Log.e("TAG", exception.message)
            }*/

      /*  val document = FirebaseFirestore.getInstance()
            .collection("users")
            .document(auth.currentUser!!.uid)*/
        val Cat = intent.getStringExtra("Catproduct")


        val db = FirebaseFirestore.getInstance()
      //  val query =  db.collection("product")//.whereEqualTo("categroy",Cat)
            //db!!.collection("product").whereGreaterThanOrEqualTo("categroy", Cat)
        val query =   thoughtsCollectionRef.whereEqualTo("categroy",Cat)
        //  val query= getAllUsersByWhere()
        val options =
            FirestoreRecyclerOptions.Builder<product_view>().setQuery(query, product_view::class.java).build()


        adapter = object : FirestoreRecyclerAdapter<product_view, productViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productViewHolder {
                var view = LayoutInflater.from(this@product_user)
                    .inflate(R.layout.item_product, parent, false)
                return productViewHolder(
                    view
                )
            }

            override fun onBindViewHolder(holder: productViewHolder, position: Int, model: product_view) {
                holder.name.text = model.name
                holder.price.text=model.price.toString()
                Glide.with(this@product_user).load(model.image).into(holder.image)

               /* cardview.setOnClickListener(View.OnClickListener {
                    /*cardview.getContext().startActivity(
                        Intent(v.getContext(),
                            YOUR_ACTIVITY_TO_START::class.java
                        )
                    )*/
                    val docId = snapshots.getSnapshot(position).id

                    Log.d("hzm", docId)
                })*/

                product.addOnItemTouchListener(
                    RecyclerItemClickListener(
                        this@product_user, product, object : RecyclerItemClickListener.OnItemClickListener {
                            //    val c = db.recyclerCate(order)

                            // val ProductID = c.getInt(c.getColumnIndex("id"))
                            override fun onItemClick(view: View, position: Int) {
                                //     Toast.makeText(this@AllUser, "YESSSS", Toast.LENGTH_SHORT).show()



                                db!!.collection("product").get()
                                    .addOnSuccessListener { querySnapshot ->
                                        val docId = snapshots.getSnapshot(position).id
                                        Log.d("hzm", docId)
                                        val intent = Intent(this@product_user,
                                            ViewProductUser::class.java)
                                        intent.putExtra("docId",docId)
                                        intent.putExtra("latproduct1",querySnapshot.documents.get(0).get("lat").toString())
                                        intent.putExtra("lngproduct1",querySnapshot.documents.get(0).get("lng").toString())
                                        Toast.makeText(this@product_user, querySnapshot.documents.get(0).get("name").toString(), Toast.LENGTH_SHORT).show()
                                        startActivity(intent)

                                    }.addOnFailureListener { exception ->

                                    }


                                //startActivity(intent)
                            }
                            override fun onLongItemClick(view: View, position: Int) {
                                //    registerForContextMenu(cardview)

                            }}
                    ))



                holder.itemView.setOnLongClickListener(View.OnLongClickListener {
                    Toast.makeText(this@product_user, "item123123", Toast.LENGTH_LONG).show()


                    true
                })


/*
Card.setOnClickListener {
    Toast.makeText(this@product_user,"id : ",Toast.LENGTH_LONG).show()

}*/





              //  Toast.makeText(this@product_user,"id : $docId",Toast.LENGTH_LONG).show()


               // Submit = loginDialog.findViewById(R.id.Submit) as Button

              /*  Card.setOnClickListener {
                    Toast.makeText(this@product_user,"$docId",Toast.LENGTH_LONG).show()

                }*/

               /* val docId = snapshots.getSnapshot(position).id

                Log.d("hzm", docId)*/

            }


        }

        product.layoutManager = LinearLayoutManager(this)
        product.adapter = adapter

       /* product.addOnItemTouchListener(
            RecyclerItemClickListener(
                this, product, object : RecyclerItemClickListener.OnItemClickListener {
                    //    val c = db.recyclerCate(order)

                    // val ProductID = c.getInt(c.getColumnIndex("id"))
                    override fun onItemClick(view: View, position: Int) {
                        //     Toast.makeText(this@AllUser, "YESSSS", Toast.LENGTH_SHORT).show()



                        db!!.collection("product").get()
                            .addOnSuccessListener { querySnapshot ->
                                //  address.setText(querySnapshot.documents.get(0).get("address").toString())
                                //   email.setText(auth.currentUser!!.uid)


                                val intent = Intent(this@product_user,ViewProductUser::class.java)
                                intent.putExtra("nameproduct1",querySnapshot.documents.get(0).get("name").toString())
                                intent.putExtra("Catproduct1",querySnapshot.documents.get(0).get("categroy").toString())
                                intent.putExtra("imageproduct1",querySnapshot.documents.get(0).get("image").toString())
                                intent.putExtra("priceproduct1",querySnapshot.documents.get(0).get("price").toString())
                                intent.putExtra("rateproduct1",querySnapshot.documents.get(0).get("rate").toString())
                                intent.putExtra("latproduct1",querySnapshot.documents.get(0).get("lat").toString())
                                intent.putExtra("lngproduct1",querySnapshot.documents.get(0).get("lng").toString())

                                Toast.makeText(this@product_user, querySnapshot.documents.get(0).get("name").toString(), Toast.LENGTH_SHORT).show()
                                startActivity(intent)

                            }.addOnFailureListener { exception ->

                            }


                        //startActivity(intent)
                    }
                    override fun onLongItemClick(view: View, position: Int) {
                        //    registerForContextMenu(cardview)

                    }}
            ))*/




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

    /*fun getAllUser() {
        val query = db!!.collection("product")
        //  val query= getAllUsersByWhere()
        val options =
            FirestoreRecyclerOptions.Builder<product_view>().setQuery(query, product_view::class.java).build()

        adapter = object : FirestoreRecyclerAdapter<product_view, productViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productViewHolder {
                var view = LayoutInflater.from(this@product_user)
                    .inflate(R.layout.item_product, parent, false)
                return productViewHolder(view)
            }

            override fun onBindViewHolder(holder: productViewHolder, position: Int, model: product_view) {
                holder.name.text = model.name
                holder.price.text=model.price
                Glide.with(this@product_user).load(model.image).into(holder.image)

            }


        }*/
/*
        RecyclerProduct.layoutManager = LinearLayoutManager(this)
        RecyclerProduct.adapter = adapter

        RecyclerProduct.addOnItemTouchListener(
            RecyclerItemClickListener(
                this, RecyclerProduct, object : RecyclerItemClickListener.OnItemClickListener {
                    //    val c = db.recyclerCate(order)

                    // val ProductID = c.getInt(c.getColumnIndex("id"))
                    override fun onItemClick(view: View, position: Int) {
                        //     Toast.makeText(this@AllUser, "YESSSS", Toast.LENGTH_SHORT).show()

                        db!!.collection("product").get()
                            .addOnSuccessListener { querySnapshot ->



                                val intent = Intent(this@product_user,ViewProductAdmin::class.java)
                                intent.putExtra("nameproduct",querySnapshot.documents.get(0).get("name").toString())
                                intent.putExtra("Catproduct",querySnapshot.documents.get(0).get("categroy").toString())
                                intent.putExtra("imageproduct",querySnapshot.documents.get(0).get("image").toString())
                                intent.putExtra("priceproduct",querySnapshot.documents.get(0).get("price").toString())
                                intent.putExtra("rateproduct",querySnapshot.documents.get(0).get("rate").toString())
                                intent.putExtra("latproduct",querySnapshot.documents.get(0).get("lat").toString())
                                intent.putExtra("lngproduct",querySnapshot.documents.get(0).get("lng").toString())

                                Toast.makeText(this@product_user, querySnapshot.documents.get(0).get("name").toString(), Toast.LENGTH_SHORT).show()
                                startActivity(intent)

                            }.addOnFailureListener { exception ->

                            }


                        //startActivity(intent)
                    }
                    override fun onLongItemClick(view: View, position: Int) {
                        //    registerForContextMenu(cardview)

                    }}
            ))




    }

    class productViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.productName

        var image = view.productImage
        var price = view.productPrice
    }
*/


    private fun getAllUsersByWhere(categroy1 :String) {
        //db.collection("users").whereGreaterThan("level",3)

    }
}
