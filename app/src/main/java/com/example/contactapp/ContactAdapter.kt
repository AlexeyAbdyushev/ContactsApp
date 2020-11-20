package com.example.contactapp
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView


class ContactAdapter: RecyclerView.Adapter<ContactsViewHolder>(){

    private var list = mutableListOf<Contact>()
    private var context: Context? = null
    fun setContext(context:Context) {
        this.context = context
    }
    fun setContacts(list: MutableList<Contact>) {
        this.list.addAll(list)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.contact, parent, false)
        return ContactsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact: Contact = list[position]
        holder.bind(contact)
        holder.itemView.setOnClickListener {
            val fragment = ContactInfoFragment.newInstance(contact.name.toString(), contact.imageUri, contact.numbersList ?: arrayListOf())
            val fm: FragmentManager = (context as FragmentActivity).supportFragmentManager
            val transaction = fm.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }
    override fun getItemCount(): Int = list.size

}