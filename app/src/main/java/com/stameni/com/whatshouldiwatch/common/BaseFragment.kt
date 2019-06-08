package com.stameni.com.whatshouldiwatch.common

import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import com.stameni.com.whatshouldiwatch.di.components.ApplicationComponent
import com.stameni.com.whatshouldiwatch.di.components.ControllerComponent
import com.stameni.com.whatshouldiwatch.di.modules.ControllerModule
import com.stameni.com.whatshouldiwatch.di.modules.NetworkModule
import com.stameni.com.whatshouldiwatch.di.modules.ViewModelModule

abstract class BaseFragment : Fragment() {

    protected val controllerComponent: ControllerComponent
        @UiThread
        get() {
            return applicationComponent!!
                .newControllerComponent(ControllerModule(activity!!), ViewModelModule(), NetworkModule())

        }

    private val applicationComponent: ApplicationComponent?
        get() = (activity!!.application as BaseApplication).getApplicationComponent()
}