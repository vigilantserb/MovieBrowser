package com.stameni.com.whatshouldiwatch.di.components

import com.stameni.com.whatshouldiwatch.di.modules.ControllerModule
import com.stameni.com.whatshouldiwatch.di.modules.NetworkModule
import com.stameni.com.whatshouldiwatch.di.modules.ViewModelModule
import com.stameni.com.whatshouldiwatch.screens.MainActivity
import com.stameni.com.whatshouldiwatch.screens.discover.genre.GenreMovies
import dagger.Subcomponent
import javax.inject.Singleton

@Singleton
@Subcomponent(modules = [ControllerModule::class, ViewModelModule::class, NetworkModule::class])
interface ControllerComponent {
    fun inject(genreMovies: GenreMovies)
}