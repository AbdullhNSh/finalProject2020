package com.example.finalproject2020
/*
import android.os.Bundle

import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore

import java.util.*

class MainActivity : AppCompatActivity() {
    var toDoList: MutableList<ToDo> = ArrayList<ToDo>()
    var db: FirebaseFirestore? = null
    var listItem: RecyclerView? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    var fab: FloatingActionButton? = null
    var title: MaterialEditText? = null
    var description // made this public because i want to access from ListAdapter
            : MaterialEditText? = null
    var isUpdate = false // flag to check is update or add new;
    var idUpdate = "" // id of item need to update
    var adapter: ListItemAdapter? = null
    var dialog: SpotsDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initiate FireStore
        db = FirebaseFirestore.getInstance()

        //View
        dialog = SpotsDialog(this)
        title = findViewById(R.id.title)
        description = findViewById(R.id.description)
        fab = findViewById(R.id.fab)
        fab.setOnClickListener(View.OnClickListener {
            //add New
            if (!isUpdate) {
                setData(title.getText().toString(), description.getText().toString())
            } else {
                updateData(title.getText().toString(), description.getText().toString())
                isUpdate = !isUpdate // reset flag
            }
        })
        listItem = findViewById(R.id.listTodo)
        listItem.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(this)
        listItem.setLayoutManager(layoutManager)
        loadData() // Load data from fireStore
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        if (item.title == "DELETE") deleteItem(item.order)
        return super.onContextItemSelected(item)
    }

    private fun deleteItem(index: Int) {
        db!!.collection("ToDoList")
            .document(toDoList[index].getId())
            .delete()
            .addOnSuccessListener { loadData() }
    }

    private fun updateData(title: String, description: String) {
        db!!.collection("ToDoList").document(idUpdate)
            .update("title", title, "description", description)
            .addOnSuccessListener {
                Toast.makeText(
                    this@MainActivity,
                    "Updated !",
                    Toast.LENGTH_SHORT
                ).show()
            }

        //realtime update refresh data
        db!!.collection("ToDoList").document(idUpdate)
            .addSnapshotListener { documentSnapshot, e -> loadData() }
    }

    private fun setData(title: String, description: String) {
        // Random Id
        val id = UUID.randomUUID().toString()
        val toDo: MutableMap<String, Any> =
            HashMap()
        toDo["id"] = id
        toDo["title"] = title
        toDo["description"] = description
        db!!.collection("ToDoList").document(id)
            .set(toDo).addOnSuccessListener { // refresh data
                loadData()
            }
    }

    private fun loadData() {
        dialog.show()
        if (toDoList.size > 0) toDoList.clear() // Remove the old value
        db!!.collection("ToDoList")
            .get()
            .addOnCompleteListener { task ->
                for (documentSnapshot in task.result!!) {
                    val toDo = ToDo(
                        documentSnapshot.getString("id"),
                        documentSnapshot.getString("title"),
                        documentSnapshot.getString("description")
                    )
                    toDoList.add(toDo)
                }
                adapter = ListItemAdapter(this@MainActivity, toDoList)
                listItem!!.adapter = adapter
                dialog.dismiss()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this@MainActivity,
                    "" + e.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}*/