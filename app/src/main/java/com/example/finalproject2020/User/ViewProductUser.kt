package com.example.finalproject2020.User

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.finalproject2020.MapsProduct
import com.example.finalproject2020.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_profile_user.*
import kotlinx.android.synthetic.main.activity_view_product_user.*
import kotlinx.android.synthetic.main.activity_view_product_user.back
import kotlinx.android.synthetic.main.activity_view_product_user.catproduct
import kotlinx.android.synthetic.main.activity_view_product_user.imgProduct
import kotlinx.android.synthetic.main.activity_view_product_user.nameproduct
import kotlinx.android.synthetic.main.activity_view_product_user.priceproduct
import kotlinx.android.synthetic.main.activity_view_product_user.rateproduct

class ViewProductUser : AppCompatActivity() {
    var db: FirebaseFirestore? = null
    lateinit var auth: FirebaseAuth
    var idCat = ""
    var idProduct = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_product_user)

        db = Firebase.firestore
        auth = Firebase.auth

        var num = tv_num.text.toString().toInt()




        minus.setOnClickListener {
            if (num > 1) {


                num -= 1
                tv_num.text = num.toString()
            }
        }
        plus.setOnClickListener {
            num += 1
            tv_num.text = num.toString()

        }


        idCat = intent.getStringExtra("idCat").toString()
        idProduct = intent.getStringExtra("idProduct").toString()
        addtocartBtn.setOnClickListener {


            addOrder(idCat,idProduct,num)

            /*   val product = db!!.collection("categories").document(idCat!!).collection("Product")
                   .document(idProduct!!)
               product.get()
                   .addOnSuccessListener { document ->
                       if (document != null) {
                           //val docRef=     db!!.collection("product").document(id)
                           val num = document.getDouble("numpay")

                           product.update("numpay", num!! + 1)
                               .addOnSuccessListener { querySnapshot ->
                                   Toast.makeText(this, "تم الشراء بنجاح", Toast.LENGTH_LONG).show()

                               }.addOnFailureListener { exception ->
                                   Toast.makeText(this, "error", Toast.LENGTH_LONG).show()


                               }
                       }


                   }*/
        }

        val docRef = db!!.collection("categories").document(idCat!!).collection("Product")
            .document(idProduct!!)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")

                    val name = document.getString("name")
                    val catg = document.getString("categroy")
                    val image = document.getString("image")
                    val price = document.getDouble("price")
                    val rate = document.getString("rate")


                    nameproduct.setText(name)
                    priceproduct.setText("${price} $ ")
                    rateproduct.setText(rate)
                    catproduct.setText(catg)
                    Glide.with(this).load(image).into(imgProduct)


                    //   document.get(0).get("categroy").toString()
                    Toast.makeText(this, "$name", Toast.LENGTH_LONG).show()


                } else {
                    Log.d("TAG", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }


        /* val name =  intent.getStringExtra("nameproduct1")
        val catg =  intent.getStringExtra("Catproduct1")
        val image =  intent.getStringExtra("imageproduct1")
        val price =  intent.getStringExtra("priceproduct1")
        val rate =  intent.getStringExtra("rateproduct1")*/

        /*  */
        /* db!!.collection("product").document(id)
             .get()
             .addOnSuccessListener {querySnapshot->
                 Log.e("TAG", "User deleted successfully")

                 intent.putExtra("Catproduct1",querySnapshot.documenst.get(0).get("categroy").toString())
                 for (document in querySnapshot) {
                     Log.e("TAG", "${document.id} => ${document.data}")
                 }
             }
             .addOnFailureListener { exception ->
                 Log.e("TAG", exception.message)
             }


 */
        Toast.makeText(this, "$idProduct", Toast.LENGTH_LONG).show()

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


        mapProduct.setOnClickListener {

            val intent = Intent(this, MapsProduct::class.java)

            val docRef = db!!.collection("categories").document(idCat).collection("Product")
                .document(idProduct)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                        val latProduct = document.getString("lat").toString()
                        val lngProduct = document.getString("lng").toString()
                        if (latProduct != "") {

                            intent.putExtra("latProduct", latProduct)
                            intent.putExtra("lngProduct", lngProduct)
                            startActivity(intent)
                        }


                    }
                }


        }


        back.setOnClickListener {
            val intent = Intent(
                this,
                product_user::class.java
            )
            startActivity(intent)
            finish()
        }


    }

    private fun getAllUsersByWhere(id: String) {
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
    }

    fun addOrder(idCat: String, idProduct: String,num:Int) {


        val docRef = db!!.collection("categories").document(idCat!!).collection("Product")
            .document(idProduct!!)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {


                    val catg = document.getString("categroy")
                    val price = document.getDouble("price")
                    val rate = document.getString("rate")


                    priceproduct.setText("${price} $ ")
                    rateproduct.setText(rate)
                    catproduct.setText(catg)


                    //   document.get(0).get("categroy").toString()


                    getIdAuth()
                    val order = hashMapOf(
                        "idCat" to idCat,
                        "idProduct" to idProduct,
                        "num" to num,
                        "image" to document.getString("image"),
                        "name" to document.getString("name")

                    )
                    //getIdAuth()
                    db!!.collection("users").whereEqualTo("email", auth.currentUser!!.email).get()
                        .addOnSuccessListener { querySnapshot ->
                            for (document in querySnapshot) {

                                db!!.collection("users").document(document.id).collection("order")
                                    .add(order)
                                Log.d("TAG", "${document.id} => ${document.data}")
                            }

                        }

                } else {
                    Log.d("TAG", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
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