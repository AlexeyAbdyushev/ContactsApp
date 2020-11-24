package com.example.contactapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NumbersViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var phoneNum: TextView? = null
    init {
        phoneNum = itemView.findViewById(R.id.number)
    }
    fun bind(phone: String) {
        phoneNum?.text = phone
    }
}