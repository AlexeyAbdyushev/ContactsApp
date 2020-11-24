package com.example.contactapp

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel

class ContactInfoViewModel (application: Application): AndroidViewModel(application){
    var name: String? = null
    var phone: String? = null
    var numbersList:ArrayList<String> = arrayListOf()
    var imageUri: String? = null
   fun setContact(contact:Contact?) {
       name = contact?.name
       phone = contact?.phone
       numbersList = contact?.numbersList ?: arrayListOf()
       imageUri = contact?.imageUri
   }
}