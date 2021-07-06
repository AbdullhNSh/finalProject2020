package com.example.finalproject2020.User

import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalproject2020.R
import com.example.finalproject2020.adapter.ProductAdapter
import com.example.finalproject2020.model.product_view
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.android.synthetic.main.activity_prodct_user1.*
import kotlinx.android.synthetic.main.item_product.view.*


class ProdctUser1 : AppCompatActivity() {

    var adapter: FirestoreRecyclerAdapter<product_view, productViewHolder>? = null

    var db: FirebaseFirestore? = null

    // var adapter: FirestoreRecyclerAdapter<User, UserViewHolder>? = null
    lateinit var thoughtsListener: ListenerRegistration


    val thoughtsCollectionRef = FirebaseFirestore.getInstance().collection("categories")

    val thoughts = arrayListOf<product_view>()
    lateinit var thoughtAdapter: ProductAdapter

    lateinit var Card: CardView
    // var db: FirebaseFirestore? = null
    //  var adapter: FirestoreRecyclerAdapter<product_view, productViewHolder>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prodct_user1)
        //

        //Toast.makeText(this,Cat,Toast.LENGTH_LONG).show()

        val idCat = intent.getStringExtra("idCat")

        getData(idCat!!)

        back.setOnClickListener {
            val intent = Intent(
                this,
                MainActivityUser::class.java
            )
            startActivity(intent)
            finish()
        }

    }

    fun setListener() {
        Toast.makeText(this, "setListener", Toast.LENGTH_LONG).show()



        thoughtsListener = thoughtsCollectionRef
            .addSnapshotListener(this) { snapshot, exception ->
                if (exception != null) {
                    Log.e("hzm", exception.toString())
                }

                if (snapshot != null) {
                    // parseData(snapshot)

                }

            }

    }


    fun getData(idCat: String) {


        val query = thoughtsCollectionRef.document(idCat).collection("Product")
        val options = FirestoreRecyclerOptions.Builder<product_view>()
            .setQuery(query, product_view::class.java).build()

        adapter = object : FirestoreRecyclerAdapter<product_view, productViewHolder>(options) {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): productViewHolder {
                var view = LayoutInflater.from(this@ProdctUser1)
                    .inflate(R.layout.item_products, parent, false)
                return productViewHolder(
                    view
                )
            }

            override fun onBindViewHolder(
                holder: productViewHolder,
                position: Int,
                model: product_view
            ) {
                holder.name.text = model.name
                holder.price.text = model.price.toString()
                Glide.with(this@ProdctUser1).load(model.image).into(holder.image)

                holder.itemView.setOnClickListener {
                    val idProduct = snapshots.getSnapshot(position).id
                    Log.e("idProduct", idProduct)
                    val intent = Intent(this@ProdctUser1, ViewProductUser::class.java)
                    intent.putExtra("idCat", idCat)
                    intent.putExtra("idProduct", idProduct)
                    startActivity(intent)
                }
            }
        }

        product.layoutManager = GridLayoutManager(this,2)
        product.adapter = adapter


    }


    class productViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name = view.productName

        var image = view.productImage
        var price = view.productPrice
    }


    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }

    fun getRoundedCornerBitmap(bitmap: Bitmap): Bitmap? {
        val output = Bitmap.createBitmap(
            bitmap.width,
            bitmap.height, Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)
        val color = -0xbdbdbe
        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)
        val rectF = RectF(rect)
        val roundPx = 12f
        paint.setAntiAlias(true)
        canvas.drawARGB(0, 0, 0, 0)
        paint.setColor(color)
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
        paint.setXfermode(PorterDuffXfermode(PorterDuff.Mode.SRC_IN))
        canvas.drawBitmap(bitmap, rect, rect, paint)
        return output
    }

}


/*  fun parseData(snapshot: QuerySnapshot) {
            thoughts.clear()

            for (document in snapshot!!.documents) {
                val data = document.data
                val name = data!!["name"] as String

                val id = document.id

                val newThought =
                    product_view(id, name)
                thoughts.add(newThought)
                // Toast.makeText(this,"$newThought", Toast.LENGTH_LONG).show()


            }
            thoughtAdapter.notifyDataSetChanged(

            )
        }*/