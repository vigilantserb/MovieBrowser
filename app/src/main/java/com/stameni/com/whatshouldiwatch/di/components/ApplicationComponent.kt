package com.stameni.com.whatshouldiwatch.di.components

import com.stameni.com.whatshouldiwatch.di.modules.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, RoomModule::class])
interface ApplicationComponent {
    fun newControllerComponent(
        controllerModule: ControllerModule,
        viewModelModule: ViewModelModule,
        networkModule: NetworkModule
    ): ControllerComponent
}