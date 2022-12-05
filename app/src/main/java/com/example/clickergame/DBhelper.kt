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

    val query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME+ " (" +
            CLICK_POWER + " INT, "  + TOTAL + " INT"+" )"

    val query2 = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME2 + " (" +
            ATTACK_POWER + " INT, " + PRICE + " INT"+ ")"

    val insert = "INSERT INTO click_table (click_power,total) VALUES (1,0)"
    val insert2 = "INSERT INTO attack_table (attack_power,price) VALUES (5,10000)"

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL(query);
        db.execSQL(query2);
        db.execSQL(insert);
        db.execSQL(insert2);
        println(query)
        println(query2)

    }
    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME+"'");
        db?.execSQL("DROP TABLE IF EXISTS '" + TABLE_NAME2 +"'");
        onCreate(db!!)
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

    fun getPrice(): Cursor{
        val db = this.readableDatabase

        return db.rawQuery("SELECT $PRICE FROM $TABLE_NAME2", null)

    }

    fun getAttack(): Cursor{
        val db = this.readableDatabase

        return db.rawQuery("SELECT $ATTACK_POWER FROM $TABLE_NAME2", null)

    }

    fun updateAttack(attack: Int, price: Int){
        val values = ContentValues()
        values.put(ATTACK_POWER, attack)
        values.put(PRICE, price)
        val db = this.writableDatabase
        db.insert(TABLE_NAME2, null, values)
        db.close()
    }




    companion object {
    private val DATABASE_NAME = "ClickerDB"
    private val DATABASE_VERSION = 1
    val TABLE_NAME = "click_table"
        val CLICK_POWER = "click_power"
        val TOTAL = "total"

        val TABLE_NAME2 = "attack_table"
        val ATTACK_POWER = "attack_power"
        val PRICE = "price"

    }
}



