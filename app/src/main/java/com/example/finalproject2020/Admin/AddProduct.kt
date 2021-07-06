package com.example.finalproject2020.Admin

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.finalproject2020.MapsActivity
import com.example.finalproject2020.R
import com.google.android.gms.maps.model.LatLng
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
import kotlinx.android.synthetic.main.activity_add_product.*
import java.util.*

class AddProduct : AppCompatActivity(), IPickResult {

    val TAG = "hzm"

    lateinit var auth: FirebaseAuth
    var db: FirebaseFirestore?=null
    var storage: FirebaseStorage? =null
    var reference: StorageReference?=null
    lateinit var prog: ProgressDialog
    var path:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)



        db = Firebase.firestore
        auth = Firebase.auth
        storage= Firebase.storage
        reference= storage!!.reference
        prog= ProgressDialog(this)
        prog.setMessage("جاري التحميل")
        prog.setCancelable(false)



        val idCat = intent.getStringExtra("idCat")
        Log.e("hzmdocId",idCat.toString())




    back.setOnClickListener {
        val intent = Intent(this, ProductActivity::class.java)
    startActivity(intent)
    }
        back1.setOnClickListener {
            val intent = Intent(this,
                ProductActivity::class.java)
            startActivity(intent)
        }

                    save.setOnClickListener{

                        val nameProduct=nameProduct.text
                        val priceProduct=priceProduct.text.toString().toDouble()
                        val rateProduct=rateProduct.text
                        val categroyProduct=categroyProduct.text


                        val lat1 = intent.getStringExtra("lat1")
                           val lng1  = intent.getStringExtra("lng1")
                        Toast.makeText(this, "$lat1", Toast.LENGTH_SHORT).show()

                        val location = LatLng(lat1!!.toDouble(), lng1!!.toDouble())

                        if(((nameProduct.toString() == ""))||((priceProduct.toString() == ""))||((categroyProduct.toString() == ""))
                            ||((rateProduct.toString() == ""))||((location == null))||((path == null))) {
                            Toast.makeText(this,"ارغ",Toast.LENGTH_LONG).show()

                        }else{
val numpay:Int = 0

                                addUserToDB(nameProduct.toString(), imgProduct.toString(),priceProduct,rateProduct.toString(),lat1,lng1,categroyProduct.toString(),idCat.toString(),numpay)


                        }




                    }

        map.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("idCat",idCat)
            startActivity(intent)
        }



        imgProduct.setOnClickListener{
            PickImageDialog.build(PickSetup()).show(this)
        }
    }
override fun onPickResult(r: PickResult?) {
    //imageView = (ImageView)findViewById(R.id.imageView2);

    imgProduct.setImageBitmap(r!!.bitmap)
    uploadImage(r.uri)
}
fun uploadImage(uri: Uri){
    prog.show()
    reference!!.child("product" + UUID.randomUUID().toString()).putFile(uri)
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
private fun addUserToDB(name: String, image: String,price:Double,rate:String,lat:String,lng:String ,categroy:String,docId:String,numpay:Int) {

    prog.show()


    val product = hashMapOf("name" to name, "image" to path, "price" to price ,"rate" to rate,"lat" to lat,"lng" to lng,"categroy" to categroy,"numpay" to numpay)

    Log.e("docId", docId.toString())
    db!!.collection("categories").document(docId).collection("Product").add(product)
        .addOnSuccessListener { documentReference ->
            prog.dismiss()

            val intent = Intent(this,MainActivityAdmin::class.java)
            startActivity(intent)
            Log.e("hzm", "User added Successfully with user docId ${documentReference.id}  +++  ${docId}")
        }
        .addOnFailureListener { exception ->

            prog.dismiss()


            Log.e("ham", exception.message.toString())

        }
    db!!.collection("product")
        .add(product)

        .addOnSuccessListener { documentReference ->
            prog.dismiss()


            Log.e("hzm", "User added Successfully with user id ${documentReference.id}")
        }
        .addOnFailureListener { exception ->

            prog.dismiss()


            Log.e("ham", exception.message.toString())

        }
}

/*
private fun updateImage(){
    // val user = hashMapOf("name" to name,"image" to path)
    db!!.collection("product").whereEqualTo("id",auth.currentUser!!.uid).get()
        .addOnSuccessListener { querySnapshot ->
            db!!.collection("product").document(querySnapshot.documents.get(0).id).update("image",path)
        }.addOnFailureListener { exception ->

        }
}
*/


}