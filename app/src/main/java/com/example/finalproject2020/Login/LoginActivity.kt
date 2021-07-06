package com.example.finalproject2020.Login


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.finalproject2020.Admin.MainActivityAdmin
import com.example.finalproject2020.User.MainActivityUser
import com.example.finalproject2020.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    var mAuth: FirebaseAuth? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)




        mAuth= FirebaseAuth.getInstance()


        to_register.setOnClickListener {
            val intent = Intent(this,
                RegisterActivity::class.java)
            startActivity(intent)
        }

        btn_login.setOnClickListener {

            val u_email: String = email.text.toString()
            val u_pass: String = password.text.toString()

            var login = ""
            if (login_Admin.isChecked)
                login = "Admin"
            else
                login = "User"

            if ((u_email == "") || (u_pass == "")) {

                if ((u_email == "")) {
                    email.error = "Please enter username"
                    //return@OnClickListener
                }
                if (u_pass == "") {
                    password.error = "Please enter password"

                }
            } else {
                prog.visibility = View.VISIBLE
                // Toast.makeText(this,"go",Toast.LENGTH_LONG).show()
                mAuth?.signInWithEmailAndPassword(u_email, u_pass)?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        prog.visibility = View.GONE
                        var remember1=false
                        if(remember.isChecked) {
                            remember1 = true
                        }

                        if (remember1){
                            Toast.makeText(this,remember1.toString(), Toast.LENGTH_LONG).show()
                            val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                            val editor = sharedPref.edit()
                            editor.putString("email",u_email)
                            editor.putString("pass",u_pass)
                            editor.putString("login",login)
                            // editor.putBoolean("isActive",true)
                            //editor.apply()
                            val b =  editor.commit()
                        }
                        if (login != "Admin") {
                            if (login == "User") {
                                val intent = Intent(this@LoginActivity, MainActivityUser::class.java)
                                startActivity(intent)
                            }

                        } else {
                            val intent = Intent(this@LoginActivity, MainActivityAdmin::class.java)
                            startActivity(intent)
                        }

                    } else {
                        prog.visibility = View.GONE

                        Toast.makeText(applicationContext, it.exception.toString(), Toast.LENGTH_LONG).show()

                    }


                }
            }

        }
    }}








/*  if (u_name == name) {
      if (u_pass == pass) {
          val intent = Intent(this@LoginActivity, MainActivity::class.java)
          startActivity(intent)
          editor.putBoolean("auth", true)
          editor.apply()
          finish()
          Toast.makeText(this@LoginActivity, "Successfully Login", Toast.LENGTH_LONG).show()
      } else {
          Toast.makeText(this@LoginActivity, "Wrong password", Toast.LENGTH_LONG).show()
      }
  } else {
      Log.d("TAG", "SUCCESS5")
      Toast.makeText(this@LoginActivity, "Wrong Username : " + u_name + " , " + name + " , " + (name == u_name), Toast.LENGTH_SHORT).show()
  }
var prefs = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
var editor = prefs.edit()
val  p_email:String = prefs.getString("email", " ").toString()
val   p_pass:String = prefs.getString("pass", " ").toString()
val  p_login:String = prefs.getString("login", " ").toString()



if(u_remember == "remember"){

              val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
              val editor = sharedPref.edit()
              editor.putString("email",u_name)
              editor.putString("pass",u_pass)
              editor.putString("login",login)
              editor.apply()
              val b =  editor.commit()
              if(b)
                  Toast.makeText(applicationContext,"Added Successfully", Toast.LENGTH_SHORT).show()

          }

*/

