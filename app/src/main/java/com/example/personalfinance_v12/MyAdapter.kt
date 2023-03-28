package com.example.personalfinance_v12

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val recordlist : ArrayList<History>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,
        parent,false)
        return MyViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = recordlist[position]
        holder.tagImage.setImageResource(currentItem.tagImage)
        holder.tagText.text = currentItem.category
    }



    override fun getItemCount(): Int {

        return recordlist.size
    }



    class MyViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {


        val tagImage : ImageView = itemView.findViewById(R.id.tagImg)
        val tagText : TextView = itemView.findViewById(R.id.tagText)
    }

}