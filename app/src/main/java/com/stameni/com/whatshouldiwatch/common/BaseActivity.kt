package com.stameni.com.whatshouldiwatch.common

import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import com.stameni.com.whatshouldiwatch.di.components.ApplicationComponent
import com.stameni.com.whatshouldiwatch.di.components.ControllerComponent
import com.stameni.com.whatshouldiwatch.di.modules.ControllerModule
import com.stameni.com.whatshouldiwatch.di.modules.NetworkModule
import com.stameni.com.whatshouldiwatch.di.modules.ViewModelModule

abstract class BaseActivity : AppCompatActivity() {


    private var mIsInjectorUsed: Boolean = false

    @UiThread
    protected fun getControllerComponent(): ControllerComponent {
        if (mIsInjectorUsed) {
            throw RuntimeException("there is no need to use injector more than once")
        }
        mIsInjectorUsed = true
        return getApplicationComponent()!!
            .newControllerComponent(ControllerModule(this), ViewModelModule(), NetworkModule())

    }

    private fun getApplicationComponent(): ApplicationComponent? {
        return (application as BaseApplication).getApplicationComponent()
    }
}