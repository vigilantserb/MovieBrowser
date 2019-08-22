package com.stameni.com.whatshouldiwatch.screens.settings

import androidx.lifecycle.ViewModel

class SettingsViewModel(
    private val requestPermissions: RequestPermissionUseCase,
    private val writeMoviesToCsv: CreateCsvFileUseCase
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
}
