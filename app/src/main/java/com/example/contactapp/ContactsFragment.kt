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
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ContactsFragment : Fragment() {

    private val contactViewModel by lazy {
        ViewModelProviders.of(this).get(ContactsViewModel::class.java)
    }

    lateinit var adapter: ContactsAdapter
    lateinit var contactsList: MutableList<Contact>
    var permissionCheck = false
    private val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    val bundle = Bundle()
    var recyclerView: RecyclerView? = null
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
        recyclerView = view.findViewById(R.id.recyclerView)
        (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.app_name)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        adapter = ContactsAdapter { id ->
            bundle.putParcelable("Contact", contactViewModel.getContactById(id))
            val fragment = ContactInfoFragment()
            fragment.arguments = bundle
            val fm: FragmentManager = (context as FragmentActivity).supportFragmentManager
            val transaction = fm.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
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
                        getString(R.string.allow_permissions),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    contactsList = contactViewModel.getContacts()
                    adapter.setContacts(contactsList)
                    adapter.notifyDataSetChanged()
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

    override fun onResume() {

        super.onResume()
    }
}