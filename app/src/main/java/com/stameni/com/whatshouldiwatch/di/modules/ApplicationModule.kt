package com.stameni.com.whatshouldiwatch.di.modules

import android.app.Application
import dagger.Module
import dagger.Provides


@Module
class ApplicationModule(private val mApplication: Application) {
    @Provides
    internal fun getApplication(): Application = mApplication
}
