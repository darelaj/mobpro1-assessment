package org.d3if3060.assessment1.ui.screen

import androidx.lifecycle.ViewModel
import org.d3if3060.assessment1.model.Laporan

class MainViewModel : ViewModel() {

    val data = getDataDummy()

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