package com.example.finalproject2020.User

import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject2020.*
import com.example.finalproject2020.Login.LoginActivity
import com.example.finalproject2020.model.categories
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import kotlinx.android.synthetic.main.activity_main_user.*
import kotlinx.android.synthetic.main.activity_profile.address
import kotlinx.android.synthetic.main.activity_profile.email
import kotlinx.android.synthetic.main.activity_profile.imgProfile
import kotlinx.android.synthetic.main.activity_profile.name
import kotlinx.android.synthetic.main.activity_profile.phone
import kotlinx.android.synthetic.main.activity_profile_user.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.app_bar_home.toolbar
import kotlinx.android.synthetic.main.content_scrolling_user_cate.*
import kotlinx.android.synthetic.main.item_category1.view.*
import kotlinx.android.synthetic.main.nav_header_main.*


class MainActivityUser : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {


    lateinit var auth: FirebaseAuth
    var storage: FirebaseStorage? = null
    var reference: StorageReference? = null
    lateinit var prog: ProgressDialog
    var path: String? = null

    var db: FirebaseFirestore? = null
    var adapter: FirestoreRecyclerAdapter<categories, categoriesViewHolder>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_user)



        db = Firebase.firestore
        auth = Firebase.auth
        storage = Firebase.storage
        reference = storage!!.reference
        prog = ProgressDialog(this)
        prog.setMessage("جاري التحميل")
        prog.setCancelable(false)
        getProfileData()


        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        getAllUser()


        //                Glide.with(this@MainActivityUser).load(model.image).into(holder.image)

        val toggle = ActionBarDrawerToggle(
            this, drawer_layoutUSer, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer_layoutUSer.addDrawerListener(toggle)
        toggle.syncState()
        nav_viewUser.setNavigationItemSelectedListener(this)


    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu!!, v!!, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
        menu.setHeaderTitle("Select Action :")
        val btnId = v.getId()

        Toast.makeText(this@MainActivityUser, "id : $btnId", Toast.LENGTH_LONG).show()


    }

    private fun deleteItem(index: Int) {
        db!!.collection("ToDoList")
            .document().getId()

        Toast.makeText(
            this, db!!.collection("ToDoList")
                .document().getId(), Toast.LENGTH_SHORT
        ).show()

        //   .delete()
        //   .addOnSuccessListener { loadData() }
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {


        when (item.itemId) {
            R.id.update -> {
                val intent = Intent(this, UpdateCategori::class.java)


                startActivity(intent)


            }
            R.id.delete -> {
                deleteItem(item.order)
                Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show()

            }
            R.id.share -> Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
            R.id.exit -> {
                finish()
            }
        }


        return super.onContextItemSelected(item)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        /* val id = item?.itemId
         if(id == R.id.itemLogout){
             FirebaseAuth.getInstance().signOut()
             val intent = Intent(this,LoginActivity::class.java)
             startActivity(intent)
         }*/
        when (item.itemId) {
            R.id.itemLogout -> {
                val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.clear().apply()
                Toast.makeText(applicationContext, "Data Cleared Successfully", Toast.LENGTH_SHORT)
                    .show()
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(
                    this,
                    LoginActivity::class.java
                )
                startActivity(intent)
            }
              R.id.about -> {

              }
            /*  R.id.share-> {}
              R.id.serch->{}*/

        }

        return super.onOptionsItemSelected(item)
    }


    fun getAllUser() {
        val query = db!!.collection("categories")
        val options =
            FirestoreRecyclerOptions.Builder<categories>().setQuery(query, categories::class.java)
                .build()

        adapter = object : FirestoreRecyclerAdapter<categories, categoriesViewHolder>(options) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): categoriesViewHolder {
                var view = LayoutInflater.from(this@MainActivityUser)
                    .inflate(R.layout.item_category1, parent, false)
                return categoriesViewHolder(
                    view
                )
            }

            override fun onBindViewHolder(
                holder: categoriesViewHolder,
                position: Int,
                model: categories
            ) {
                holder.name.text = model.name



                Glide.with(this@MainActivityUser).load(model.image).into(holder.image)


                holder.itemView.setOnClickListener {
                    val idCat = snapshots.getSnapshot(position).id
                    Log.d("hzm", idCat)

                    val intent = Intent(this@MainActivityUser, ProdctUser1::class.java)
                    intent.putExtra("idCat", idCat)
                    startActivity(intent)

                /*   val docRef = db!!.collection("categories").document(docId)
                    docRef.get()
                        .addOnSuccessListener { document ->
                            if (document != null) {
                                Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                                val intent = Intent(
                                    this@MainActivityUser,
                                    ProdctUser1::class.java
                                )

                                val name = document.getString("name")
                                Toast.makeText(this@MainActivityUser, "$name", Toast.LENGTH_LONG)
                                    .show()
                                intent.putExtra("Catproduct", name)
                                startActivity(intent)

}
                            }*/

                }

                holder.itemView.setOnLongClickListener(View.OnLongClickListener {
                    Toast.makeText(this@MainActivityUser, "item123123", Toast.LENGTH_LONG).show()


                    true
                })


            }


        }

        category.layoutManager = GridLayoutManager(this, 2)
        category.adapter = adapter


    }

    class categoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.categoryName

        var image = view.categoryImage
    }


    private fun getAllUsers() {
        db!!.collection("users")
            .get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    Log.e("hzm", "${document.id} => ${document.data}")
                    Log.e("hzm", "${document.id} => ${document.getString("name")}")
                }

            }
            .addOnFailureListener { exception ->
                Log.e("hzm", exception.message.toString())
            }
    }
    /* fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.itemLogout -> {
                val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.clear().apply()
                Toast.makeText(applicationContext,"Data Cleared Successfully",Toast.LENGTH_SHORT).show()
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.about -> {}
            R.id.share-> {}
            R.id.serch->{}

        }

        return super.onOptionsItemSelected(item)
    }*/


    /* override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         menuInflater.inflate(R.menu.menu,menu)

         return super.onCreateOptionsMenu(menu)
     }*/


    override fun onBackPressed() {
        if (drawer_layoutUSer.isDrawerOpen(GravityCompat.START)) {
            drawer_layoutUSer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                val intent = Intent(
                    this,
                    MainActivityUser::class.java
                )
                startActivity(intent)
            }
            R.id.nav_Product -> {
                val intent = Intent(
                    this,
                    product_user::class.java
                )
                startActivity(intent)
            }
            R.id.nav_Categorie -> {
                val intent = Intent(this, CatUser::class.java)
                startActivity(intent)
            }
            R.id.nav_profile -> {
                val intent = Intent(
                    this,
                    ProfileUser::class.java
                )
                startActivity(intent)
            }

            R.id.nav_search -> {
                val intent = Intent(
                    this, orderUser::class.java
                )
                startActivity(intent)
            }
            R.id.nav_share -> {
                val sendIntent = Intent()
                sendIntent.action = Intent.ACTION_SEND
                sendIntent.putExtra(Intent.EXTRA_TEXT, "Hazem A. S. Al Rekhawi")
                sendIntent.type = "text/plain"
                startActivity(sendIntent)
            }
            R.id.nav_send -> {
                val emailIntent =
                    Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "abc@gmail.com", null))
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
                emailIntent.putExtra(Intent.EXTRA_TEXT, "Body")
                startActivity(Intent.createChooser(emailIntent, "Send email..."))
            }
            R.id.nav_logout -> {
                val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.clear().apply()
                Toast.makeText(applicationContext, "Data Cleared Successfully", Toast.LENGTH_SHORT)
                    .show()
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(
                    this,
                    LoginActivity::class.java
                )
                startActivity(intent)
            }

        }
        drawer_layoutUSer.closeDrawer(GravityCompat.START)
        return true

    }


    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
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

        db!!.collection("users").whereEqualTo("email", auth.currentUser!!.email).get()
            .addOnSuccessListener { querySnapshot ->
                //  nameUser.setText(querySnapshot.documents.get(0).get("name").toString())

                emailUser.setText(auth.currentUser!!.email)
                // Picasso.with(context).load(querySnapshot.documents.get(0).get("image").toString()).into(imgProfile);
                Glide.with(this).load(querySnapshot.documents.get(0).get("image")).into(imgUser)
              //  Glide.with(this).load(querySnapshot.documents.get(0).get("image")).into(imgProfile)


            }.addOnFailureListener { exception ->

            }
    }

}

