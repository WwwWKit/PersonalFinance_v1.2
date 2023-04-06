package com.example.personalfinance_v12

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var recordRecyclerview: RecyclerView
    private lateinit var adapter: TransactionAdapter
    private lateinit var database: DatabaseReference
    private lateinit var mainFAB: FloatingActionButton
    private lateinit var transactionList: ArrayList<TransactionModel>
    private lateinit var tvNetAmount : TextView
    private lateinit var tvAmountExpense : TextView
    private lateinit var tvAmountIncome : TextView
    private lateinit var user: FirebaseUser


    //initialize var for storing amount value from db
    var allTimeExpense: Double = 0.0
    var allTimeIncome: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        transactionList = ArrayList()
        database = Firebase.database.reference
        adapter = TransactionAdapter(this, transactionList)
        recordRecyclerview = findViewById(R.id.recyclerView)
        recordRecyclerview.layoutManager = LinearLayoutManager(this)
        recordRecyclerview.adapter = adapter


        fetchAmount()
        showAllTimeRecap()

        // get data
        val transactionListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                transactionList.clear()
                for (transactionSnapshot in snapshot.children) {
                    val transaction = transactionSnapshot.getValue(TransactionModel::class.java)
                    transactionList.add(transaction as TransactionModel)

                }

            //reverse listing
//                transactionList.reverse()
//                Log.d("HERE", adapter.itemCount.toString())


                adapter.notifyDataSetChanged()


            }



            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error: " + error.toString(), Toast.LENGTH_LONG)
                    .show()
            }
        }

        user = FirebaseAuth.getInstance().currentUser!!
        database = Firebase.database.reference

        database.child("transaction").orderByChild("uid").equalTo(user.uid)
            .addValueEventListener(transactionListener)





//        // menu code
//        // Initialize and assign variable
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
//
//        // Set Home selected
        bottomNavigationView.selectedItemId = R.id.dashboard
//
//        // Perform item selected listener
        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard ->
                    return@OnNavigationItemSelectedListener true

                R.id.profile -> {
                    startActivity(Intent(this@MainActivity, Profile::class.java))
                    // override default transition from page to page
                    overridePendingTransition(0, 0)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        })



//FAB
        // initializing variables of floating
        // action button on below line.
        mainFAB = findViewById(R.id.idmainFAB)

        mainFAB.setOnClickListener {
            val intent = Intent(this@MainActivity, InsertionActivity::class.java)
            intent.putExtra("type", 1)
            startActivity(intent)

        }
    }

    private fun showAllTimeRecap() {
        //---show recap after calculation---
        tvNetAmount = findViewById(R.id.netAmount)
        tvAmountExpense = findViewById(R.id.expenseAmount)
        tvAmountIncome = findViewById(R.id.incomeAmount)

        tvNetAmount.text = "${allTimeIncome - allTimeExpense}"
        tvAmountExpense.text = "$allTimeExpense"
        tvAmountIncome.text = "$allTimeIncome"

    }

    private fun fetchAmount() { //show and calculate transaction recap
        var amountExpenseTemp = 0.0
        var amountIncomeTemp = 0.0

        val transactionList: ArrayList<TransactionModel> = ArrayList<TransactionModel>()

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                transactionList.clear()
                if (snapshot.exists()) {
                    for (transactionSnap in snapshot.children) {
                        val transactionData =
                            transactionSnap.getValue(TransactionModel::class.java) //reference data class
                        transactionList.add(transactionData!!)
                    }

                    adapter.notifyDataSetChanged()


                    //take all amount expense and income :
                    for ((i) in transactionList.withIndex()) {
                        if (transactionList[i].type == 1) {
                            amountExpenseTemp += transactionList[i].amount!!
                        } else if (transactionList[i].type == 2) {
                            amountIncomeTemp += transactionList[i].amount!!
                        }
                    }
                    allTimeExpense = amountExpenseTemp
                    allTimeIncome = amountIncomeTemp

                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}


