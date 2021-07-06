package com.example.finalproject2020.Admin

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.finalproject2020.R
import com.example.finalproject2020.UpdateProduct

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_update_product.*
import kotlin.math.ln

class MapsUpdateProduct : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    lateinit var auth: FirebaseAuth
    var db: FirebaseFirestore?=null
    var storage: FirebaseStorage? =null
    var reference: StorageReference?=null
    lateinit var prog: ProgressDialog
    var path:String?=null
    var lat = ""
    var lng = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps_update_product)

        db = Firebase.firestore
        auth = Firebase.auth
        storage= Firebase.storage
        reference= storage!!.reference
        prog= ProgressDialog(this)
        prog.setMessage("جاري التحميل")
        prog.setCancelable(false)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isCompassEnabled = true
//mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this,R.raw.style))
// Add a marker in Sydney and move the camera
        val idCat = intent.getStringExtra("idCat")
        val idProduct = intent.getStringExtra("idProduct")

        Log.e("idCat", idCat!!)


        val docRef = db!!.collection("categories").document(idCat!!).collection("Product")
            .document(idProduct!!)
        docRef.get()
            .addOnSuccessListener { document ->
                if (document != null) {
                    Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                    lat = document.getString("lat").toString()
                    lng = document.getString("lng").toString()


                }



                if (lat != "") {
                    val sydney = LatLng(lat!!.toDouble(), lng!!.toDouble())
                    Log.e("lat", sydney.toString())
                    mMap.addMarker(MarkerOptions().position(sydney).title("location"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16f))
                    mMap.setOnMapClickListener { latLng ->
                        mMap.clear()
                        mMap.addMarker(MarkerOptions().position(latLng).title("Marker"))
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                        Toast.makeText(
                            this,
                            latLng.latitude.toString() + "     " + latLng.longitude.toString(),
                            Toast.LENGTH_LONG
                        ).show()


                        val intent = Intent(this, UpdateProduct::class.java)
                        Log.e("lat", latLng.toString())
                        intent.putExtra("idCat", idCat)
                        intent.putExtra("idProduct", idProduct)

                        intent.putExtra("lat1", latLng.latitude.toString())
                        intent.putExtra("lng1", latLng.longitude.toString())


                        startActivity(intent)

                    }
                } else {
                    val sydney = LatLng(31.455749, 34.402908)

                    mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney, 16f))
                    mMap.setOnMapClickListener { latLng ->
                        mMap.clear()
                        mMap.addMarker(MarkerOptions().position(latLng).title("Marker"))
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                        Toast.makeText(this, latLng.latitude.toString() + "   " +
                                "  " + latLng.longitude.toString(),
                            Toast.LENGTH_LONG
                        ).show()


                        val intent = Intent(
                            this,
                            UpdateProduct::class.java
                        )
                        intent.putExtra("lat1", latLng.latitude.toString())
                        intent.putExtra("lng1", latLng.longitude.toString())

                        intent.putExtra("idCat", idCat)
                        intent.putExtra("idProduct", idProduct)

                        startActivity(intent)
                    }
                }

            }
    }}