package com.example.finalproject2020

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import java.text.SimpleDateFormat
import java.util.*

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class confirmfinal : AppCompatActivity() {
    var uname: EditText? = null
    var uphonenumber: EditText? = null
    var uaddress: EditText? = null
    var ucity: EditText? = null
    var confirmOrderBtn: Button? = null
    var totalAmount = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmfinal)
      /*  totalAmount = intent.getStringExtra("Total Price")
        Toast.makeText(this, "Total Price =$totalAmount", Toast.LENGTH_LONG).show()
        confirmOrderBtn =
            findViewById<View>(R.id.confirm_final_order_btn) as Button
        uname = findViewById<View>(R.id.shipment_name) as EditText
        uphonenumber = findViewById<View>(R.id.shipment_phonenumber) as EditText
        uaddress = findViewById<View>(R.id.shipment_address) as EditText
        ucity = findViewById<View>(R.id.shipment_city) as EditText
        confirmOrderBtn!!.setOnClickListener { check() }
    }

    fun check() {
        if (TextUtils.isEmpty(uname!!.text.toString())) {
            uname!!.error = "please Provide your name"
        } else if (TextUtils.isEmpty(uphonenumber!!.text.toString())) {
            uphonenumber!!.error = "Please Provide your phone number"
        } else if (TextUtils.isEmpty(uaddress!!.text.toString())) {
            uaddress!!.error = "Please Provide your Address"
        }
        if (TextUtils.isEmpty(ucity!!.text.toString())) {
            ucity!!.error = "Please Provide City"
        } else {
            ConfirmOrder()
        }
    }

    fun ConfirmOrder() {
        val saveCurrentDate: String
        val saveCurrentTime: String
        val calendar = Calendar.getInstance()
        val currentDate = SimpleDateFormat("MMM dd, yyyy")
        saveCurrentDate = currentDate.format(calendar.time)
        val currentTime = SimpleDateFormat("HH:mm:ss a")
        saveCurrentTime = currentTime.format(calendar.time)
       val ordersRef =
            FirebaseDatabase.getInstance().reference.child("Orders")
                .child(Prevalant.currentOnlineUser.getPhone())
        val map =
            HashMap<String, Any>()
        map["totalAmount"] = totalAmount
        map["name"] = uname!!.text.toString()
        map["phone"] = uphonenumber!!.text.toString()
        map["address"] = uaddress!!.text.toString()
        map["city"] = ucity!!.text.toString()
        map["date"] = saveCurrentDate
        map["time"] = saveCurrentTime
        map["state"] = "Not Shipped"
        ordersRef.updateChildren(map)
            .addOnCompleteListener(object : OnCompleteListener<Void?> {
                override fun onComplete(task: Task<Void>) {
                    if (task.isSuccessful) {
                        FirebaseDatabase.getInstance().reference.child("Cart List")
                            .child("User View").child(Prevalant.currentOnlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(object : OnCompleteListener<Void?> {
                                override fun onComplete(task: Task<Void>) {
                                    if (task.isSuccessful) {
                                        Toast.makeText(
                                            this@confirmfinal,
                                            "Your order has been placed successfully",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        val intent =
                                            Intent(this@confirmfinal, HomeActivity::class.java)
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                        startActivity(intent)
                                        finish()
                                    }
                                }
                            })
                    }
                }
            })*/
    }
}