package com.example.personalfinance_v12

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var recordRecyclerview : RecyclerView
//    private lateinit var recordArrayList : ArrayList<History>
    lateinit var tagId : Array<Int>
    lateinit var category : Array<String>
    private lateinit var adapter: TransactionAdapter

    private lateinit var database: DatabaseReference

    lateinit var incomesFAB: ExtendedFloatingActionButton
    lateinit var expensesFAB: ExtendedFloatingActionButton
    lateinit var mainFAB: FloatingActionButton
    var fabVisible = false
    lateinit var transactionList: ArrayList<TransactionModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        transactionList = ArrayList()
        database = Firebase.database.reference
        adapter = TransactionAdapter(this, transactionList)
        recordRecyclerview =findViewById(R.id.recyclerView)
        recordRecyclerview.layoutManager = LinearLayoutManager(this)
//        recordRecyclerview.setHasFixedSize(true)

        recordRecyclerview.adapter = adapter

        // get data
        val transactionListener = object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                transactionList.clear()
                for(transactionSnapshot in snapshot.children){
                    val transaction = transactionSnapshot.getValue(TransactionModel::class.java)
                    transactionList.add(transaction as TransactionModel)

//                    Log.d("USERS", userSnapshot.value)
                }

                transactionList.reverse()
                Log.d("HERE", adapter.itemCount.toString())


                adapter.notifyDataSetChanged()

            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity,"Error: "+error.toString(),Toast.LENGTH_LONG).show()
            }
        }

        database.child("transaction").orderByChild("date").addValueEventListener(transactionListener)


//        // menu code
//        // Initialize and assign variable
//        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
//
//        // Set Home selected
//        bottomNavigationView.selectedItemId = R.id.dashboard
//
//        // Perform item selected listener
//        bottomNavigationView.setOnNavigationItemSelectedListener(BottomNavigationView.OnNavigationItemSelectedListener { item ->
//            when (item.itemId) {
//                R.id.dashboard ->
//                    return@OnNavigationItemSelectedListener true
//
//                R.id.transaction ->{
//                    startActivity(Intent(this@MainActivity, Transaction::class.java))
//                    // override default transition from page to page
//                    overridePendingTransition(0, 0)
//                    return@OnNavigationItemSelectedListener true
//                }
//            }
//            false
//        })


//RECYLERVIEW
//        tagId = arrayOf(
//            R.drawable.food,
//            R.drawable.petrol,
//            R.drawable.entertainment,
//            R.drawable.education,
//            R.drawable.bill,
//            R.drawable.shop,
//            R.drawable.communication,
//            R.drawable.investment,
//            R.drawable.health,
//            R.drawable.more,
//
//            R.drawable.salary,
//            R.drawable.award,
//            R.drawable.gift,
//            R.drawable.money,
//            R.drawable.more
//        )
//
//        category = arrayOf(
//            "FOOD",
//            "TRANSPORT",
//            "ENTERTAINMENT",
//            "EDUCATION",
//            "BILLS",
//            "SHOPPING",
//            "COMMUNICATION",
//            "INVESTMENT",
//            "HEALTH",
//            "OTHER EXPENSE",
//
//            "SALARY",
//            "AWARD",
//            "GIFT",
//            "INVESTMENT RETURN",
//            "OTHER INCOME"
//        )


//        recordArrayList = arrayListOf<History>()
//        getUserdata()

//FAB
        // initializing variables of floating
        // action button on below line.
        mainFAB = findViewById(R.id.idmainFAB)
        expensesFAB = findViewById(R.id.idexpensesFAB)
        incomesFAB = findViewById(R.id.idincomesFAB)

        // on below line we are initializing our
        // fab visibility boolean variable
        fabVisible = false

        // on below line we are adding on click listener
        // for our add floating action button
        mainFAB.setOnClickListener {
            // on below line we are checking
            // fab visible variable.
            if (!fabVisible) {

                // if its false we are displaying home fab
                // and settings fab by changing their
                // visibility to visible.
                expensesFAB.show()
                incomesFAB.show()

                // on below line we are setting
                // their visibility to visible.
                expensesFAB.visibility = View.VISIBLE
                incomesFAB.visibility = View.VISIBLE

                // on below line we are checking image icon of add fab
                mainFAB.setImageDrawable(resources.getDrawable(R.drawable.close))

                // on below line we are changing
                // fab visible to true
                fabVisible = true
            } else {

                // if the condition is true then we
                // are hiding home and settings fab
                expensesFAB.hide()
                incomesFAB.hide()

                // on below line we are changing the
                // visibility of home and settings fab
                expensesFAB.visibility = View.GONE
                incomesFAB.visibility = View.GONE

                // on below line we are changing image source for add fab
                mainFAB.setImageDrawable(resources.getDrawable(R.drawable.add))

                // on below line we are changing
                // fab visible to false.
                fabVisible = false
            }
        }

        // on below line we are adding
        // click listener for our home fab
        expensesFAB.setOnClickListener {
            // on below line we are displaying a toast message.
            val intent = Intent(this@MainActivity,InsertionActivity::class.java)
            intent.putExtra("type",1)
            startActivity(intent)        }

        // on below line we are adding on
        // click listener for settings fab
        incomesFAB.setOnClickListener {
            // on below line we are displaying a toast message
            val intent = Intent(this@MainActivity,InsertionActivity::class.java)
            intent.putExtra("type",2)
            startActivity(intent)
        }

    }

//    private fun getUserdata() {
//
//        for(i in tagId.indices){
//
//            val records = History(tagId[i],category[i])
//            recordArrayList.add(records)
//    }
//
//        recordRecyclerview.adapter = rwAdapter(recordArrayList)
//}


}