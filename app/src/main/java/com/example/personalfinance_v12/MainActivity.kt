package com.example.personalfinance_v12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var recordRecyclerview : RecyclerView
    private lateinit var recordArrayList : ArrayList<History>
    lateinit var tagId : Array<Int>
    lateinit var category : Array<String>

    lateinit var incomesFAB: FloatingActionButton
    lateinit var expensesFAB: FloatingActionButton
    lateinit var mainFAB: FloatingActionButton
    var fabVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//RECYLERVIEW
        tagId = arrayOf(
            R.drawable.bill,
            R.drawable.food,
            R.drawable.petrol,
            R.drawable.shop
        )

        category = arrayOf(
            "BILL",
            "FOOD",
            "PETROL",
            "SHOP"
        )

        recordRecyclerview =findViewById(R.id.recyclerView)
        recordRecyclerview.layoutManager = LinearLayoutManager(this)
        recordRecyclerview.setHasFixedSize(true)

        recordArrayList = arrayListOf<History>()
        getUserdata()

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
            Toast.makeText(this@MainActivity, "Expenses clicked..", Toast.LENGTH_LONG).show()
        }

        // on below line we are adding on
        // click listener for settings fab
        incomesFAB.setOnClickListener {
            // on below line we are displaying a toast message
            Toast.makeText(this@MainActivity, "Incomes clicked..", Toast.LENGTH_LONG).show()
        }

    }

    private fun getUserdata() {

        for(i in tagId.indices){

            val records = History(tagId[i],category[i])
            recordArrayList.add(records)
    }

        recordRecyclerview.adapter = MyAdapter(recordArrayList)
}


}