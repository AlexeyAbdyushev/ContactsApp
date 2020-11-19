package com.example.contactapp
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ContactAdapter(private val list: List<Contact>): RecyclerView.Adapter<ContactAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.contact, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact: Contact = list[position]
        holder.bind(contact)
    }

    override fun getItemCount(): Int = list.size

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
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
            if (contact.imageUri != null) {
                image?.setImageURI(Uri.parse(contact.imageUri))
            }
        }
    }
}