package com.vishnu.featurescompose.viewmodel

import androidx.lifecycle.ViewModel
import com.vishnu.featurescompose.data.local.DatabaseHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SQLdbViewModel @Inject constructor(private val dbHelper: DatabaseHelper) : ViewModel() {
    init {
        // Inserting data
        val insertedRowId = dbHelper.insertData("Hello, Vishnu!", 42)
        if (insertedRowId != -1L) {
            println("Data inserted successfully with ID: $insertedRowId")
        } else {
            println("Failed to insert data")
        }

        println("dbHelper.getAllData()" + dbHelper.getAllData())
    }
}