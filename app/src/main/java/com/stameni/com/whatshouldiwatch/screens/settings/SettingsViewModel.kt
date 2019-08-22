package com.stameni.com.whatshouldiwatch.screens.settings

import androidx.lifecycle.ViewModel
import com.stameni.com.whatshouldiwatch.data.room.localData.ImportMovieListFromCsvUseCase
import com.stameni.com.whatshouldiwatch.screens.settings.useCases.ClearPhoneCashUseCase
import com.stameni.com.whatshouldiwatch.screens.settings.useCases.CreateCsvFileUseCase

class SettingsViewModel(
    private val writeMoviesToCsv: CreateCsvFileUseCase,
    private val clearPhoneCash: ClearPhoneCashUseCase,
    private val importMovieListFromCsv: ImportMovieListFromCsvUseCase
) : ViewModel() {

    val csvFileWriteSuccessful = writeMoviesToCsv.writeSuccessful

    val csvReadFileSuccessful = importMovieListFromCsv.successMessage

    fun writeMoviesToCsvFile() {
        writeMoviesToCsv.requestCsvFileWrite()
    }

    fun clearPhoneCash() {
        clearPhoneCash.handleImageCache()
    }

    fun importMovieListFromCsv(){
        importMovieListFromCsv.requestCsvFileRead()
    }
}
