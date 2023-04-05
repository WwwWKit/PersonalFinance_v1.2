package com.example.personalfinance_v12

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.net.URL


class Profile : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var user : FirebaseUser
    private lateinit var btnLogout: ImageButton
    private lateinit var tvName : TextView
    private lateinit var tvEmail : TextView
    private lateinit var imgProfile : ImageView
    private lateinit var mDbRef: DatabaseReference
    private lateinit var changeImgProfile: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        btnLogout = findViewById(R.id.btn_logout)
        auth = FirebaseAuth.getInstance()
        mDbRef = FirebaseDatabase.getInstance().getReference()
        user = FirebaseAuth.getInstance().currentUser!!
        tvName = findViewById(R.id.tv_Name)
        tvEmail = findViewById(R.id.tv_Email)
        imgProfile = findViewById(R.id.picture)


        val eventListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val currentUser = snapshot.getValue(User::class.java) as User
                    tvName.setText(currentUser.name.toString())
                    tvEmail.setText(currentUser.email.toString())
                }
            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@Profile,"Error: "+error.toString(), Toast.LENGTH_LONG).show()
            }
        }

        val snapshot = mDbRef.child("user").child(user.uid)
            .addListenerForSingleValueEvent(eventListener)



        //Logout Button
        btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this@Profile,Login::class.java))
        }




        // menu code
        // Initialize and assign variable
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // Set Home selected
        bottomNavigationView.selectedItemId = R.id.profile

        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.profile ->
                    return@OnNavigationItemSelectedListener true

                R.id.dashboard -> {
                    startActivity(Intent(this@Profile, MainActivity::class.java))
                    // override default transition from page to page
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })


    }


}
