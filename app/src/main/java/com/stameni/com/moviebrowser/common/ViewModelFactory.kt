package com.stameni.com.moviebrowser.common

import androidx.annotation.NonNull
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Provider


class ViewModelFactory(private val mProviderMap: Map<Class<out ViewModel>, Provider<ViewModel>>) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    @NonNull
    override fun <T : ViewModel> create(@NonNull modelClass: Class<T>): T {
        return mProviderMap.getValue(modelClass).get() as T
    }
}
