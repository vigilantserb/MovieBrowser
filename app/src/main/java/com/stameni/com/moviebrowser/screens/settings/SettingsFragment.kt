package com.stameni.com.moviebrowser.screens.settings

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SwitchCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.Constants
import com.stameni.com.moviebrowser.common.ViewModelFactory
import com.stameni.com.moviebrowser.common.baseClasses.BaseFragment
import com.stameni.com.moviebrowser.data.room.MovieDatabase
import com.stameni.com.moviebrowser.databinding.FragmentSettingsNewBinding
import com.stameni.com.moviebrowser.screens.settings.about.AboutUsActivity
import javax.inject.Inject

class SettingsFragment : BaseFragment<FragmentSettingsNewBinding>(FragmentSettingsNewBinding::inflate) {

    @Inject
    lateinit var prefs: SharedPreferences

    @Inject
    lateinit var movieDatabase: MovieDatabase

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: SettingsViewModel

    private lateinit var load_images_swithc: SwitchCompat
    private lateinit var image_cache_placeholder: TextView
    private lateinit var watched_placeholder: TextView
    private lateinit var to_watch_placeholder: TextView
    private lateinit var github_logo: ImageView
    private lateinit var save_your_lists_placeholder: TextView
    private lateinit var import_backup_placeholder: TextView

    override fun setupViews() {
        load_images_swithc = binding.loadImagesSwithc
        image_cache_placeholder = binding.imageCachePlaceholder
        watched_placeholder = binding.watchedPlaceholder
        to_watch_placeholder = binding.toWatchPlaceholder
        github_logo = binding.githubLogo
        save_your_lists_placeholder = binding.saveYourListsPlaceholder
        import_backup_placeholder = binding.importBackupPlaceholder
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controllerComponent.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SettingsViewModel::class.java)

        viewModel.csvFileWriteSuccessful.observe(this, Observer {
            if (it == Constants.SUCCESS) {
                Toast.makeText(activity, "Movies successfully written to CSV file", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.csvReadFileSuccessful.observe(this, Observer {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
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

        save_your_lists_placeholder.setOnClickListener {
            viewModel.writeMoviesToCsvFile()
        }

        import_backup_placeholder.setOnClickListener {
            viewModel.importMovieListFromCsv()
        }

        github_logo.setOnClickListener {
            val urlString = "https://github.com/vigilantserb/MovieBrowser"
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(urlString))
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.setPackage("com.android.chrome")
            try {
                context?.startActivity(intent)
            } catch (ex: ActivityNotFoundException) {
                // Chrome browser presumably not installed so allow user to choose instead
                intent.setPackage(null)
                context?.startActivity(intent)
            }

        }
    }
}