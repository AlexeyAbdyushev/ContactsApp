package com.example.contactapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
private const val ARG_PARAM3 = "param3"
/**
 * A simple [Fragment] subclass.
 * Use the [ContactInfoFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactInfoFragment : Fragment() {
    private var contact_name: String? = null
    private var avatar_uri: String? = null
    private var number: String = ""
    private var numbersList: ArrayList<String> = arrayListOf()
    lateinit var adapter: NumbersAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            contact_name = it.getString(ARG_PARAM1)
            avatar_uri = it.getString(ARG_PARAM2)
            numbersList = it.getStringArrayList(ARG_PARAM3) as ArrayList<String>
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact_info, container, false)
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
        avatar.setImageBitmap(null)
        if (avatar_uri != null) {
            Glide
                .with(view)
                .load(avatar_uri)
                .into(avatar)
        } else {
            Glide
                .with(view)
                .load(R.drawable.ic_user)
                .into(avatar)
        }
        contactName.text = contact_name
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String?, param3: ArrayList<String>) =
            ContactInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                    putStringArrayList(ARG_PARAM3, param3)
                }
            }
    }
}