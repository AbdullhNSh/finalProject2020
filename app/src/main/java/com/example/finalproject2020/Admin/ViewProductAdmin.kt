package com.example.finalproject2020.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.finalproject2020.MapsProduct
import com.example.finalproject2020.R
import com.example.finalproject2020.UpdateProduct
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_view_product_admin.*
import kotlinx.android.synthetic.main.activity_view_product_admin.back
import kotlinx.android.synthetic.main.activity_view_product_admin.catproduct
import kotlinx.android.synthetic.main.activity_view_product_admin.imgProduct
import kotlinx.android.synthetic.main.activity_view_product_admin.nameproduct
import kotlinx.android.synthetic.main.activity_view_product_admin.priceproduct
import kotlinx.android.synthetic.main.activity_view_product_admin.rateproduct


class ViewProductAdmin : AppCompatActivity() {

    var db: FirebaseFirestore? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_product_admin)


        val idProduct = intent.getStringExtra("idProduct")
        val idCat = intent.getStringExtra("idCat")
        Log.e("idProduct",idProduct.toString())



        db = Firebase.firestore

        udpateProduct.setOnClickListener {

            val intent = Intent(this, UpdateProduct::class.java)


         //   startActivity(intent)

            //  intent.putExtra("id",id)

           db!!.collection("categories").document(idCat!!).collection("Product").document(idProduct!!).get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("TAG", "DocumentSnapshot data: ${document.data}")

                        val lat =  document.getString("lat")
                        val lng =  document.getString("lng")
                       // val lat =  intent.getStringExtra("latproduct2")
                        //val lng =  intent.getStringExtra("lngproduct2")

                       // val intent = Intent(this,MapsProduct::class.java)
                        intent.putExtra("idCat",idCat)
                        intent.putExtra("idProduct",idProduct)
                        intent.putExtra("latProduct1",lat)
                        intent.putExtra("lngProduct1",lng)
                        startActivity(intent)


                        //   document.get(0).get("categroy").toString()
                    } else {
                        Log.d("TAG", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("TAG", "get failed with ", exception)
                }
        }



        deleteProduct.setOnClickListener {
           deleteUserById(idCat!!,idProduct!!)
        }

        db!!.collection("categories").document(idCat!!).collection("Product").document(idProduct!!).get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")

                    val name =     document.getString("name")
                    val catg =  document.getString("categroy")
                    val image =  document.getString("image")
                    val price =  document.getDouble("price")
                    val rate =  document.getString("rate")
                  /*  val lat =  intent.getStringExtra("lat")
                    val lng =  intent.getStringExtra("lng")*/

                    nameproduct.setText(name)
                    priceproduct.setText("${price} $")
                    rateproduct.setText(rate)
                    catproduct.setText(catg)
                    Glide.with(this).load(image).into(imgProduct)


                    //   document.get(0).get("categroy").toString()
                    Toast.makeText(this,"$name", Toast.LENGTH_LONG).show()


                } else {
                    Log.d("TAG", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }

        val name =  intent.getStringExtra("nameproduct")
        val catg =  intent.getStringExtra("Catproduct")
        val image =  intent.getStringExtra("imageproduct")
        val price =  intent.getStringExtra("priceproduct")
        val rate =  intent.getStringExtra("rateproduct")





        nameproduct.setText(name)
        priceproduct.setText(price+"$")
        rateproduct.setText(rate)
        catproduct.setText(catg)
        Glide.with(this).load(image).into(imgProduct)



        back.setOnClickListener {
            val intent = Intent(this,
                ProductActivity::class.java)
            startActivity(intent)
        }

        mapProduct1.setOnClickListener {
            val intent = Intent(this, MapsProduct::class.java)

            val docRef =   db!!.collection("categories").document(idCat!!).collection("Product").document(idProduct!!)

            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("TAG", "DocumentSnapshot data: ${document.data}")

                        val lat = document.getString("lat")
                        val lng = document.getString("lng")

                        intent.putExtra("latProduct", lat)
                        intent.putExtra("lngProduct", lng)
                        startActivity(intent)


                        //   document.get(0).get("categroy").toString()


                    } else {
                        Log.d("TAG", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("TAG", "get failed with ", exception)
                }



        }


}

    private fun deleteUserById(idCat: String,idProduct:String) {
        db!!.collection("categories").document(idCat).collection("Product").document(idProduct)

            .delete()
            .addOnSuccessListener {
                Log.e("TAG", "Product deleted successfully")
                Toast.makeText(this,"Product deleted successfully",Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { exception ->
                Log.e("TAG", exception.message.toString())
            }
    }










/* db!!.collection("product").whereGreaterThanOrEqualTo("id", 1)
     .orderBy("id", Query.Direction.DESCENDING)*/
/*db!!.collection("product").document(id)

    .get()
    .addOnSuccessListener { querySnapshot ->
        for (document in querySnapshot) {
            Log.e("hzm", "${document.id} => ${document.data}")
        }

    }
    .addOnFailureListener { exception ->
        Log.e("hzm", exception.message)
    }*/

// getAllUsersByWhere(id)







private fun getAllUsersByWhere(id:String) {
    //db.collection("product").whereGreaterThan("level",3)
    db!!.collection("product").whereGreaterThanOrEqualTo(id, 1)
        .orderBy(id, Query.Direction.DESCENDING)
        .get()
        .addOnSuccessListener { querySnapshot ->
            for (document in querySnapshot) {
                Log.e("TAG", "${document.id} => ${document.data}")
            }

        }
        .addOnFailureListener { exception ->
            Log.e("TAG", exception.message.toString())
        }
}}
