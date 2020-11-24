package com.example.contactapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactInfoFragment : Fragment() {
    lateinit var adapter: NumbersAdapter

    private val contactInfoViewModel by lazy {
        ViewModelProviders.of(this).get(ContactInfoViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.contact_info)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contact_info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listView: RecyclerView = view.findViewById(R.id.numbers)
        val contactName: TextView = view.findViewById(R.id.name_info)
        val avatar: ImageView = view.findViewById(R.id.avatar)
        val bundle = this.arguments
        contactInfoViewModel.setContact(bundle?.getParcelable("Contact"))
        adapter = NumbersAdapter()
        listView.layoutManager = LinearLayoutManager(context)
        listView.adapter = adapter
        adapter.setNumbers(contactInfoViewModel.numbersList)
        adapter.notifyDataSetChanged()
        ImageHelper.loadImageByUri(contactInfoViewModel.imageUri, view, avatar)
        contactName.text = contactInfoViewModel.name
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}