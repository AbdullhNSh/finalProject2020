package com.example.finalproject2020.Admin

import android.annotation.SuppressLint
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
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject2020.Login.LoginActivity
import com.example.finalproject2020.R
import com.example.finalproject2020.User.ProfileActivity
import com.example.finalproject2020.ViewProductmorebought
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
import kotlinx.android.synthetic.main.activity_main_admin.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.item_category1.view.*
import kotlinx.android.synthetic.main.nav_header_main.*


class MainActivityAdmin : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {
    lateinit var auth: FirebaseAuth
    var storage: FirebaseStorage? = null
    var reference: StorageReference? = null
    lateinit var prog: ProgressDialog
    var path: String? = null
    var db: FirebaseFirestore? = null
    var adapter: FirestoreRecyclerAdapter<categories, categoriesViewHolder>? = null

    //var editor: SharedPreferences.Editor? = null
    @SuppressLint("VisibleForTests")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_admin)

        db = Firebase.firestore
        auth = Firebase.auth
        storage = Firebase.storage
        reference = storage!!.reference
        prog = ProgressDialog(this)
        prog.setMessage("جاري التحميل")
        prog.setCancelable(false)
        getProfileData()


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        //    val navView = findViewById<BottomNavigationView>(R.id.bot_nav_view)
        // navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        getAllUser()


        // val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()

            val intent = Intent(this, AddCateg::class.java)
            startActivity(intent)
        }
        //val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        // val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val toggle = ActionBarDrawerToggle(
            this,
            drawer_layout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()
        nav_view.setNavigationItemSelectedListener(this)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)


        return super.onCreateOptionsMenu(menu)
    }


    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu!!, v!!, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
        menu.setHeaderTitle("Select Action :")
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.title == "DELETE") deleteItem(item.order)
        return super.onContextItemSelected(item)
    }

    private fun deleteItem(index: Int) {
        db!!.collection("ToDoList")
        //.document(toDoList[index].getId())
        //   .delete()
        // .addOnSuccessListener {
        //  loadData()
    }


    // val docId = snapshots.getSnapshot(position).id
    //  Log.d("hzm", docId)


    /*    val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
            when (item.title.toString()) {
                "Delete" -> {
                    val docRef = db!!.collection("categories").document(id)
                    docRef.get()
                        .addOnSuccessListener { document ->
                            if (document != null) {
                                Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                                docRef.moveToPosition(info.position)
                    val id = cursor.getInt(cursor.getColumnIndex("_id"))
                    op!!.deleteProduct(id)
                    adptr = AdapterCursor(baseContext, op!!.ProductCursor(order))
                    val lv = findViewById<ListView>(R.id.lv)
                    lv!!.adapter = adptr
                }
                "Update" -> {
                    val c = op!!.ProductCursor(order)
                    c.moveToPosition(info.position)
                    val ProductID = c.getInt(c.getColumnIndex("_id"))
                    val intent = Intent(baseContext, Modify::class.java)
                    intent.putExtra("id", ProductID.toString() + "")


         /*   val docRef = db!!.collection("categories").document(id)
            docRef.get()
                .addOnSuccessListener { document ->
                    if (document != null) {
                        Log.d("TAG", "DocumentSnapshot data: ${document.data}")

                        // =  document.getString("name")
                        /* val docId = snapshots.getSnapshot(position).id

                        Log.d("hzm", docId)*/


                    }
                }
                */

    /*}}


                    when (item.itemId) {
                        R.id.update -> {
                            val intent = Intent(this, UpdateCategori::class.java)


                            startActivity(intent)


                        }
                        R.id.delete -> {

                            Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show()

                        }
                        R.id.share -> Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
                        R.id.exit -> {
                            finish()
                        }
                    }
    */*/


    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.itemLogout -> {
                val sharedPref = getSharedPreferences("MyPref", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.clear().apply()
                Toast.makeText(applicationContext, "Data Cleared Successfully", Toast.LENGTH_SHORT)
                    .show()
                FirebaseAuth.getInstance().signOut()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.about -> {
            }
            R.id.share -> {
            }
            R.id.serch -> {
            }

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                val intent = Intent(
                    this,
                    MainActivityAdmin::class.java
                )
                startActivity(intent)
            }
            R.id.nav_Product -> {
                val intent = Intent(
                    this,
                    ViewProductmorebought::class.java
                )
                startActivity(intent)
            }
            R.id.nav_Categorie -> {
                val intent = Intent(
                    this,
                    CatActivity::class.java
                )
                startActivity(intent)
            }
            R.id.nav_profile -> {
                val intent = Intent(
                    this,
                    ProfileActivity::class.java
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
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
        //  val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
    }

    fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(
            R.id.mainContainer,
            fragment
        ).commit()
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
                var view = LayoutInflater.from(this@MainActivityAdmin)
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



                Glide.with(this@MainActivityAdmin).load(model.image).into(holder.image)


                holder.itemView.setOnClickListener {
                    val idCat = snapshots.getSnapshot(position).id

                    Log.d("hzm", idCat)


                    val docRef = db!!.collection("categories").document(idCat)
                    docRef.get()
                        .addOnSuccessListener { document ->
                            if (document != null) {
                                Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                                val intent = Intent(
                                    this@MainActivityAdmin,
                                    ProductActivity::class.java
                                )

                                val name = document.getString("name")
                                Toast.makeText(this@MainActivityAdmin, "$name", Toast.LENGTH_LONG)
                                    .show()
                                intent.putExtra("idCat", idCat)
                                intent.putExtra("Catproduct", name)
                                startActivity(intent)


                            }
                        }
                }

                holder.itemView.setOnLongClickListener(View.OnLongClickListener {


                    val docId = snapshots.getSnapshot(position).id
                    Log.d("hzm", docId)

                    val intent = Intent(
                        this@MainActivityAdmin,
                        CatrgoryEdit::class.java
                    )

                    intent.putExtra("id", docId)
                    startActivity(intent)

                    //    startActivity(intent)

                    /* val docRef = db!!.collection("categories").document(docId)
                     docRef.get()
                         .addOnSuccessListener { document ->
                             if (document != null) {
                                 Log.d("TAG", "DocumentSnapshot data: ${document.data}")
                                 val intent= Intent(this@MainActivityAdmin,ProductActivity::class.java)

                                 val name =     document.getString("name")
                                 Toast.makeText(this@MainActivityAdmin,"$name",Toast.LENGTH_LONG).show()
                                 intent.putExtra("Catproduct",name)
                                 startActivity(intent)


                             }
                         }*/



                    true
                })


            }


        }

        recyclerCate.layoutManager = GridLayoutManager(this, 2)
        recyclerCate.adapter = adapter


    }

    class categoriesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.categoryName

        var image = view.categoryImage
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }


    fun getProfileData() {
        db!!.collection("users").whereEqualTo("email", auth.currentUser!!.email).get()
            .addOnSuccessListener { querySnapshot ->
                //   nameUser.setText(querySnapshot.documents.get(0).get("name").toString())

                emailUser.setText(auth.currentUser!!.email)
                // Picasso.with(context).load(querySnapshot.documents.get(0).get("image").toString()).into(imgProfile);

                //  Glide.with(this).load(querySnapshot.documents.get(0).get("image")).into(imgUser)

            }.addOnFailureListener { exception ->

            }
    }
    /* private fun fetchProducts() {
         getProducts(object : DataStatus() {
             fun onSuccess(products: ArrayList<*>?) {
                 if (products_adapter == null) {
                     products_adapter = ProductAdapter(
                         productsList,
                         this,
                         object : RecyclerViewClickListenerProduct() {
                             override  fun onClick(
                                 view: View?,
                                 product: product
                             ) {
                                 val productPage =
                                     Intent(this@MainActivityAdmin, ProductActivity::class.java)
                                 productPage.putExtra("nomItem", product.name)
                               /*  productPage.putExtra("descItem", product.getDescription())
                                 productPage.putExtra("idItem", product.getId_product())
                                 productPage.putExtra("imgItem", product.getImg())
                                 productPage.putExtra("priceItem", product.getPrice())
                                 productPage.putExtra("idSeller", product.getId_seller())
                                 productPage.putExtra("idCat", product.getId_cat())*/
                                 startActivity(productPage)
                             }

                              override fun onClick(view: View?, product: product_view?) {
                                 TODO("Not yet implemented")
                             }
                         })
                     //recyclerView_products.setAdapter(products_adapter)
                 } else {
                   /*  products_adapter.getItems().clear()
                     products_adapter.getItems().addAll(productsList)
                     products_adapter.notifyDataSetChanged()*/
                 }
             }

             fun onError(e: String?) {
                 Toast.makeText(this@MainActivityAdmin, e, Toast.LENGTH_SHORT).show()
             }
         })
     }
 */


}
