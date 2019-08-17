package com.stameni.com.whatshouldiwatch.screens.settings

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.baseClasses.BaseFragment
import com.stameni.com.whatshouldiwatch.screens.settings.about.AboutUsActivity
import kotlinx.android.synthetic.main.fragment_settings_new.*
import java.io.File
import javax.inject.Inject

class SettingsFragment : BaseFragment() {

    @Inject
    lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings_new, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerComponent.inject(this)

        var loadImage = prefs.getBoolean("loadImage", true)
        load_images_swithc.isChecked = loadImage

        load_images_swithc.setOnClickListener {
            if (loadImage != load_images_swithc.isChecked) {
                with(prefs.edit()) {
                    loadImage = load_images_swithc.isChecked
                    putBoolean("loadImage", load_images_swithc.isChecked)
                    apply()
                }
            }
        }

        image_cache_placeholder.setOnClickListener {
            handleImageCache()
        }

        watched_placeholder.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            val data = Uri.parse("mailto:mihajlo07@gmail.com?subject=[What Should I Watch?] Help" + "" + "&body=" + "")
            intent.data = data
            startActivity(intent)
        }

        to_watch_placeholder.setOnClickListener {
            startActivity(Intent(context, AboutUsActivity::class.java))
        }
    }

    private fun handleImageCache() {
        try {
            val dir = context!!.cacheDir
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