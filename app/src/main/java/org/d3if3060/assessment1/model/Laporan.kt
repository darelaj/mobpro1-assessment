package org.d3if3060.assessment1.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "laporan")
data class Laporan(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val nama: String,
    val jenis: String,
    val jumlah: String,
    val tanggal: String
)
