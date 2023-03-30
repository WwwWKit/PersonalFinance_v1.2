package com.example.personalfinance_v12

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class TransactionAdapter (val context: Context, private val transactionList: ArrayList<TransactionModel>) : RecyclerView.Adapter<TransactionAdapter.ViewHolder>(){

//    private lateinit var mListener: onItemClickListener

//    interface onItemClickListener{
//        fun onItemClick(position: Int)
//    }

//    fun setOnItemClickListener(clickListener: onItemClickListener){
//        mListener = clickListener
//    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentTransaction = transactionList[position]
        Log.d("ADAP",currentTransaction.toString())
        Log.d("ADAP","RAN")

        holder.tvTransactionTitle.text = currentTransaction.title // get the current title

        if (currentTransaction.type == 1){
            holder.tvTransactionAmount.setTextColor(Color.parseColor("#ff9f1c"))
        }else{
            holder.tvTransactionAmount.setTextColor(Color.parseColor("#2ec4b6"))
        }
        holder.tvTransactionAmount.text = currentTransaction.amount.toString()

        holder.tvCategory.text = currentTransaction.category

        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
        val result = Date(currentTransaction.date!!)
        holder.tvDate.text = simpleDateFormat.format(result)

        if (currentTransaction.type == 1){
            holder.typeIcon.setImageResource(R.drawable.expenses)
        }else{
            holder.typeIcon.setImageResource(R.drawable.incomes)
        }
    }

    override fun getItemCount(): Int {
        return transactionList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTransactionTitle: TextView = itemView.findViewById(R.id.categoryTag)
        val tvTransactionAmount: TextView = itemView.findViewById(R.id.amount)
        val tvCategory: TextView = itemView.findViewById(R.id.title)
        val tvDate: TextView = itemView.findViewById(R.id.date)
        val typeIcon: ImageView = itemView.findViewById(R.id.tagImg)

//        init {
//            itemView.setOnClickListener {
//                clickListener.onItemClick(adapterPosition)
//            }
//        }
    }
}

/* Catat Uang App,
   A simple money tracker app.
   Created By Ferry Dwianta P
   First Created on 18/05/2022
*/