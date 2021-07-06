package com.example.finalproject2020

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main2.*
import java.io.ByteArrayOutputStream

class MainActivity2 : AppCompatActivity() {
    private var progressDialog: ProgressDialog?=null
    private var fileURI: Uri? = null
    private val PICK_IMAGE_REQUEST = 111

    var imageURI: Uri? = null
    val TAG = "hzm"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)



 val storage = Firebase.storage
            val storageRef = storage.reference
            val imageRef = storageRef.child("images")

            btnChooseImage.setOnClickListener {
                val intent = Intent()
                intent.action = Intent.ACTION_PICK
                intent.type = "image/*"
                startActivityForResult(intent, PICK_IMAGE_REQUEST)
            }

            btnUploadImage.setOnClickListener {
                showDialog()
                // Get the data from an ImageView as bytes
                val bitmap = (imageViewChoose.drawable as BitmapDrawable).bitmap
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
                val data = baos.toByteArray()

                val childRef = imageRef.child(System.currentTimeMillis().toString() + "_hzmimages.png")
                var uploadTask = childRef.putBytes(data)
                uploadTask.addOnFailureListener { exception ->
                    Log.e(TAG, exception.message.toString())
                    hideDialog()
                    // Handle unsuccessful uploads
                }.addOnSuccessListener {
                    // taskSnapshot.metadata contains file metadata such as size, content-type, etc.
                    // ...
                    Log.e(TAG, "Image Uploaded Successfully")
                    Toast.makeText(this, "Image Uploaded Successfully", Toast.LENGTH_SHORT).show()
                    childRef.downloadUrl.addOnSuccessListener { uri ->
                        Log.e(TAG, uri.toString())
                        fileURI = uri
                    }
                    hideDialog()
                }
            }

            btnReadImage.setOnClickListener {
                Picasso.get().load(fileURI).into(imageViewRead)
            }
        }

        private fun showDialog() {
            progressDialog = ProgressDialog(MainActivity@this)
            progressDialog!!.setMessage("Uploading image ...")
            progressDialog!!.setCancelable(false)
            progressDialog!!.show()
        }

        private fun hideDialog(){
            if(progressDialog!!.isShowing)
                progressDialog!!.dismiss()
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
                imageURI = data!!.data
                Log.e(TAG, imageURI.toString())
                imageViewChoose.setImageURI(imageURI)
            }
        }
    }
