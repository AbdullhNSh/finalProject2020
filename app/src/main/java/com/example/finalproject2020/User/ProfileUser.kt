package com.example.finalproject2020.User

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.finalproject2020.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_profile_user.*

class ProfileUser : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var db: FirebaseFirestore?=null
    var storage: FirebaseStorage? =null
    var reference: StorageReference?=null
    lateinit var prog: ProgressDialog
    var path:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_user)
        db = Firebase.firestore
        auth = Firebase.auth
        storage= Firebase.storage
        reference= storage!!.reference
        prog= ProgressDialog(this)
        prog.setMessage("جاري التحميل")
        prog.setCancelable(false)
        getProfileData()

        update.setOnClickListener{
////updateImage(path)
            val intent = Intent(this,
                UpdateProfileUser::class.java)
            startActivity(intent)
        }


        back1.setOnClickListener {
            val intent = Intent(this,
                MainActivityUser::class.java)
            startActivity(intent)
        }

    }


    fun getProfileData1() {
        db!!.collection("users").get()
            .addOnSuccessListener { querySnapshot ->
                name.setText(querySnapshot.documents.get(0).get("name").toString())
                phone.setText(querySnapshot.documents.get(0).get("phone").toString())
                address.setText(querySnapshot.documents.get(0).get("address").toString())
                email.setText(auth.currentUser!!.email)
                Glide.with(this).load(querySnapshot.documents.get(0).get("image")).into(imgProfile)
            }.addOnFailureListener { exception ->

            }
    }

    fun getProfileData() {
        db!!.collection("users").whereEqualTo("email",auth.currentUser!!.email).get()
            .addOnSuccessListener { querySnapshot ->
                name.setText(querySnapshot.documents.get(0).get("name").toString())
                phone.setText(querySnapshot.documents.get(0).get("phone").toString())
                address.setText(querySnapshot.documents.get(0).get("address").toString())
                email.setText(auth.currentUser!!.email)
                Glide.with(this).load(querySnapshot.documents.get(0).get("image")).into(imgProfile)
            }.addOnFailureListener { exception ->

            }
    }





}
