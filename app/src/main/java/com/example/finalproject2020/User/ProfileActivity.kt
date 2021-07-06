package com.example.finalproject2020.User

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.finalproject2020.Admin.MainActivityAdmin
import com.example.finalproject2020.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_profile.address
import kotlinx.android.synthetic.main.activity_profile.back1
import kotlinx.android.synthetic.main.activity_profile.email
import kotlinx.android.synthetic.main.activity_profile.imgProfile
import kotlinx.android.synthetic.main.activity_profile.name
import kotlinx.android.synthetic.main.activity_profile.phone
import kotlinx.android.synthetic.main.activity_profile.update

class ProfileActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
var db:FirebaseFirestore?=null
    var storage:FirebaseStorage? =null
    var reference:StorageReference?=null
    lateinit var prog:ProgressDialog
    var path:String?=null

override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_profile)
    db = Firebase.firestore
   auth = Firebase.auth
    storage=Firebase.storage
    reference= storage!!.reference
    prog= ProgressDialog(this)
    prog.setMessage("جاري التحميل")
    prog.setCancelable(false)
    getProfileData1()

    update.setOnClickListener{
////updateImage(path)
        val intent = Intent(this,
            UpdateProfile::class.java)
        startActivity(intent)
    }

   /* imgProfile.setOnClickListener{
        PickImageDialog.build(PickSetup()).show(this)
    }*/

    back1.setOnClickListener {
        val intent = Intent(this,
            MainActivityAdmin::class.java)
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
               // Picasso.with(context).load(querySnapshot.documents.get(0).get("image").toString()).into(imgProfile);

                Glide.with(this).load(querySnapshot.documents.get(0).get("image")).into(imgProfile)

            }.addOnFailureListener { exception ->

            }
    }

   /* override fun onPickResult(r: PickResult?) {
        imgProfile.setImageBitmap(r!!.bitmap)
        uploadImage(r!!.uri)
            }

    fun uploadImage(uri:Uri){
        prog.show()
        reference!!.child("profile/" + UUID.randomUUID().toString()).putFile(uri!!)
            .addOnSuccessListener {taskSnapshot->
                prog.dismiss()
                taskSnapshot.storage.downloadUrl.addOnSuccessListener { uri->
                    path = uri.toString()

                }.addOnFailureListener{

                }
                Toast.makeText(this,"Success",Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener{Exception->

            }



    }

    private fun updateImage(path: String?) {
        db!!.collection("users").whereEqualTo("email", auth.currentUser!!.email).get()
            .addOnSuccessListener { querySnapshot ->
                db!!.collection("users").document(querySnapshot.documents.get(0).id)
                    .update("image", path)
            }.addOnFailureListener { exception ->

            }
    }
*/


}