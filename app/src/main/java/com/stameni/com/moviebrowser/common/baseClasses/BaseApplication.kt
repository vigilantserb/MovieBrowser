package com.stameni.com.moviebrowser.common.baseClasses

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.stameni.com.moviebrowser.di.components.ApplicationComponent
import com.stameni.com.moviebrowser.di.components.DaggerApplicationComponent
import com.stameni.com.moviebrowser.di.modules.ApplicationModule

class BaseApplication: Application() {

    private var mApplicationComponent: ApplicationComponent? = null

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        mApplicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    fun getApplicationComponent(): ApplicationComponent? {
        return mApplicationComponent
    }
}