package com.stameni.com.whatshouldiwatch.screens.settings

import android.Manifest
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import com.karumi.dexter.listener.single.PermissionListener
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.baseClasses.BaseFragment
import com.stameni.com.whatshouldiwatch.common.libraries.CSVWriter
import com.stameni.com.whatshouldiwatch.data.room.MovieDatabase
import com.stameni.com.whatshouldiwatch.screens.settings.about.AboutUsActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_settings_new.*
import timber.log.Timber
import java.io.File
import java.io.FileWriter
import java.io.IOException
import javax.inject.Inject

class SettingsFragment : BaseFragment() {

    @Inject
    lateinit var prefs: SharedPreferences

    @Inject
    lateinit var movieDatabase: MovieDatabase

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

        save_your_lists_placeholder.setOnClickListener {

            Dexter.withActivity(activity)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener{
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        createCsvFile()
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?
                    ) {
                        token!!.continuePermissionRequest()
                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {
                        DialogOnDeniedPermissionListener.Builder
                            .withContext(context)
                            .withTitle("Camera permission")
                            .withMessage("Camera permission is needed to take pictures of your cat")
                            .withButtonText(android.R.string.ok)
                            .withIcon(R.mipmap.ic_new_launcer)
                            .build()
                    }

                }).check()
        }
    }

    private fun createCsvFile(): Disposable {
        val exportDir = File(Environment.getExternalStorageDirectory().path)
        if (!exportDir.exists()) {
            exportDir.mkdirs()
        }

        val file = File(exportDir, "Movies.csv")
        if(!file.exists()){
            try {
                file.createNewFile()
            }catch (e: IOException){
                Timber.e(e)
            }
        }
        val csvWrite = CSVWriter(FileWriter(file))
        val column = arrayOf("movieTitle", "releaseDate", "movieGenres", "movieImageUrl", "movieId", "listType")
        csvWrite.writeNext(column)

        return movieDatabase.movieDao()
            .getMovies()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ movies ->
                for (i in movies.indices) {
                    val mySecondStringArray =
                        arrayOf(
                            movies[i].movieTitle!!,
                            movies[i].releaseDate!!,
                            movies[i].movieGenres!!,
                            movies[i].movieImageUrl!!,
                            movies[i].movieId.toString(),
                            movies[i].listType!!
                        )
                    csvWrite.writeNext(mySecondStringArray)
                }
                csvWrite.close()
            }, {
                Timber.e(it)
            })
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