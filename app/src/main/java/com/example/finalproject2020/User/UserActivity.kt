package com.example.finalproject2020.User

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_user.*
import com.bumptech.glide.Glide
import com.example.finalproject2020.R
import com.example.finalproject2020.model.User
import kotlinx.android.synthetic.main.user_item.view.*


class UserActivity : AppCompatActivity(){
    var db: FirebaseFirestore? = null
    var adapter: FirestoreRecyclerAdapter<User, UserViewHolder>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        db = Firebase.firestore

        getAllUser()

    }

    fun getAllUser() {
        val query = db!!.collection("users")
        val options =
            FirestoreRecyclerOptions.Builder<User>().setQuery(query, User::class.java).build()

        adapter = object : FirestoreRecyclerAdapter<User, UserViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
                var view = LayoutInflater.from(this@UserActivity)
                    .inflate(R.layout.user_item, parent, false)
                return UserViewHolder(
                    view
                )
            }

            override fun onBindViewHolder(holder: UserViewHolder, position: Int, model: User) {
                holder.name.text = model.name
                holder.email.text = model.email
                holder.phone.text = model.phone
                holder.address.text = model.address
                Glide.with(this@UserActivity).load(model.image).into(holder.image)

            }


        }

        all_user_recycle.layoutManager = LinearLayoutManager(this)
        all_user_recycle.adapter = adapter

    }

    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.tv_name
        var email = view.tv_email
        var phone = view.tv_phone
        var address = view.tv_address
        var image = view.image_profile
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }
}

/*
    lateinit var auth: FirebaseAuth

       lateinit var db: FirebaseFirestore
        var adapter: FirestoreRecyclerAdapter<categories, UserViewHolder>? = null
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_user)
            db = Firebase.firestore
auth = Firebase.auth
            getAllUser()

        }

    fun getAllUser() {
        val query = db.collection("categories")
        val options =
            FirestoreRecyclerOptions.Builder<categories>().setQuery(query, categories::class.java).build()

        adapter = object : FirestoreRecyclerAdapter<categories, UserViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
                var view = LayoutInflater.from(this@UserActivity)
                    .inflate(R.layout.item_category1, parent, false)
                return UserViewHolder(view)
            }

            override fun onBindViewHolder(holder: UserViewHolder, position: Int, model: categories) {
                holder.name.text = model.name
                // holder.email.text = model.image.toString()

             Glide.with(this@UserActivity).load(model.image).into(holder.image)

            }



        }





        all_user_recycle.layoutManager = GridLayoutManager(this,2)
        all_user_recycle.adapter = adapter
        all_user_recycle.addOnItemTouchListener(
            RecyclerItemClickListener(
                this, all_user_recycle, object : RecyclerItemClickListener.OnItemClickListener {
                   // val c = db.ProductCursor(order)

                 //   val ProductID = c.getInt(c.getColumnIndex("id"))
                    override fun onItemClick(view: View, position: Int) {
                        //     Toast.makeText(this@AllUser, "YESSSS", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@UserActivity,AddCateg::class.java)


                       // intent.putExtra("id", ProductID.toString() + "")

                        startActivity(intent)
                    }
                    override fun onLongItemClick(view: View, position: Int) {
                        registerForContextMenu(cardview)
                        //Toast.makeText(this@UserActivity,"LOng",Toast.LENGTH_LONG).show()

                    }}
            ))


    }








    class UserViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.categoryName!!
       var image = view.categoryImage!!
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }



    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu!!, v!!, menuInfo)
        menuInflater.inflate(R.menu.context_menu, menu)
        menu.setHeaderTitle("Select Action :")
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {
        //     val info = item.menuInfo as AdapterView.AdapterContextMenuInfo

       // val db = DatabaseHelper(this)
        //val c = db.ProductCursor(order)
        when (item.itemId) {
            R.id.update -> {
/*
             /   db.collection("abed").document(id)
                    .delete()
                    .addOnSuccessListener {
                      /  Log.e(TAG, "User deleted successfully")
                    }
                    .addOnFailureListener { exception ->
                        Log.e(TAG, exception.message)
                    }*/
               val intent = Intent(this,UpdateCategori::class.java)
                db!!.collection("categories").whereEqualTo("id",auth.currentUser!!.uid).get()
                    .addOnSuccessListener { querySnapshot ->


                      val id = querySnapshot.documents.get(0).get("id").toString()
                       // phone.setText(querySnapshot.documents.get(0).get("phone").toString())
                       // address.setText(querySnapshot.documents.get(0).get("address").toString())
                       // email.setText(auth.currentUser!!.email)
                        Toast.makeText(this,auth.currentUser!!.uid,Toast.LENGTH_LONG).show()

                       intent.putExtra("id", id)
                       // Toast.makeText(this,id.toString(),Toast.LENGTH_LONG).show()
                     startActivity(intent)

                    }.addOnFailureListener { exception ->
Toast.makeText(this,"errrror",Toast.LENGTH_LONG).show()
                    }


               // val c = db.ProductCursor(order)
                //  c.moveToPosition(info.position)
               // val ProductID = c.getInt(c.getColumnIndex("id"))


               // intent.putExtra("id", ProductID.toString() + "")
                startActivity(intent)
            }
            R.id.delete -> {
                //val ProductID = c.getInt(c.getColumnIndex("id"))
               // db.deleteStudent(ProductID)
                Toast.makeText(this, "delete", Toast.LENGTH_SHORT).show()
                //  val id = cursor.getInt(cursor.getColumnIndex("_id"))

            }
            R.id.share -> Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show()
            R.id.exit->{finish()}
        }

        return super.onContextItemSelected(item)
    }
}





*/
