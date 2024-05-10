package org.d3if3060.assessment1.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import org.d3if3060.assessment1.database.LaporanDao
import org.d3if3060.assessment1.model.Laporan

class MainViewModel(dao: LaporanDao) : ViewModel() {

    val data: StateFlow<List<Laporan>> = dao.getLaporan().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    private fun getDataDummy(): List<Laporan> {
        val data = mutableListOf<Laporan>()
        for (i in 29 downTo 20) {
            data.add(
                Laporan(
                    i.toLong(),
                    "Beli Laptop",
                    "Elektronik",
                    "Rp. 15000000",
                    "2024-05-$i"
                )
            )
        }
        return data
    }
}