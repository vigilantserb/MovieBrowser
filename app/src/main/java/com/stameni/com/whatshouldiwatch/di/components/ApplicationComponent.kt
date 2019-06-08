package com.stameni.com.whatshouldiwatch.di.components

import com.stameni.com.whatshouldiwatch.di.modules.ApplicationModule
import com.stameni.com.whatshouldiwatch.di.modules.ControllerModule
import com.stameni.com.whatshouldiwatch.di.modules.NetworkModule
import com.stameni.com.whatshouldiwatch.di.modules.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun newControllerComponent(
        controllerModule: ControllerModule,
        viewModelModule: ViewModelModule,
        networkModule: NetworkModule
    ): ControllerComponent
}