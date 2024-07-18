package com.vishnu.featurescompose.data.local

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import javax.inject.Inject

class DatabaseHelper @Inject constructor(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "SampleDatabase"
        private const val TABLE_NAME = "SampleTable"
        private const val COLUMN_STRING = "sampleString"
        private const val COLUMN_INT = "sampleInt"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_STRING TEXT,"
                + "$COLUMN_INT INTEGER)"
        )
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(sampleString: String, sampleInt: Int): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_STRING, sampleString)
        contentValues.put(COLUMN_INT, sampleInt)
        return db.insert(TABLE_NAME, null, contentValues)
    }

    @SuppressLint("Range")
    fun getAllData(): ArrayList<Pair<String, Int>> {
        val dataList = ArrayList<Pair<String, Int>>()
        val db = this.readableDatabase
        val cursor: Cursor? = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        cursor?.use {
            if (it.moveToFirst()) {
                do {
                    val sampleString = it.getString(it.getColumnIndex(COLUMN_STRING))
                    val sampleInt = it.getInt(it.getColumnIndex(COLUMN_INT))
                    dataList.add(Pair(sampleString, sampleInt))
                } while (it.moveToNext())
            }
        }
        cursor?.close() // Close the cursor to avoid memory leaks
        return dataList
    }

}

