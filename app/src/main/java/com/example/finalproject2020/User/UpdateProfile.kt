package com.example.finalproject2020.User

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
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
import com.vansuita.pickimage.bean.PickResult
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import com.vansuita.pickimage.listeners.IPickResult
import kotlinx.android.synthetic.main.activity_update_profile.*
import kotlinx.android.synthetic.main.activity_update_profile.addressProfile
import kotlinx.android.synthetic.main.activity_update_profile.emailProfile
import kotlinx.android.synthetic.main.activity_update_profile.imgProfile1
import kotlinx.android.synthetic.main.activity_update_profile.nameProfile
import kotlinx.android.synthetic.main.activity_update_profile.phoneProfile
import kotlinx.android.synthetic.main.activity_update_profile.updateProfile
import java.util.*

class UpdateProfile : AppCompatActivity() , IPickResult {

    lateinit var auth: FirebaseAuth
    var db: FirebaseFirestore?=null
    var storage: FirebaseStorage? =null
    var reference: StorageReference?=null
    lateinit var prog: ProgressDialog
    var path:String?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_profile)



        db = Firebase.firestore
        auth = Firebase.auth
        storage= Firebase.storage
        reference= storage!!.reference
        prog= ProgressDialog(this)
        prog.setMessage("جاري التحميل")
        prog.setCancelable(false)
        getProfileData()

imgProfile1.setOnClickListener {

    PickImageDialog.build(PickSetup()).show(this)
}

        updateProfile.setOnClickListener {

            val name= nameProfile.text
            val phone = phoneProfile.text
            val email = emailProfile.text
            val address = addressProfile.text
            //Toast.makeText(this,"$path",Toast.LENGTH_LONG).show()

            updateProfile(name.toString(),email.toString(),phone.toString(),address.toString(),path.toString())
            val intent= Intent(this,
                ProfileActivity::class.java)
           Toast.makeText(this,"تم التعديل بنجلح",Toast.LENGTH_LONG).show()
            Toast.makeText(this,"$path",Toast.LENGTH_LONG).show()

            startActivity(intent)
        }

        cancelProfile1.setOnClickListener {
            val intent= Intent(this,
                ProfileActivity::class.java)
            startActivity(intent)
        }




    }

    fun getProfileData() {
        db!!.collection("users").whereEqualTo("email",auth.currentUser!!.email).get()
            .addOnSuccessListener { querySnapshot ->
                nameProfile.setText(querySnapshot.documents.get(0).get("name").toString())
                phoneProfile.setText(querySnapshot.documents.get(0).get("phone").toString())
                addressProfile.setText(querySnapshot.documents.get(0).get("address").toString())
                emailProfile.setText(auth.currentUser!!.email)
                // Picasso.with(context).load(querySnapshot.documents.get(0).get("image").toString()).into(imgProfile);

                Glide.with(this).load(querySnapshot.documents.get(0).get("image")).into(imgProfile1)

            }.addOnFailureListener { exception ->

            }
    }

    override fun onPickResult(r: PickResult?) {
        imgProfile1.setImageBitmap(r!!.bitmap)
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

    private fun updateProfile( name: String, email: String,phone : String,address:String ,path: String) {

        val user = HashMap<String, Any>()
        user["name"] = name
        user["email"] = email
        user["phone"] = phone
        user["address"] = address
        user["image"] = path


        db!!.collection("users").whereEqualTo("email", auth.currentUser!!.email).get()
            .addOnSuccessListener { querySnapshot ->
                db!!.collection("users").document(querySnapshot.documents.get(0).id)
                    .update(user)

            }.addOnFailureListener { exception ->

            }
    }
/*
    private fun updateUserbyId(oldId: String, name: String, email: String,phone : String,address:String ,path: String?) {
        val user = HashMap<String, Any>()
        user["name"] = name
        user["email"] = email
        user["phone"] = phone
        user["address"] = address

        user["path"] = path
        db!!.collection("users").whereEqualTo("email", auth.currentUser!!.email).get()


        db!!.collection("users").document(querySnapshot.documents.get(0).id)
            .update(user)
            .addOnSuccessListener {
                Log.e(TAG, "User Updated Successfully")
            }.addOnFailureListener { exception ->
                Log.e(TAG, exception.message)
            }
    }*/
}