package com.walter.fireb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    val TAG = "DB_FB"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val butt: Button = findViewById(R.id.buttonFetch)
        val buttShow: Button = findViewById(R.id.showUsers)
        val inputName: EditText = findViewById(R.id.inputName)
        val inputAge: EditText = findViewById(R.id.inputAge)
        val inputSalary: EditText = findViewById(R.id.inputSalary)


        val database = Firebase.database
        val databaseRef = database.getReference("school")

        butt.setOnClickListener {
            val name = inputName.text.toString().trim()
            val age = inputAge.text.toString().trim().toIntOrNull()
            val salary = inputSalary.text.toString().trim().toDoubleOrNull()

            if (age == null || salary == null) {
                Toast.makeText(this, "Invalid Salary or Age Value", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(name, age, salary)
            databaseRef.child("users").push().setValue(user)
            inputName.text.clear()
            inputAge.text.clear()
            inputSalary.text.clear()

            val bottomSheetDialog = BottomSheetDialog(this)
            bottomSheetDialog.setContentView(R.layout.bottom_sheet_layout)
            bottomSheetDialog.show()
            bottomSheetDialog.setOnDismissListener {
                bottomSheetDialog.dismiss()
            }
        }

        buttShow.setOnClickListener {
            val intent = Intent(this, DisplayActivity::class.java)
            startActivity(intent)
        }

/*
        val user1 = User("Jane Jill", 22, 65000.0)
        val user2 = User("Hellen Jane", 24, 105000.0)
        val user3 = User("Jack Juma", 23, 165000.0)*/
        // databaseRef.child("users").push().setValue(user1)
        //databaseRef.child("users").push().setValue(user2)
        // databaseRef.child("users").push().setValue(user3)


        /*      databaseRef.addValueEventListener(object: ValueEventListener{
                  override fun onDataChange(snapshot: DataSnapshot) {
                      //Log.d(tag, "onDataChange: ${}")
                      val usersSnapshot = snapshot.child("users")
                      usersSnapshot.children.forEach {
                          val u = it.value
                          Log.d(TAG, "onDataChange: $it")
                          //val user = it.getValue(User::class.java)
                          //Log.d(tag, "onDataChange: ${user!!.name}")
                      }
                  }

                  override fun onCancelled(error: DatabaseError) {

                  }

              })*/


        /*  butt.setOnClickListener {
              Toast.makeText(this, "Fetching", Toast.LENGTH_SHORT).show()
              databaseRef.child("users").get().addOnSuccessListener {
                 // Log.d(TAG, "onCreate: $it")
                  for (child in it.children){
                     //Log.d(TAG, "onCreate: $child")
                     val x =  child.getValue(User::class.java)
                      if (x != null) {
                          Log.d(TAG, "onCreate: ${x.name}")
                      }
                  }
              }.addOnFailureListener {
                  Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
              }
          }*/

    }
}