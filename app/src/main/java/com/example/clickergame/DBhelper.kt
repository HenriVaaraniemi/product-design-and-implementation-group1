package com.example.clickergame

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf

class DBhelper (context: Context, factory: SQLiteDatabase.CursorFactory?) :
SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION)
{

    val query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
            CLICK_POWER + " INT DEFAULT 1, "  + TOTAL + " INT DEFAULT 0" +")"

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(query);
        println(query)

    }
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME+"'");
        onCreate(db!!)
    }
    fun addTotal(amount: Int){
        val values = ContentValues()
        values.put(TOTAL, amount)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun addPower(amount2: Int){
        val values = ContentValues()
        values.put(CLICK_POWER, amount2)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun addBoth(total: Int, power: Int){
        val values = ContentValues()
        values.put(TOTAL, total)
        values.put(CLICK_POWER, power)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()

    }

    fun getTotal(): Cursor? {

        val db = this.readableDatabase

        return db.rawQuery("SELECT $TOTAL FROM $TABLE_NAME", null)

        //db.close()
    }

    fun getPower(): Cursor? {

        val db = this.readableDatabase

        return db.rawQuery("SELECT $CLICK_POWER FROM $TABLE_NAME", null)

        //db.close()
    }




    companion object {
    private val DATABASE_NAME = "ClickerDB"
    private val DATABASE_VERSION = 1
    val TABLE_NAME = "click_table"
        val CLICK_POWER = "click_power"
        val TOTAL = "total"

    }
}



