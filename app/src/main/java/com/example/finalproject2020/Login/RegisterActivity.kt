package com.example.finalproject2020.Login


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.finalproject2020.User.ProfileActivity
import com.example.finalproject2020.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null
    var db:FirebaseFirestore?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        mAuth = FirebaseAuth.getInstance()
        db = Firebase.firestore


        to_login.setOnClickListener {
            val intent = Intent(this,
                LoginActivity::class.java)
            startActivity(intent)
        }

        //   btn_register.setOnClickListener(View.OnClickListener {




        btn_register.setOnClickListener {
            val r_name:String = name.text.toString()
            val r_email: String = email.text.toString()
            val r_pass: String = pass.text.toString()
            val r_address: String = address.text.toString()
            val r_confirm: String = confirm_pass.text.toString()
            val r_authentication: String = authentication.selectedItem.toString()
            val r_mobile :String = phone.text.toString()


            if(((r_name == ""))||((r_email == ""))||((r_address == "")) ||((r_mobile == ""))||((r_pass == ""))||((r_confirm == ""))) {

                if ((r_name == "")) {
                    name.setError("Please enter username")
                }
                if ((r_email == "")) {
                    email.setError("Please enter email")
                }

                if ((r_address == "")) {
                    address.setError("Please enter address")
                }

                if ((r_pass == "")) {
                    pass.error = "Please enter password"
                }
                if ((r_confirm == "")) {
                    confirm_pass.error = "Please enter confirm Password"
                }
                if (r_pass != r_confirm) {
                    confirm_pass.error = "Please enter password Identical"
                    pass.error = "Please enter password Identical"
                }
              if ((r_mobile == "")) {
                    phone.error = "Please enter mobile number"
                }
            }else{    //
                if (r_name.isNotEmpty() &&r_mobile.isNotEmpty()&& r_pass.isNotEmpty()&&r_email.isNotEmpty()&&r_authentication.isNotEmpty()) {

                    prog.visibility= View.VISIBLE

                    createNewAcount(r_email,r_pass)
                }else{
                    Toast.makeText(this, "Fill Fields", Toast.LENGTH_SHORT).show()
                }

            }






        }





    }


    private fun createNewAcount(email: String,pass:String){
        mAuth?.createUserWithEmailAndPassword(email,pass)
            ?.addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(applicationContext,"تم التسجيل بنجاح", Toast.LENGTH_LONG).show()
                    prog.visibility= View.GONE
                    val user = mAuth!!.currentUser
                    mAuth!!.currentUser
                    Log.e("hzm","user${user!!.uid} + ${user.email}")
                    addUser( user.uid,name.text.toString(), user!!.email.toString(),phone.text.toString(),
                        authentication.selectedItem.toString(),address.text.toString()
                    )
                    val intent = Intent(this,
                        ProfileActivity::class.java)
                    startActivity(intent)

                }else{
                    Toast.makeText(applicationContext,"فشل في التحميل" , Toast.LENGTH_LONG).show()
                    prog.visibility= View.GONE

                }
            }
    }

    private  fun addUser(id : String,name:String ,email:String,phone:String,auth1 :String,address:String){

        var user =
            hashMapOf("id" to id,"name" to name,"email" to email,"phone" to phone,"auth1" to auth1,"address" to address )
            db!!.collection("users").add(user)
                .addOnSuccessListener {documentReference ->
                    Log.e("hzm",documentReference.id)


                }.addOnFailureListener{

                }

    }



}


