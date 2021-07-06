package com.example.finalproject2020

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalproject2020.User.ViewProductUser
import com.example.finalproject2020.adapter.RecyclerItemClickListener
import com.example.finalproject2020.model.product_view
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_product_user.*
import kotlinx.android.synthetic.main.activity_serach_user.*
import kotlinx.android.synthetic.main.item_product.view.*

class SerachActivityUser : AppCompatActivity() {


    lateinit var Card: CardView
    var db: FirebaseFirestore? = null
    var adapter: FirestoreRecyclerAdapter<product_view, productViewHolder>? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serach_user)

        db = Firebase.firestore



/*db!!.collection("product").document(id)
        val seach = findViewById<SearchView>(R.id.search)
        seach.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(s: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                callSearch(s)
                return false
            }
        })
        // Call method Swipe to delete University....
    }*/

        db!!.collection("product")
            .whereEqualTo("name", true)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("hzm", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("hzm", "Error getting documents: ", exception)
            }


       }

    private fun getAllUsersByWhere() {
        //db.collection("users").whereGreaterThan("level",3)
        db!!.collection("users").whereGreaterThanOrEqualTo("level", 3)
            .orderBy("level", Query.Direction.DESCENDING)
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
    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_main,menu)
        val item = menu!!.findItem(R.id.action_search)

        val searchView = MenuItemCompat!!.getActionView(item)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }*/
    fun getAllUser() {
        val Cat = intent.getStringExtra("Catproduct")


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
        val cat = intent.getStringExtra("Catproduct")

        db!!.collection("product")
            .whereEqualTo("name", cat)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    Log.d("hzm", "${document.id} => ${document.data}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("hzm", "Error getting documents: ", exception)
            }

        val db = FirebaseFirestore.getInstance()
        val query =  db!!.collection("product")
            .whereEqualTo("name", cat)
        //db!!.collection("product").whereGreaterThanOrEqualTo("categroy", Cat)

        //  val query= getAllUsersByWhere()
        val options =
            FirestoreRecyclerOptions.Builder<product_view>().setQuery(query, product_view::class.java).build()


        adapter = object : FirestoreRecyclerAdapter<product_view, productViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productViewHolder {
                var view = LayoutInflater.from(this@SerachActivityUser)
                    .inflate(R.layout.item_product, parent, false)
                return productViewHolder(view)
            }

            override fun onBindViewHolder(holder: productViewHolder, position: Int, model: product_view) {
                holder.name.text = model.name
             //   holder.price.text=model.price
              //  Glide.with(this@SerachActivityUser).load(model.image).into(holder.image)

                /* cardview.setOnClickListener(View.OnClickListener {
                     /*cardview.getContext().startActivity(
                         Intent(v.getContext(),
                             YOUR_ACTIVITY_TO_START::class.java
                         )
                     )*/
                     val docId = snapshots.getSnapshot(position).id

                     Log.d("hzm", docId)
                 })*/

                Recyc.addOnItemTouchListener(
                    RecyclerItemClickListener(
                        this@SerachActivityUser, product, object : RecyclerItemClickListener.OnItemClickListener {
                            //    val c = db.recyclerCate(order)

                            // val ProductID = c.getInt(c.getColumnIndex("id"))
                            override fun onItemClick(view: View, position: Int) {
                                //     Toast.makeText(this@AllUser, "YESSSS", Toast.LENGTH_SHORT).show()



                                db!!.collection("product").get()
                                    .addOnSuccessListener { querySnapshot ->
                                        //  address.setText(querySnapshot.documents.get(0).get("address").toString())
                                        //   email.setText(auth.currentUser!!.uid)

                                        val docId = snapshots.getSnapshot(position).id

                                        Log.d("hzm", docId)
                                        val intent = Intent(this@SerachActivityUser,
                                            ViewProductUser::class.java)
                                        //  intent.putExtra("nameproduct1",querySnapshot.documents.get(0).get("name").toString())
                                        intent.putExtra("docId",docId)
                                        /*intent.putExtra("Catproduct1",querySnapshot.documents.get(0).get("categroy").toString())
                                        intent.putExtra("imageproduct1",querySnapshot.documents.get(0).get("image").toString())
                                        intent.putExtra("priceproduct1",querySnapshot.documents.get(0).get("price").toString())
                                        intent.putExtra("rateproduct1",querySnapshot.documents.get(0).get("rate").toString())*/
                                        intent.putExtra("latproduct1",querySnapshot.documents.get(0).get("lat").toString())
                                        intent.putExtra("lngproduct1",querySnapshot.documents.get(0).get("lng").toString())

                                        Toast.makeText(this@SerachActivityUser, querySnapshot.documents.get(0).get("name").toString(), Toast.LENGTH_SHORT).show()
                                        startActivity(intent)

                                    }.addOnFailureListener { exception ->

                                    }


                                //startActivity(intent)
                            }
                            override fun onLongItemClick(view: View, position: Int) {
                                //    registerForContextMenu(cardview)

                            }}
                    ))



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

        Recyc.layoutManager = LinearLayoutManager(this)

        Recyc.adapter = adapter

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
}