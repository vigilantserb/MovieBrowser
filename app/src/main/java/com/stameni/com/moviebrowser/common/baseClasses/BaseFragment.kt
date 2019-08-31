package com.stameni.com.moviebrowser.common.baseClasses

import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import com.stameni.com.moviebrowser.di.components.ApplicationComponent
import com.stameni.com.moviebrowser.di.components.ControllerComponent
import com.stameni.com.moviebrowser.di.modules.ControllerModule
import com.stameni.com.moviebrowser.di.modules.NetworkModule
import com.stameni.com.moviebrowser.di.modules.ViewModelModule

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