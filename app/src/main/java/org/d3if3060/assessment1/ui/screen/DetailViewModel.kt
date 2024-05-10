package org.d3if3060.assessment1.ui.screen

import androidx.lifecycle.ViewModel
import org.d3if3060.assessment1.model.Laporan

class DetailViewModel : ViewModel() {

    fun getLaporan(id: Long): Laporan {
        return Laporan(
            id,
            "Beli Laptop",
            "Elektronik",
            "Rp. 15000000",
            "2024-05-$id"
        )
    }
}