package com.example.finalproject2020

import android.app.ProgressDialog
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
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
import kotlinx.android.synthetic.main.activity_catrgory_edit.*
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.activity_update_categori.*
import kotlinx.android.synthetic.main.activity_update_categori.cancel
import kotlinx.android.synthetic.main.activity_update_categori.save
import kotlinx.android.synthetic.main.activity_update_product.*
import java.util.*

class UpdateCategori : AppCompatActivity() , IPickResult {

    lateinit var auth: FirebaseAuth
    var db: FirebaseFirestore?=null
    var storage: FirebaseStorage? =null
    var reference: StorageReference?=null
    lateinit var prog: ProgressDialog
    var path:String?=null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_categori)

        db = Firebase.firestore
        auth = Firebase.auth
        storage= Firebase.storage
        reference= storage!!.reference
        prog= ProgressDialog(this)
        prog.setMessage("جاري التحميل")
        prog.setCancelable(false)

        getData()

        ed_imgCat.setOnClickListener {
            PickImageDialog.build(PickSetup()).show(this)

        }
        cancel.setOnClickListener { finish() }
        save.setOnClickListener {

            val id = intent.getStringExtra("id")


            val docRef = db!!.collection("categories").document(id!!)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("TAG", "DocumentSnapshot data: ${document.data}")


                        val name = ed_nameCat.text
                        var image: String = ""
                        if (path == null) {
                            image =  document.getString ("image").toString()
                        } else {
                                image=path.toString()
                        }



                        doUpdate(name.toString(),image)
                    }
                }
        }
    }

    fun doUpdate(name: String,path:String){
        val id = intent.getStringExtra("id")

        val cat = HashMap<String, Any>()
        cat["name"] = name
        cat["image"] = path

        val docRef=     db!!.collection("categories").document(id!!)
        docRef.update(cat)
            .addOnSuccessListener { querySnapshot ->
                Toast.makeText(this,"تم التعديل بنجاح",Toast.LENGTH_LONG).show()

            }.addOnFailureListener { exception ->
                Toast.makeText(this,"error",Toast.LENGTH_LONG).show()
            }
        /*  val docRef = db!!.collection("product").document(id)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")



                    val docRef=     db!!.collection("product").document(id)
                    docRef.update(user)
            .addOnSuccessListener { querySnapshot ->
               Toast.makeText(this,"تم التعديل بنجاح",Toast.LENGTH_LONG).show()

            }.addOnFailureListener { exception ->
                            Toast.makeText(this,"error",Toast.LENGTH_LONG).show()

*/

    }




    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    fun getData(){
        val id = intent.getStringExtra("id")


        val docRef = db!!.collection("categories").document(id!!)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")

                    val name = document.getString("name")
                    val image = document.getString("image")

                    ed_nameCat.setText(name)
                    Glide.with(this).load(image).into(ed_imgCat)



                } else {
                    Log.d("TAG", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
    }

    private fun updateProduct( name: String, price: String,rate : String,lat:String,lng:String,categroy:String,path:String ) {

        val user = HashMap<String, Any>()
        user["name"] = name
        user["price"] = price
        user["rate"] = rate
        user["lat"] = lat
        user["lng"] = lng
        user["categroy"]=categroy
        user["image"] = path
        val id = intent.getStringExtra("id")



        val docRef = db!!.collection("product").document(id!!)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")



                    val docRef=     db!!.collection("product").document(id!!)
                    docRef.update(user)
                        .addOnSuccessListener { querySnapshot ->
                            Toast.makeText(this,"تم التعديل بنجاح",Toast.LENGTH_LONG).show()

                        }.addOnFailureListener { exception ->
                            Toast.makeText(this,"error",Toast.LENGTH_LONG).show()


                        }
                }
            }}


    override fun onPickResult(r: PickResult?) {
        ed_imgCat.setImageBitmap(r!!.bitmap)
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
}