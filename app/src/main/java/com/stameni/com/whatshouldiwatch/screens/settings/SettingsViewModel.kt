package com.stameni.com.whatshouldiwatch.screens.settings

import androidx.lifecycle.ViewModel
import com.stameni.com.whatshouldiwatch.screens.settings.useCases.ClearPhoneCashUseCase
import com.stameni.com.whatshouldiwatch.screens.settings.useCases.CreateCsvFileUseCase
import com.stameni.com.whatshouldiwatch.screens.settings.useCases.RequestPermissionUseCase

class SettingsViewModel(
    private val requestPermissions: RequestPermissionUseCase,
    private val writeMoviesToCsv: CreateCsvFileUseCase,
    private val clearPhoneCash: ClearPhoneCashUseCase
) : ViewModel() {

    val writeCsvFilePermission = requestPermissions.permissionApproved

    val csvFileWriteSuccessful = writeMoviesToCsv.writeSuccessful

    fun requestPermissions(
        list: ArrayList<String>,
        deniedText: String
    ) =
        requestPermissions.requestPermissions(list, deniedText)

    fun writeMoviesToCsvFile(){
        writeMoviesToCsv.createCsvFile()
    }

    fun clearPhoneCash(){
        clearPhoneCash.handleImageCache()
    }
}
