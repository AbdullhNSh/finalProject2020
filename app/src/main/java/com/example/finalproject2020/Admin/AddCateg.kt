package com.example.finalproject2020.Admin

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import kotlinx.android.synthetic.main.activity_add_categ.*
import java.util.*

class AddCateg : AppCompatActivity(), IPickResult {

    lateinit var auth: FirebaseAuth
    var db: FirebaseFirestore?=null
    var storage: FirebaseStorage? =null
    var reference: StorageReference?=null
    lateinit var prog: ProgressDialog
    var path:String?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_categ)



        db = Firebase.firestore
        auth = Firebase.auth
        storage= Firebase.storage
        reference= storage!!.reference

        prog= ProgressDialog(this)
        prog.setMessage("جاري التحميل")
        prog.setCancelable(false)
        val name = name1.text
        save.setOnClickListener{

            if (name.toString()==""||path==null) {
               Toast.makeText(this,"ادخل البيانات",Toast.LENGTH_LONG).show()
            }else{
                addCatToDB(name.toString(), img.toString())
                Toast.makeText(this,"تم" ,Toast.LENGTH_LONG).show()
                val intent = Intent(this,
                    MainActivityAdmin::class.java)
                startActivity(intent)
                finish()
            }
        }

        img.setOnClickListener{
            PickImageDialog.build(PickSetup()).show(this)
        }
        back.setOnClickListener{
val intent = Intent(this, CatActivity::class.java)
        startActivity(intent)
        }
        back11.setOnClickListener{
            val intent = Intent(this, CatActivity::class.java)
            startActivity(intent)
        }


    }

    override fun onPickResult(r: PickResult?) {
        //imageView = (ImageView)findViewById(R.id.imageView2);

        img.setImageBitmap(r!!.bitmap)
        uploadImage(r.uri)
    }
 fun uploadImage(uri: Uri){
   prog.show()
   reference!!.child("profile" + UUID.randomUUID().toString()).putFile(uri)
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
    private fun addCatToDB(name: String, image: String) {

        prog.show()


        val user = hashMapOf("name" to name, "image" to path)

        db!!.collection("categories")
            .add(user)

            .addOnSuccessListener { documentReference ->
               // prog.dismiss()


                Log.e("hzm", "User added Successfully with user id ${documentReference.id}")
            }
            .addOnFailureListener { exception ->

                prog.dismiss()


                Log.e("ham", exception.message!!)

            }
    }


private fun updateImage(){
   // val user = hashMapOf("name" to name,"image" to path)
    db!!.collection("categories").whereEqualTo("id",auth.currentUser!!.uid).get()
        .addOnSuccessListener { querySnapshot ->
            db!!.collection("categories").document(querySnapshot.documents.get(0).id).update("image",path)
        }.addOnFailureListener { exception ->

        }
}

}