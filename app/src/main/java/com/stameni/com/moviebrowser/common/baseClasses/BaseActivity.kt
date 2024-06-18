package com.stameni.com.moviebrowser.common.baseClasses

import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.UiThread
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.stameni.com.moviebrowser.di.components.ApplicationComponent
import com.stameni.com.moviebrowser.di.components.ControllerComponent
import com.stameni.com.moviebrowser.di.modules.ControllerModule
import com.stameni.com.moviebrowser.di.modules.NetworkModule
import com.stameni.com.moviebrowser.di.modules.ViewModelModule

abstract class BaseActivity<VB : ViewBinding>(
    private val bindingInflater: (LayoutInflater) -> VB
) : AppCompatActivity() {

    private var mIsInjectorUsed: Boolean = false

    private var _binding: VB? = null
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)
        setupViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    abstract fun setupViews()

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