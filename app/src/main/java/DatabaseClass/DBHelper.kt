package DatabaseClass

import ModelClass.TransModel
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(
    context: Context?

) : SQLiteOpenHelper(context, "DataAdd", null, 1) {

    var TABLE_NAME = "Trans"
    var ID = "id"
    var AMOUNT = "amount"
    var CATEGORY = "category"
    var NOTES = "notes"
    var ISEXPENSE = "isexpense"

    override fun onCreate(p0: SQLiteDatabase?) {

        var que =
            "CREATE TABLE $TABLE_NAME($ID INTEGER PRIMARY KEY AUTOINCREMENT,$AMOUNT INTEGER, $CATEGORY TEXT, $NOTES TEXT,$ISEXPENSE INTEGER)"

        p0?.execSQL(que)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun addData(transModel: TransModel) {

        var db = writableDatabase
        var values = ContentValues().apply {
            transModel.apply {
                put(AMOUNT, amount)
                put(CATEGORY, category)
                put(NOTES, notes)
                put(ISEXPENSE, isexpense)
            }
        }

        db.insert(TABLE_NAME, null, values)

    }

    fun getTransaction(): ArrayList<TransModel> {
        var translist = ArrayList<TransModel>()
        var db = readableDatabase
        var que = "SELECT * FROM $TABLE_NAME"
        var cursor = db.rawQuery(que, null)
        cursor.moveToFirst()

        for (i in 0..cursor.count - 1) {
            var id = cursor.getInt(0)
            var amount = cursor.getInt(1)
            var category = cursor.getString(2)
            var notes = cursor.getString(3)
            var isexpense = cursor.getInt(4)

            var model = TransModel(id, amount, category, notes, isexpense)

            translist.add(model)
            cursor.moveToNext()

        }
        return translist
    }

    fun updateTrans(transModel: TransModel){

        var db = writableDatabase
        var values = ContentValues().apply {

            transModel.apply {

                put(AMOUNT, amount)
                put(CATEGORY, category)
                put(NOTES, notes)
                put(ISEXPENSE, isexpense)
            }
        }
        db.update(TABLE_NAME,values,"id=${transModel.id}",null)
    }

}