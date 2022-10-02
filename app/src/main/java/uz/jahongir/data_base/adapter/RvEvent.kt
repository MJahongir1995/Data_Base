package uz.jahongir.data_base.adapter

import android.widget.ImageView
import uz.jahongir.data_base.models.MyContact

interface RvEvent {
    fun menuClick(myContact: MyContact,view: ImageView)
}