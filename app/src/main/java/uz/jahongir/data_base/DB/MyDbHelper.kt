package uz.jahongir.data_base.DB

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import uz.jahongir.data_base.DB.MyConst.DB_NAME
import uz.jahongir.data_base.DB.MyConst.ID
import uz.jahongir.data_base.DB.MyConst.NAME
import uz.jahongir.data_base.DB.MyConst.NUMBER
import uz.jahongir.data_base.DB.MyConst.TABLE_NAME
import uz.jahongir.data_base.models.MyContact

class MyDbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, 1), MyDbInterface {
    override fun onCreate(db: SQLiteDatabase?) {
        var query = "create table $TABLE_NAME ($ID integer not null primary key autoincrement unique, $NAME text not null, $NUMBER text not null)"
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    override fun addContact(myContact: MyContact) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME, myContact.name)
        contentValues.put(NUMBER, myContact.number)
        database.insert(TABLE_NAME, null, contentValues)
        database.close()
    }

    override fun getAllContact(): List<MyContact> {
        val list = ArrayList<MyContact>()
        val database = readableDatabase
        val query = "select * from $TABLE_NAME"
        val cursor = database.rawQuery(query, null)

        if (cursor.moveToFirst()) {
            do {
                val myContact = MyContact(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2))
                list.add (myContact)
            } while (cursor.moveToNext())
        }
        return list
    }

    override fun longClick(myContact: MyContact) {
    }

    override fun deleteContact(myContact: MyContact) {
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$ID =?", arrayOf(myContact.id.toString()))
        database.close()
    }

    override fun editContact(myContact: MyContact) {
        val database = writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, myContact.id)
        contentValues.put(NAME, myContact.name)
        contentValues.put(NUMBER, myContact.number)
        database.update(TABLE_NAME, contentValues, "$ID = ?", arrayOf(myContact.id.toString()))
    }
}