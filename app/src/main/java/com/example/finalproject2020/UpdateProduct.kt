package com.example.finalproject2020

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.finalproject2020.Admin.MapsUpdateProduct
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.vansuita.pickimage.bean.PickResult
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import com.vansuita.pickimage.listeners.IPickResult
import kotlinx.android.synthetic.main.activity_update_product.*
import java.util.*

class UpdateProduct : AppCompatActivity() , IPickResult {

    lateinit var auth: FirebaseAuth
    var db: FirebaseFirestore?=null
    var storage: FirebaseStorage? =null
    var reference: StorageReference?=null
    lateinit var prog: ProgressDialog
    var path:String?=null






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_product)


      /*  val lat =  document.getString("lat")
        val lng =  document.getString("lng")*/

        db = Firebase.firestore
        auth = Firebase.auth
        storage= Firebase.storage
        reference= storage!!.reference
        prog= ProgressDialog(this)
        prog.setMessage("جاري التحميل")
        prog.setCancelable(false)


       val idCat = intent.getStringExtra("idCat")
        val idProduct = intent.getStringExtra("idProduct")

        getProductData(idCat!!,idProduct!!)

        ed_mapProduct.setOnClickListener {
            val intent = Intent(this, MapsUpdateProduct::class.java)
            intent.putExtra("idProduct",idProduct)
            intent.putExtra("idCat",idCat)

            startActivity(intent)
        }

        ed_imgProduct.setOnClickListener {

            PickImageDialog.build(PickSetup()).show(this)
        }

        save.setOnClickListener {
            val docRef = db!!.collection("categories").document(idCat!!).collection("Product").document(idProduct!!)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("TAG", "DocumentSnapshot data: ${document.data}")


            val lat1 = intent.getStringExtra("lat1")
            val lng1  = intent.getStringExtra("lng1")

            val name =     ed_name.text
            val catg =  ed_productCat.text
         //   val path =  document.getString("image")

            val price =  ed_price.text
            val rate =  ed_rate.text
            val lat = intent.getStringExtra("latProduct1")
            val lng = intent.getStringExtra("lngProduct1")



                   var latUpdate :String = ""
                        var lngUpdate:String = ""
if (lat1==null){
     latUpdate = lat!!
     lngUpdate =lng!!
}else{
    latUpdate = lat1
    lngUpdate = lng1!!

}
                        var image:String =  ""
                        if (path==null){
                            image= document.getString("image").toString()
                        }else{
                            image = path.toString()
                        }



                          updateProduct(name.toString(),price.toString(),rate.toString(),latUpdate.toString(),lngUpdate.toString(),catg.toString(),image.toString(),idCat,idProduct)



                    } else {
                        Log.d("TAG", "No such document")
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("TAG", "get failed with ", exception)
                }


        }

    }

    //ed_mapProduct

    fun getProductData(idCat:String,idProduct:String) {
        val id = intent.getStringExtra("id")


     db!!.collection("categories").document(idCat).collection("Product").document(idProduct).get()

            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")


                    val name =     document.getString("name")
                    val catg =  document.getString("categroy")
                    val image =  document.getString("image")
                    val price =  document.getString("price")
                    val rate =  document.getString("rate")
                    val lat =  document.getString("lat")
                    val lng =  document.getString("lng")
                    val path = document.getString("image")

                    ed_name.setText(name)
                    ed_price.setText(price+"$")
                    ed_rate.setText(rate)
                    ed_productCat.setText(catg)
                    Glide.with(this).load(image).into(ed_imgProduct)




                } else {
                    Log.d("TAG", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }

    }

    override fun onPickResult(r: PickResult?) {
        ed_imgProduct.setImageBitmap(r!!.bitmap)
        uploadImage(r!!.uri)
    }




    fun uploadImage(uri: Uri){
        prog.show()
        reference!!.child("profile/" + UUID.randomUUID().toString()).putFile(uri!!)
            .addOnSuccessListener {taskSnapshot->
                prog.dismiss()
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri->
                    path = uri.toString()

                }.addOnFailureListener{

                }
                Toast.makeText(this,"Success", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{Exception->

            }



    }


    private fun updateProduct( name: String, price: String,rate : String,lat:String,lng:String,categroy:String,path:String,idCat:String,idProduct:String ) {

        val user = HashMap<String, Any>()
        user["name"] = name
        user["price"] = price
        user["rate"] = rate
        user["lat"] = lat
        user["lng"] = lng
        user["categroy"]=categroy
        user["image"] = path




        val docRef =      db!!.collection("categories").document(idCat).collection("Product").document(idProduct)

        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")



                    val docRef=      db!!.collection("categories").document(idCat).collection("Product").document(idProduct)
                    docRef.update(user)
                        .addOnSuccessListener { querySnapshot ->
                            Toast.makeText(this,"تم التعديل بنجاح",Toast.LENGTH_LONG).show()

                        }.addOnFailureListener { exception ->
                            Toast.makeText(this,"error",Toast.LENGTH_LONG).show()


                        }
                }
            }}
}