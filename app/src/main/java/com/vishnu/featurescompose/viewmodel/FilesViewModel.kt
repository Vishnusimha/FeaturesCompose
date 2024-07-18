package com.vishnu.featurescompose.viewmodel

import androidx.lifecycle.ViewModel
import com.vishnu.featurescompose.data.local.FileManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilesViewModel @Inject constructor(fileManager: FileManager) : ViewModel(){
    init {
        val fileName = "samplefile.txt"
        val data = "Hello Thanks for searching here... Have a Nice day!"

        // Saving data to file
        if (fileManager.saveToFile(fileName, data)) {
            println("Data saved to file successfully")
        } else {
            println("Failed to save data to file")
        }

        // Retrieving data from file
        val retrievedData = fileManager.getFileContents(fileName)
        if (retrievedData != null) {
            println("Retrieved data from file: $retrievedData")
        } else {
            println("Failed to retrieve data from file.")
        }
    }
}