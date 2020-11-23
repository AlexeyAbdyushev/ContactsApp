package com.example.contactapp

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val CONTACT_NAME = "name"
private const val AVATAR_URI = "avatar_uri"
private const val NUMBERS_LIST = "numbers_list"

class ContactInfoFragment : Fragment() {
    private var contact_name: String? = null
    private var avatar_uri: String? = null
    private var numbersList: ArrayList<String> = arrayListOf()
    lateinit var adapter: NumbersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        arguments?.let {
            contact_name = it.getString(CONTACT_NAME)
            avatar_uri = it.getString(AVATAR_URI)
            numbersList = it.getStringArrayList(NUMBERS_LIST) as ArrayList<String>
            (activity as AppCompatActivity).supportActionBar?.title = getString(R.string.contact_info)
            (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.contact_info_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val listView: RecyclerView = view.findViewById(R.id.numbers)
        val contactName: TextView = view.findViewById(R.id.name_info)
        val avatar: ImageView = view.findViewById(R.id.avatar)
        adapter = NumbersAdapter()
        listView.layoutManager = LinearLayoutManager(context)
        listView.adapter = adapter
        adapter.setNumbers(numbersList)
        adapter.notifyDataSetChanged()
        ImageHelper.loadImageByUri(avatar_uri, view, avatar)
        contactName.text = contact_name
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

    companion object {
        @JvmStatic
        fun newInstance(_contactName: String, _avatarUri: String?, _numbersList: ArrayList<String>) =
            ContactInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(CONTACT_NAME, _contactName)
                    putString(AVATAR_URI, _avatarUri)
                    putStringArrayList(NUMBERS_LIST, _numbersList)
                }
            }
    }
}