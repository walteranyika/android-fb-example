package com.walter.fireb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class DisplayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dispalay)
        val  recyclerUsers : RecyclerView = findViewById(R.id.recyclerViewUsers)
        val listUsers = ArrayList<User>()
        val layoutManager =  LinearLayoutManager(this)
        recyclerUsers.layoutManager =layoutManager
        val  adapter = CustomAdapter(listUsers)
        recyclerUsers.adapter = adapter

        val divider = DividerItemDecoration(this, layoutManager.orientation)
        recyclerUsers.addItemDecoration(divider)

        val firebase = Firebase.database
        val dbRef = firebase.getReference("school")

        dbRef.child("users").get().addOnSuccessListener {
            listUsers.clear()
            for (child in it.children){
                val user = child.getValue(User::class.java)
                listUsers.add(user!!)
            }
            adapter.notifyDataSetChanged()
        }
    }
}