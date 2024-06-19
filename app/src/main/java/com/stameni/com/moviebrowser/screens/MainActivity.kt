package com.stameni.com.moviebrowser.screens

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.stameni.com.moviebrowser.R
import com.stameni.com.moviebrowser.common.baseClasses.BaseActivity
import com.stameni.com.moviebrowser.databinding.MainActivityBinding

class MainActivity : BaseActivity<MainActivityBinding>(MainActivityBinding::inflate) {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        setupBottomNavMenu(navController)
    }

    override fun setupViews() {
        bottomNav = binding.bottomNav
    }

    private fun setupBottomNavMenu(navController: NavController) {
        bottomNav?.let {
            NavigationUI.setupWithNavController(it, navController)
        }
    }
}
