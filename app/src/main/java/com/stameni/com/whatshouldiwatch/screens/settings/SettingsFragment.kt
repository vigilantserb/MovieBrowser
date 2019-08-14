package com.stameni.com.whatshouldiwatch.screens.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.stameni.com.whatshouldiwatch.R
import com.stameni.com.whatshouldiwatch.common.baseClasses.BaseFragment
import kotlinx.android.synthetic.main.fragment_settings_new.*
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
            println("switch " + load_images_swithc.isChecked)
            println("prefs " + prefs.getBoolean("loadImage", false))
        }
    }
}