package org.d3if3060.assessment1.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import org.d3if3060.assessment1.model.Laporan

@Dao
interface LaporanDao {

    @Insert
    suspend fun insert(laporan: Laporan)

    @Update
    suspend fun update(laporan: Laporan)

    @Query("SELECT * FROM laporan ORDER BY tanggal DESC")
    fun getLaporan(): Flow<List<Laporan>>

    @Query("SELECT * FROM laporan WHERE id = :id")
    suspend fun getLaporanById(id: Long): Laporan?
    
    @Query("DELETE FROM laporan WHERE id = :id")
    suspend fun deleteById(id: Long)

}