package com.example.contactapp

import android.app.Service
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.IBinder
import android.provider.ContactsContract

class ContactsService {
    companion object {
        private lateinit var context: Context
        fun setContext(con: Context) {
            context=con
        }
    }
    fun getContacts(): MutableList<Contact> {
        val contactList: MutableList<Contact> = mutableListOf()
        // Android version is lesser than 6.0 or the permission is already granted.
        var phoneNumber: String? = null
        //Связываемся с контактными данными и берем с них значения id контакта, имени контакта и его номера:
        val CONTENT_URI: Uri = ContactsContract.Contacts.CONTENT_URI
        val _ID = ContactsContract.Contacts._ID
        val DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME
        val HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER
        val PhoneCONTENT_URI: Uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        val NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER
        val contentResolver = context.contentResolver
        val cursor: Cursor? = contentResolver?.query(CONTENT_URI, null, null, null, null)

        cursor?.let {
            //Запускаем цикл обработчик для каждого контакта:
            if (cursor.count > 0) {
                //Если значение имени и номера контакта больше 0 (то есть они существуют) выбираем
                //их значения в приложение привязываем с соответствующие поля "Имя" и "Номер":
                while (cursor.moveToNext()) {
                    val numbersOfContact = arrayListOf<String>()
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
                            phoneCursor.moveToFirst()
                            phoneNumber = phoneCursor.getString(
                                    phoneCursor.getColumnIndex(
                                            NUMBER
                                    )
                            )
                            numbersOfContact.add(phoneNumber ?: "None")
                            while (phoneCursor.moveToNext()) {
                                numbersOfContact.add(phoneCursor.getString(
                                        phoneCursor.getColumnIndex(
                                                NUMBER
                                        )
                                ) ?: "None")
                            }
                        }
                        contactList.add(Contact(name, phoneNumber, imageUri, numbersOfContact))
                    }
                }
            }
        }
        return contactList
    }
}