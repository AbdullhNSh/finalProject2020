package com.example.finalproject2020.Admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.finalproject2020.R
import com.example.finalproject2020.UpdateCategori
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_catrgory_edit.*

class CatrgoryEdit : AppCompatActivity() {

    var db: FirebaseFirestore? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_catrgory_edit)
        val id = intent.getStringExtra("id")

        db = Firebase.firestore
        getData()

        backCarEdit.setOnClickListener {
            val intent = Intent(this,
                MainActivityAdmin::class.java)
            startActivity(intent)
        }
        delete.setOnClickListener {
            if (id != null) {
                deleteCatById(id)
            }

        }
        update.setOnClickListener {
            val intent = Intent(this,
                UpdateCategori::class.java)
            intent.putExtra("id",id)
            startActivity(intent)
        }


    }

    private fun deleteCatById(id: String) {
        db!!.collection("categories").document(id)
            .delete()
            .addOnSuccessListener {
                Log.e("TAG", "category deleted successfully")
                Toast.makeText(this,"category deleted successfully", Toast.LENGTH_LONG).show()
            }
            .addOnFailureListener { exception ->
                Log.e("TAG", exception.message.toString())
            }
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

nameCat.setText(name)
                    Glide.with(this).load(image).into(CatImage)



                } else {
                    Log.d("TAG", "No such document")
                }
            }
            .addOnFailureListener { exception ->
                Log.d("TAG", "get failed with ", exception)
            }
    }

    }


