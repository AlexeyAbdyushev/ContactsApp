package com.example.contactapp

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ContactsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var name: TextView? = null
    var phoneNum: TextView? = null
    var image: ImageView? = null
    init {
        name = itemView.findViewById(R.id.name)
        phoneNum = itemView.findViewById(R.id.phone_number)
        image = itemView.findViewById(R.id.avatar)
    }
    fun bind(contact: Contact) {
        name?.text = contact.name
        phoneNum?.text = contact.phone
        image?.let { _image ->
            ImageHelper.loadImageByUri(contact.imageUri, itemView, _image)
        }
    }
}