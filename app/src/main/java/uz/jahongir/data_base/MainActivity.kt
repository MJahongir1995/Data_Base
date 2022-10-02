package uz.jahongir.data_base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import uz.jahongir.data_base.DB.MyDbHelper
import uz.jahongir.data_base.adapter.MyRvAdapter
import uz.jahongir.data_base.adapter.RvEvent
import uz.jahongir.data_base.databinding.ActivityMainBinding
import uz.jahongir.data_base.databinding.ItemDialogBinding
import uz.jahongir.data_base.models.MyContact

class MainActivity : AppCompatActivity(), RvEvent {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myDbHelper: MyDbHelper
    private lateinit var myRvAdapter: MyRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDbHelper = MyDbHelper(this)
        myRvAdapter = MyRvAdapter(myDbHelper.getAllContact(),this)

        itemTouchHelper()

        binding.apply {
            rv.adapter = myRvAdapter

            btnAdd.setOnClickListener {
                val dialog = AlertDialog.Builder(this@MainActivity).create()
                val itemDialog = ItemDialogBinding.inflate(layoutInflater)
                dialog.setView(itemDialog.root)

                itemDialog.apply {
                    btnSave.setOnClickListener {
                        val myContact = MyContact(
                            edtName.text.toString().trim(),
                            edtNumber.text.toString().trim()
                        )
                        myDbHelper.addContact(myContact)
                        Toast.makeText(this@MainActivity, "Saved", Toast.LENGTH_SHORT).show()
                        dialog.cancel()
                        myRvAdapter.list = myDbHelper.getAllContact()
                        myRvAdapter.notifyDataSetChanged()
                    }
                }
                dialog.show()
            }
        }
    }

    private fun itemTouchHelper() {

    }

    override fun menuClick(myContact: MyContact,view: ImageView) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.pop_up_menu)

        popupMenu.setOnMenuItemClickListener {

            when(it.itemId){
                R.id.delete->{
                    myDbHelper.deleteContact(myContact)
                    myRvAdapter.list = myDbHelper.getAllContact()
                    myRvAdapter.notifyDataSetChanged()
                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show()
                }
                R.id.edit->{
                    val dialog = AlertDialog.Builder(this).create()
                    val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
                    dialog.setView(itemDialogBinding.root)
                    dialog.show()

                    itemDialogBinding.edtName.setText(myContact.name)
                    itemDialogBinding.edtNumber.setText(myContact.number)

                    itemDialogBinding.btnSave.setOnClickListener {

                        myContact.name = itemDialogBinding.edtName.text.toString().trim()
                        myContact.number = itemDialogBinding.edtNumber.text.toString().trim()

                        myDbHelper.editContact(myContact)
                        myRvAdapter.list = myDbHelper.getAllContact()
                        myRvAdapter.notifyDataSetChanged()
                        dialog.dismiss()
                        Toast.makeText(this, "Edited", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            true
        }
        popupMenu.show()
    }
}