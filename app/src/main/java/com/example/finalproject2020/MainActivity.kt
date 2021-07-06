package com.example.finalproject2020

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var db: FirebaseFirestore
    val TAG = "hzm"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = Firebase.firestore

        btnAddUser.setOnClickListener {
            addUserToDB(
                txtUsername.text.toString(),
                txtEmail.text.toString(),
                txtLevel.text.toString().toInt()
            )
        }

        btnGetAllUsers.setOnClickListener {
            getAllUsers()
        }

        btnGetAllUsersLimitOrder.setOnClickListener {
            getAllUsersByLimit()
        }

        btnGetAllUsersWhere.setOnClickListener {
            getAllUsersByWhere()
        }

        btnDeleteUser.setOnClickListener {
            deleteUserById("QQX7nlICRAHAsftu3B4u")
        }

        btnDeleteUserField.setOnClickListener {
            deleteUserEmailField("RU71OkMt6rr4EmyPltuo")
        }
    }

    private fun addUserToDB(name: String, email: String, level: Int) {

        val user = hashMapOf("name" to name, "email" to email, "level" to level)

        db.collection("abed")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.e(TAG, "User added Successfully with user id ${documentReference.id}")
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, exception.message.toString())
            }
    }

    private fun getAllUsers() {
        db.collection("abed")
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    Log.e(TAG, "${document.id} => ${document.data}")
                    Log.e(TAG, "${document.id} => ${document.getString("name")}")
                }

            }
            .addOnFailureListener { exception ->
                Log.e(TAG, exception.message.toString())
            }
    }

    private fun getAllUsersByLimit() {
        //db.collection("users").limit(2).orderBy("name")
        db.collection("abed").limit(2).orderBy("name", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    Log.e(TAG, "${document.id} => ${document.data}")
                    //Log.e(TAG,"${document.id} => ${document.getString("name")}")
                }

            }
            .addOnFailureListener { exception ->
                Log.e(TAG, exception.message.toString())
            }
    }

    private fun getAllUsersByWhere() {
        //db.collection("users").whereGreaterThan("level",3)
        db.collection("abed").whereGreaterThanOrEqualTo("level", 3)
            .orderBy("level", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    Log.e(TAG, "${document.id} => ${document.data}")
                }

            }
            .addOnFailureListener { exception ->
                Log.e(TAG, exception.message.toString())
            }
    }

    private fun deleteUserById(id: String) {
        db.collection("abed").document(id)
            .delete()
            .addOnSuccessListener {
                Log.e(TAG, "User deleted successfully")
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, exception.message.toString())
            }
    }

    private fun deleteUserEmailField(id: String) {
        db.collection("abed").document(id)
            .update("email", FieldValue.delete())
            .addOnSuccessListener {
                Log.e(TAG, "User Email deleted successfully")
            }
            .addOnFailureListener { exception ->
                Log.e(TAG, exception.message.toString())
            }
    }

    private fun updateUserbyId(oldId: String, name: String, email: String, level: Int) {
        val user = HashMap<String, Any>()
        user["name"] = name
        user["email"] = email
        user["level"] = level

        db.collection("abed").document(oldId)
            .update(user)
            .addOnSuccessListener {
                Log.e(TAG, "User Updated Successfully")
            }.addOnFailureListener { exception ->
                Log.e(TAG, exception.message.toString())
            }
    }

}
