package com.example.agenda.app.helps

import android.content.ContentValues
import android.os.Environment
import android.provider.MediaStore
import com.example.agenda.app.App

object File {

    fun resolvePath(path: List<String>): String {
        return path.joinToString(separator = "/")
    }

    enum class Folder(val folderName: String) {
        BACKUP("Backup"),
    }

    enum class Path(val location: String) {
        DOWNLOADS(
            resolvePath(listOf(Environment.DIRECTORY_DOWNLOADS, Folder.BACKUP.folderName))
        )
    }

    data class Data(
        val name: String,
        val type: String,
        val mimeType: String,
        val path: Path,
        val data: String,
    )

    private fun create(file: Data) {
        val values = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "${file.name}.${file.type}")
            put(MediaStore.MediaColumns.MIME_TYPE, file.mimeType)
            put(MediaStore.MediaColumns.RELATIVE_PATH, file.path.location)
            // Para controlar visibilidade durante a gravação
            put(MediaStore.MediaColumns.IS_PENDING, 1)
        }
        val uri = App.UI.context.contentResolver.insert(
            MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY), values
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
                type = "csv",
                mimeType = "text/csv",
                path = data.path,
                data = data.data
            )
            File.create(fileData)
        }
    }
}