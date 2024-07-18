package com.vishnu.featurescompose.data.local

import android.content.Context
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class FileManager @Inject constructor(private val context: Context) {
    fun saveToFile(fileName: String, data: String): Boolean {
        return try {
            val fileOutputStream: FileOutputStream =
                context.openFileOutput(fileName, Context.MODE_PRIVATE)
            fileOutputStream.write(data.toByteArray())
            fileOutputStream.close()
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getFileContents(fileName: String): String? {
        return try {
            val file = File(context.filesDir, fileName)
            val fileContents = file.readText()
            fileContents
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun deleteFile(fileName: String): Boolean {
        return try {
            val file = File(context.filesDir, fileName)
            file.delete()
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun doesFileExist(fileName: String): Boolean {
        return File(context.filesDir, fileName).exists()
    }

    fun listFiles(): Array<String> {
        return context.fileList()
    }

    fun moveFile(oldFileName: String, newFileName: String): Boolean {
        return try {
            val oldFile = File(context.filesDir, oldFileName)
            val newFile = File(context.filesDir, newFileName)
            oldFile.renameTo(newFile)
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun getFileSize(fileName: String): Long {
        val file = File(context.filesDir, fileName)
        return file.length()
    }

    fun getFileMetadata(fileName: String): FileMetadata? {
        return try {
            val file = File(context.filesDir, fileName)
            FileMetadata(
                lastModified = file.lastModified(),
                isDirectory = file.isDirectory,
                size = file.length(),
                permissions = file.canRead() to file.canWrite()
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    data class FileMetadata(
        val lastModified: Long,
        val isDirectory: Boolean,
        val size: Long,
        val permissions: Pair<Boolean, Boolean>
    )
}