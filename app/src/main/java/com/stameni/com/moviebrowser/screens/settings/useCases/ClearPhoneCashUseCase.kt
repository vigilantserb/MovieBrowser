package com.stameni.com.moviebrowser.screens.settings.useCases

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import java.io.File

class ClearPhoneCashUseCase(
    private val context: Context
){
    fun handleImageCache() {
        try {
            val dir = context.cacheDir
            val dirSize = dirSize(dir)
            promptToDeleteDir(dirSize, dir)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun promptToDeleteDir(dirSize: Long, dir: File) {
        var cacheSizeMb = 0
        if (dirSize > 0) cacheSizeMb = (dirSize / 1024 / 1024).toInt()
        val dialogClickListener = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> {
                    deleteDir(dir)
                    Toast.makeText(context, "$cacheSizeMb MBs of memory freed", Toast.LENGTH_SHORT).show()
                }

                DialogInterface.BUTTON_NEGATIVE -> {
                    println("Not deleted")
                }
            }
        }

        val builder = AlertDialog.Builder(context)
        builder.setMessage("Are you sure you want to delete $cacheSizeMb MB of data?")
            .setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).show()
    }

    private fun dirSize(dir: File): Long {
        if (dir.exists()) {
            var result: Long = 0
            val fileList = dir.listFiles()
            for (i in fileList.indices) {
                result += if (fileList[i].isDirectory) {
                    dirSize(fileList[i])
                } else {

                    fileList[i].length()
                }
            }
            return result
        }
        return 0
    }

    private fun deleteDir(dir: File?): Boolean {
        if (dir != null && dir.isDirectory) {
            val children = dir.list()
            for (i in children.indices) {
                val success = deleteDir(File(dir, children[i]))
                if (!success) {
                    return false
                }
            }
            return dir.delete()
        } else return if (dir != null && dir.isFile) {
            dir.delete()
        } else {
            false
        }
    }
}