package com.example.agenda.app.helps

import android.content.ContentUris
import android.content.ContentValues
import android.media.MediaScannerConnection
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import com.example.agenda.app.App
import com.example.agenda.ui.screens.Banks
import io.objectbox.annotation.Entity

object File {
    fun resolvePath(path: List<String>): String {
        return path.joinToString(separator = "/") + "/"
    }

    enum class Folder(val folderName: String) {
        BACKUP("Backup"),
    }

    enum class Filename(val filename: String) {
        BANK_BACKUP("bank-backup.csv"),
        EVENT_BACKUP("event-backup.csv"),
        TRANSACTION_BACKUP("transaction-backup.csv"),
        GOAL_BACKUP("goal-backup.csv"),
        TRANSACTION_CATEGORY_BACKUP("transaction-category-backup.csv")
    }

    enum class Path(val location: String) {
        BACKUP(
            resolvePath(listOf(Environment.DIRECTORY_DOWNLOADS, Folder.BACKUP.folderName))
        )
    }

    data class Data(
        val name: String,
        val mimeType: String,
        val path: Path,
        val data: String,
    )
    fun deleteFolder(relativePath: Path): Int {
        val uri = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
        val selection = "${MediaStore.MediaColumns.RELATIVE_PATH} = ?"
        val selectionArgs = arrayOf(relativePath.location)
        val deleted = App.UI.context.contentResolver.delete(uri, selection, selectionArgs)
        return deleted
    }
    fun read(filename: String): String? {
        val uri = MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL)
        val projection = arrayOf(
            MediaStore.MediaColumns._ID,
        )
        val selection = "${MediaStore.MediaColumns.DISPLAY_NAME} = ?"
        val selectionArgs = arrayOf(filename)
        App.UI.context.contentResolver.query(uri, projection, selection, selectionArgs, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val id = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.MediaColumns._ID))
                val contentUri = ContentUris.withAppendedId(uri, id)
                return App.UI.context.contentResolver.openInputStream(contentUri)
                    ?.bufferedReader()
                    ?.readText()
            }
        }
        return null
    }

    private fun create(file: Data) {
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, file.name)
            put(MediaStore.MediaColumns.MIME_TYPE, file.mimeType)
            put(MediaStore.MediaColumns.RELATIVE_PATH, file.path.location)
            put(MediaStore.MediaColumns.IS_PENDING, 1)
        }
        val uri = App.UI.context.contentResolver.insert(
            MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY), values
        )

        uri?.let {
            App.UI.context.contentResolver.openOutputStream(it).use { os ->
                os?.write(file.data.toByteArray())
            }
            values.clear()
            values.put(MediaStore.MediaColumns.IS_PENDING, 0)
            App.UI.context.contentResolver.update(uri, values, null, null)
        }
    }

    object CSV {
        data class Data(
            val name: String,
            val path: Path,
            val data: String,
        )

        private fun line(data: List<String>): String {
            return data.joinToString(separator = ",")
        }

        private fun entityToLine(entity: Any, value: Boolean = false): String {
            val result = if (value) 1 else 0
            val newList = mutableListOf<String>()
            val s = entity.toString().replace(")", "").split("(")[1].replace(",", "=").split("=")
            for (i in s.indices) {
                if (i % 2 == result) {
                    if (value) {
                        newList.add(s[i])
                    } else {
                        newList.add(s[i].replace(" ", ""))
                    }
                }
            }
            return line(newList)
        }

        fun entityListToString(list: List<Any>): String {
            val result = mutableListOf<String>()
            for (i in list.indices) {
                if (i == 0) {
                    result.add(entityToLine(list[i]))
                }
                result.add(entityToLine(list[i], true))
            }
            return result.joinToString("\n")
        }

        fun create(data: Data) {
            val fileData = File.Data(
                name = data.name,
                mimeType = "text/csv",
                path = data.path,
                data = data.data
            )
            File.create(fileData)
        }

        data class CSVFile(
            val header: List<String>,
            val data: List<List<String>>,
        )
        fun read(filename: String, path: Path): CSVFile?{
            val file = File.read(filename) ?: return null
            val list = file.split("\n")
            return CSVFile(
                header = list[0].split(","),
                data = list.subList(1, list.size).map { it.split(",") }
            )
        }
    }
}