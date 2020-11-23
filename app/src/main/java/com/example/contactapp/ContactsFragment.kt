package com.example.contactapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.ViewModelProviders

class ContactsFragment : Fragment() {

    private val contactViewModel by lazy {
        ViewModelProviders.of(this).get(ContactsViewModel::class.java)
    }

    lateinit var adapter: ContactsAdapter
    lateinit var contactsList: MutableList<Contact>
    var permissionCheck = false
    private val PERMISSIONS_REQUEST_READ_CONTACTS = 100

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity?.checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS
            )
        } else {
            permissionCheck = true
            contactsList = contactViewModel.getContacts()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val recyclerView: RecyclerView? = view.findViewById(R.id.recyclerView)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        adapter = ContactsAdapter()
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter
        context?.let { adapter.setContext(it) }
        checkPermission()
        if (permissionCheck) {
            adapter.setContacts(contactsList)
            adapter.notifyDataSetChanged()
        }
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSIONS_REQUEST_READ_CONTACTS -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    permissionCheck = false
                    Toast.makeText(
                        context,
                        "Allow contact permission in settings",
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    contactsList = contactViewModel.getContacts()
                    permissionCheck = true
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.contacts_fragment, container, false)
    }
}