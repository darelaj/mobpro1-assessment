package org.d3if3060.assessment1.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.d3if3060.assessment1.database.LaporanDao
import org.d3if3060.assessment1.model.Laporan
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailViewModel(private val dao: LaporanDao) : ViewModel() {

    private val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)

    fun insert(nama: String, jenis: String, jumlah: String) {
        val laporan= Laporan(
            tanggal = formatter.format(Date()),
            nama = nama,
            jenis = jenis,
            jumlah = jumlah
        )

        viewModelScope.launch(Dispatchers.IO) {
            dao.insert(laporan)
        }
    }

    fun getLaporan(id: Long): Laporan {
        return Laporan(
            id,
            "Beli Laptop",
            "Elektronik",
            "15000000",
            "2024-05-$id"
        )
    }
}