package org.d3if3060.assessment1.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import org.d3if3060.assessment1.database.LaporanDao
import org.d3if3060.assessment1.ui.screen.MainViewModel

class ViewModelFactory(
    private val dao: LaporanDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}