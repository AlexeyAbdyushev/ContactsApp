package com.example.contactapp

import androidx.lifecycle.ViewModel

class ContactsViewModel: ViewModel() {
    private var contactList: MutableList<Contact> = mutableListOf()
    private val contactService: ContactsService = ContactsService()
    fun getContactById(id: Int): Contact {
        return contactList[id]
    }

    fun getContacts(): MutableList<Contact> {
        contactList = contactService.getContacts()
        return contactList
    }
}