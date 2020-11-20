package com.example.contactapp

import android.R
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView


class NumbersAdapter :RecyclerView.Adapter<NumbersViewHolder>(){

    private var list = mutableListOf<String>()
    private var context: Context? = null
    fun setNumbers(list: MutableList<String>) {
        this.list.addAll(list)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumbersViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(com.example.contactapp.R.layout.number_item, parent, false)
        return NumbersViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NumbersViewHolder, position: Int) {
        val number: String = list[position]
        holder.bind(number)
    }
    override fun getItemCount(): Int = list.size

}