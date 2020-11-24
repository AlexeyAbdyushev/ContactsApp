package com.example.contactapp
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class ContactsAdapter(val listener: (Int) -> Unit): RecyclerView.Adapter<ContactsViewHolder>(){

    private var list = mutableListOf<Contact>()
    private var context: Context? = null
    fun setContext(context:Context) {
        this.context = context
    }
    fun setContacts(list: MutableList<Contact>) {
        this.list.addAll(list)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.row_contact, parent, false)
        return ContactsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact: Contact = list[position]
        holder.bind(contact)
        holder.itemView.setOnClickListener {
            listener(position)
        }
    }
    override fun getItemCount(): Int = list.size
}