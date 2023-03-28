package com.example.personalfinance_v12

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recordRecyclerview : RecyclerView
    private lateinit var recordArrayList : ArrayList<History>
    lateinit var tagId : Array<Int>
    lateinit var category : Array<String>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


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



    }

    private fun getUserdata() {

        for(i in tagId.indices){

            val records = History(tagId[i],category[i])
            recordArrayList.add(records)
    }

        recordRecyclerview.adapter = MyAdapter(recordArrayList)
}

}