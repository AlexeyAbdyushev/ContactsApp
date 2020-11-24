package com.example.contactapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class NumbersAdapter :RecyclerView.Adapter<NumbersViewHolder>(){

    private var list = mutableListOf<String>()
    fun setNumbers(list: ArrayList<String>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumbersViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(com.example.contactapp.R.layout.row_phone_number, parent, false)
        return NumbersViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NumbersViewHolder, position: Int) {
        val number: String = list[position]
        holder.bind(number)
    }
    override fun getItemCount(): Int = list.size

}