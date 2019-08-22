package com.stameni.com.whatshouldiwatch.di.modules

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.view.LayoutInflater
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.stameni.com.whatshouldiwatch.common.ImageLoader
import com.stameni.com.whatshouldiwatch.data.room.MovieDatabase
import com.stameni.com.whatshouldiwatch.screens.settings.useCases.ClearPhoneCashUseCase
import com.stameni.com.whatshouldiwatch.screens.settings.useCases.CreateCsvFileUseCase
import com.stameni.com.whatshouldiwatch.screens.settings.useCases.RequestPermissionUseCase
import dagger.Module
import dagger.Provides

@Module
class ControllerModule(private val mActivity: FragmentActivity) {

    @Provides
    internal fun getFragmentManager(): FragmentManager {
        return mActivity.supportFragmentManager
    }

    @Provides
    internal fun getLayoutInflater(): LayoutInflater {
        return LayoutInflater.from(mActivity)
    }

    @Provides
    internal fun getActivity(): Activity {
        return mActivity
    }

    @Provides
    internal fun getSharedPreferences(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)

    @Provides
    internal fun getImageLoader(preferences: SharedPreferences) = ImageLoader(preferences, mActivity)

    @Provides
    internal fun getPermissionsApproved(activity: Activity) =
        RequestPermissionUseCase(activity)

    @Provides
    internal fun getCreateCsvFile(movieDatabase: MovieDatabase) =
        CreateCsvFileUseCase(movieDatabase)

    @Provides
    internal fun clearPhoneCash(activity: Activity) = ClearPhoneCashUseCase(activity)
}