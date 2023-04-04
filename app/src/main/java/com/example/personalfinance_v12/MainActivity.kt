package com.example.personalfinance_v12

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var recordRecyclerview: RecyclerView

    //    private lateinit var recordArrayList : ArrayList<History>
    lateinit var tagId: Array<Int>
    lateinit var category: Array<String>
    private lateinit var adapter: TransactionAdapter
    lateinit var transactionList: ArrayList<TransactionModel>
    private lateinit var dbRef: DatabaseReference
    //initialize var for Floating Action Button
//    lateinit var incomesFAB: ExtendedFloatingActionButton
//    lateinit var expensesFAB: ExtendedFloatingActionButton
    lateinit var mainFAB: FloatingActionButton
    var fabVisible = false
    //initialize var for storing amount value from db
    var amountExpense: Double = 0.0
    var amountIncome: Double = 0.0
    var allTimeExpense: Double = 0.0
    var allTimeIncome: Double = 0.0
    //Date
    private var dateStart: Long = 0
    private var dateEnd: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        transactionList = ArrayList()
        dbRef = Firebase.database.reference
        adapter = TransactionAdapter(this, transactionList)
        recordRecyclerview = findViewById(R.id.recyclerView)
        recordRecyclerview.layoutManager = LinearLayoutManager(this)
//        recordRecyclerview.setHasFixedSize(true)

        recordRecyclerview.adapter = adapter

        setInitDate()
//        fetchAmount()
        showAllTimeRecap()

        // get data
        val transactionListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                transactionList.clear()
                for (transactionSnapshot in snapshot.children) {
                    val transaction = transactionSnapshot.getValue(TransactionModel::class.java)
                    transactionList.add(transaction as TransactionModel)

//                    Log.d("USERS", userSnapshot.value)
                }

                transactionList.reverse()
                Log.d("HERE", adapter.itemCount.toString())


                adapter.notifyDataSetChanged()

            }


            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity, "Error: " + error.toString(), Toast.LENGTH_LONG)
                    .show()
            }
        }

        dbRef.child("transaction").orderByChild("date")
            .addValueEventListener(transactionListener)


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
//        expensesFAB = findViewById(R.id.idexpensesFAB)
//        incomesFAB = findViewById(R.id.idincomesFAB)

        // on below line we are initializing our
        // fab visibility boolean variable
//        fabVisible = false

        mainFAB.setOnClickListener{
            val intent = Intent(this@MainActivity, InsertionActivity::class.java)
            intent.putExtra("type", 1)
            startActivity(intent)
        }
        // on below line we are adding on click listener
        // for our add floating action button
//        mainFAB.setOnClickListener {
//            // on below line we are checking
//            // fab visible variable.
//            if (!fabVisible) {
//
//                // if its false we are displaying home fab
//                // and settings fab by changing their
//                // visibility to visible.
//                expensesFAB.show()
//                incomesFAB.show()
//
//                // on below line we are setting
//                // their visibility to visible.
//                expensesFAB.visibility = View.VISIBLE
//                incomesFAB.visibility = View.VISIBLE
//
//                // on below line we are checking image icon of add fab
//                mainFAB.setImageDrawable(resources.getDrawable(R.drawable.close))
//
//                // on below line we are changing
//                // fab visible to true
//                fabVisible = true
//            } else {
//
//                // if the condition is true then we
//                // are hiding home and settings fab
//                expensesFAB.hide()
//                incomesFAB.hide()
//
//                // on below line we are changing the
//                // visibility of home and settings fab
//                expensesFAB.visibility = View.GONE
//                incomesFAB.visibility = View.GONE
//
//                // on below line we are changing image source for add fab
//                mainFAB.setImageDrawable(resources.getDrawable(R.drawable.add))
//
//                // on below line we are changing
//                // fab visible to false.
//                fabVisible = false
//            }
//        }
//
//        // on below line we are adding
//        // click listener for our home fab
//        expensesFAB.setOnClickListener {
//            // on below line we are displaying a toast message.
//            val intent = Intent(this@MainActivity, InsertionActivity::class.java)
//            intent.putExtra("type", 1)
//            startActivity(intent)
//        }
//
//        // on below line we are adding on
//        // click listener for settings fab
//        incomesFAB.setOnClickListener {
//            // on below line we are displaying a toast message
//            val intent = Intent(this@MainActivity, InsertionActivity::class.java)
//            intent.putExtra("type", 2)
//            startActivity(intent)
//        }

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

    private fun showAllTimeRecap() {
        //---show recap after calculation---
        val tvNetAmount: TextView = findViewById(R.id.netAmount)
        val tvAmountExpense: TextView = findViewById(R.id.expenseAmount)
        val tvAmountIncome: TextView = findViewById(R.id.incomeAmount)

        tvNetAmount.text = "${allTimeIncome - allTimeExpense}"
        tvAmountExpense.text = "$allTimeExpense"
        tvAmountIncome.text = "$allTimeIncome"

        }

    private fun fetchAmount(dateStart : Long, dateEnd : Long) { //show and calculate transaction recap
        var amountExpenseTemp = 0.0
        var amountIncomeTemp = 0.0

        val transactionList: ArrayList<TransactionModel> = ArrayList<TransactionModel>()

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                transactionList.clear()
                if (snapshot.exists()) {
                    for (transactionSnap in snapshot.children) {
                        val transactionData =
                            transactionSnap.getValue(TransactionModel::class.java) //reference data class
                        transactionList.add(transactionData!!)
                    }
                }
                //separate expanse amount and income amount, and show it based on the range date :
                for ((i) in transactionList.withIndex()) {
                    if (transactionList[i].type == 1
                        &&
                        transactionList[i].date!! > dateStart - 86400000 && //minus by 1 day
                        transactionList[i].date!! <= dateEnd
                    ) {
                        amountExpenseTemp += transactionList[i].amount!!
                    } else if (transactionList[i].type == 2
                        &&
                        transactionList[i].date!! > dateStart - 86400000 &&
                        transactionList[i].date!! <= dateEnd
                    ) {
                        amountIncomeTemp += transactionList[i].amount!!
                    }
                }
                amountExpense = amountExpenseTemp
                amountIncome = amountIncomeTemp

                var amountExpenseTemp = 0.0 //reset
                var amountIncomeTemp = 0.0

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

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    private fun setInitDate() {
//        val dateRangeButton: Button = findViewById(com.google.firebase.database.R.id.buttonDate)

        val currentDate = Date()
        val cal: Calendar = Calendar.getInstance(TimeZone.getDefault())
        cal.time = currentDate

        val startDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH) //get the first date of the month
        cal.set(Calendar.DAY_OF_MONTH, startDay)
        val startDate = cal.time
        dateStart= startDate.time //convert to millis

        val endDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH) //get the last date of the month
        cal.set(Calendar.DAY_OF_MONTH, endDay)
        val endDate = cal.time
        dateEnd= endDate.time //convert to millis

        fetchAmount(dateStart, dateEnd) //call fetch amount so showAllTimeRecap() can be executed
//        dateRangeButton.text = "This Month"
    }


    override fun onResume() {
        super.onResume()

        showAllTimeRecap() //show all time recap text
    }
}







