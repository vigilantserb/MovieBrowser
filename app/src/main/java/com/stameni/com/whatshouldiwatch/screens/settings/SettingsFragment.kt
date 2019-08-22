package com.stameni.com.whatshouldiwatch.screens.settings

import android.Manifest
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener
import com.karumi.dexter.listener.single.PermissionListener
import com.opencsv.CSVReader
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.Constants
import com.stameni.com.whatshouldiwatch.common.ViewModelFactory
import com.stameni.com.whatshouldiwatch.common.baseClasses.BaseFragment
import com.stameni.com.whatshouldiwatch.data.room.MovieDatabase
import com.stameni.com.whatshouldiwatch.screens.settings.about.AboutUsActivity
import kotlinx.android.synthetic.main.fragment_settings_new.*
import java.io.FileReader
import javax.inject.Inject

class SettingsFragment : BaseFragment() {

    @Inject
    lateinit var prefs: SharedPreferences

    @Inject
    lateinit var movieDatabase: MovieDatabase

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings_new, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java)

        viewModel.writeCsvFilePermission.observe(this, Observer {
                if(it == Constants.SUCCESS){
                    viewModel.writeMoviesToCsvFile()
                }
            }
        )

        viewModel.csvFileWriteSuccessful.observe(this, Observer {
            if(it == Constants.SUCCESS){
                Toast.makeText(activity, "Movies successfully written to CSV file", Toast.LENGTH_SHORT).show()
            }
        })

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
            viewModel.clearPhoneCash()
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

        val array = ArrayList<String>()
        array.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)

        save_your_lists_placeholder.setOnClickListener {
            viewModel.requestPermissions(array, "No permission allowed - CSV file not created")
        }

        import_backup_placeholder.setOnClickListener {

            Dexter.withActivity(activity)
                .withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        try {
                            val reader =
                                FileReader(Environment.getExternalStorageDirectory().toString() + "/" + "Movies.csv")
                            val csvReader = CSVReader(reader)

                            var nextRecord = csvReader.readNext()
                            while (nextRecord != null) {
                                nextRecord = csvReader.readNext()
                            }
                        } catch (e: java.lang.Exception) {

                        }
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
                            .withTitle("File reading")
                            .withMessage("This permission is needed to read the CSV file")
                            .withButtonText(android.R.string.ok)
                            .withIcon(R.mipmap.ic_new_launcer)
                            .build()
                    }
                }).check()
        }
    }
}