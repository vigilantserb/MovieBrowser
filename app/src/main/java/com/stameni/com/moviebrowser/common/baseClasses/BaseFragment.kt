package com.stameni.com.moviebrowser.common.baseClasses

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.UiThread
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.stameni.com.moviebrowser.di.components.ApplicationComponent
import com.stameni.com.moviebrowser.di.components.ControllerComponent
import com.stameni.com.moviebrowser.di.modules.ControllerModule
import com.stameni.com.moviebrowser.di.modules.NetworkModule
import com.stameni.com.moviebrowser.di.modules.ViewModelModule

abstract class BaseFragment<VB : ViewBinding>(private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB) :
    Fragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, false)
        setupViews()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun setupViews()

    protected val controllerComponent: ControllerComponent
        @UiThread
        get() {
            return applicationComponent!!
                .newControllerComponent(
                    ControllerModule(requireActivity()),
                    ViewModelModule(),
                    NetworkModule()
                )
        }

    private val applicationComponent: ApplicationComponent?
        get() = (requireActivity().application as BaseApplication).getApplicationComponent()
}