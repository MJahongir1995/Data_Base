package uz.jahongir.data_base.DB

import android.icu.text.Transliterator
import uz.jahongir.data_base.models.MyContact

interface MyDbInterface {
    fun addContact(myContact: MyContact)
    fun getAllContact():List<MyContact>
    fun longClick(myContact:MyContact)
    fun deleteContact(myContact: MyContact)
    fun editContact(myContact: MyContact)
}