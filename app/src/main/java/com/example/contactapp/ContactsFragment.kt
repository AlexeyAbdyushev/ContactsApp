package com.example.contactapp

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import javax.inject.Inject

class ContactsFragment : Fragment() {
    var contactName: TextView? = null
    var phone: TextView? = null
    var avatar: ImageView? = null

    lateinit var adapter: ContactAdapter
    lateinit var list: MutableList<Contact>

    var permissionCheck = false
    private val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val recyclerView: RecyclerView? = view?.findViewById(R.id.recyclerView)
        contactName = view?.findViewById(R.id.name)
        phone = view?.findViewById(R.id.phone_number)
        avatar = view?.findViewById(R.id.avatar)
        adapter = ContactAdapter()
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = adapter
        checkPermission()
        if (permissionCheck) {
            adapter.setContacts(list)
            adapter.notifyDataSetChanged()
        }
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity?.checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(Manifest.permission.READ_CONTACTS),
                PERMISSIONS_REQUEST_READ_CONTACTS
            )
        } else {
            permissionCheck = true
            list = getContacts()
        }
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
                    Toast.makeText(context, "Allow contact permission in settings", Toast.LENGTH_LONG).show()
                } else {
                    list = getContacts()

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.contacts_fragment, container, false)
    }

    private fun getContacts(): MutableList<Contact> {
        // Android version is lesser than 6.0 or the permission is already granted.
        val contactList: MutableList<Contact> = mutableListOf()
        var phoneNumber: String? = null

        //Связываемся с контактными данными и берем с них значения id контакта, имени контакта и его номера:
        val CONTENT_URI: Uri = ContactsContract.Contacts.CONTENT_URI
        val _ID = ContactsContract.Contacts._ID
        val DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME
        val HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER
        val PhoneCONTENT_URI: Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        val NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER
        val contentResolver = activity?.contentResolver
        val cursor: Cursor? = contentResolver?.query(CONTENT_URI, null, null, null, null)

        cursor?.let {
            //Запускаем цикл обработчик для каждого контакта:
            if (cursor.count > 0) {
                //Если значение имени и номера контакта больше 0 (то есть они существуют) выбираем
                //их значения в приложение привязываем с соответствующие поля "Имя" и "Номер":
                while (cursor.moveToNext()) {
                    val contact_id: String = cursor.getString(cursor.getColumnIndex(_ID))
                    val name: String? = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME))
                    val hasPhoneNumber: Int = cursor.getString(
                        cursor.getColumnIndex(
                            HAS_PHONE_NUMBER
                        )
                    ).toInt()
                    val imageUri = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
                    //Получаем имя:
                    if (hasPhoneNumber > 0) {
                        val phoneCursor: Cursor? = contentResolver.query(
                            PhoneCONTENT_URI, null,
                            "$Phone_CONTACT_ID = ?", arrayOf(contact_id), null
                        )

                        //и соответствующий ему номер:
                        phoneCursor?.let {
                            while (phoneCursor.moveToNext()) {
                                phoneNumber = phoneCursor.getString(
                                    phoneCursor.getColumnIndex(
                                        NUMBER
                                    )
                                )
                            }
                        }
                    }
                    contactList.add(Contact(name, phoneNumber, imageUri))
                }
            }
        }
        return contactList
    }
}