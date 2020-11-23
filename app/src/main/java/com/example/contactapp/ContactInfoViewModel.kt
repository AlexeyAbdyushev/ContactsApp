package com.example.contactapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class ContactInfoViewModel (application: Application): AndroidViewModel(application){

    var _contact_name: String? = null
    var avatar_uri: String? = null
    var numbersList: ArrayList<String> = arrayListOf()
}