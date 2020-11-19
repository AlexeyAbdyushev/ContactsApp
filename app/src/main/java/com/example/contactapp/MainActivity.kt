
package com.example.contactapp

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    var contactName: TextView? = null
    var phone: TextView? = null
    var avatar: ImageView? = null
    private val PERMISSIONS_REQUEST_READ_CONTACTS = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        contactName = findViewById(R.id.name)
        phone = findViewById(R.id.phone_number)
        avatar = findViewById(R.id.avatar)
        getContacts(recyclerView)
    }


    //Описываем метод:
    private fun getContacts(recyclerView: RecyclerView) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), PERMISSIONS_REQUEST_READ_CONTACTS)
            getContacts(recyclerView)
        } else {
            // Android version is lesser than 6.0 or the permission is already granted.
            val contactList: MutableList<Contact>? = mutableListOf()
            var phoneNumber: String? = null

            //Связываемся с контактными данными и берем с них значения id контакта, имени контакта и его номера:
            val CONTENT_URI: Uri = ContactsContract.Contacts.CONTENT_URI
            val _ID = ContactsContract.Contacts._ID
            val DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME
            val HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER
            val PhoneCONTENT_URI: Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
            val Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
            val NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER
            val contentResolver = contentResolver
            val cursor: Cursor? = contentResolver.query(CONTENT_URI, null, null, null, null)

            cursor?.let {
                //Запускаем цикл обработчик для каждого контакта:
                if (cursor.count > 0) {
                    //Если значение имени и номера контакта больше 0 (то есть они существуют) выбираем
                    //их значения в приложение привязываем с соответствующие поля "Имя" и "Номер":
                    while (cursor.moveToNext()) {
                        val contact_id: String = cursor.getString(cursor.getColumnIndex(_ID))
                        val name: String? = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME))
                        val hasPhoneNumber: Int = cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)).toInt()
                        val imageUri = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.PHOTO_URI))
                        //Получаем имя:
                        if (hasPhoneNumber > 0) {
                            val phoneCursor: Cursor? = contentResolver.query(PhoneCONTENT_URI, null,
                                    "$Phone_CONTACT_ID = ?", arrayOf(contact_id), null)

                            //и соответствующий ему номер:
                            phoneCursor?.let {
                                while (phoneCursor.moveToNext()) {
                                    phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER))
                                }
                            }
                        }
                        contactList?.add(Contact(name, phoneNumber, imageUri))
                    }
                }
            }
            if (contactList != null) {
                recyclerView.adapter = ContactAdapter(contactList)
            }
        }
    }
}