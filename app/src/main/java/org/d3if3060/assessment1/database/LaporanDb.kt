package org.d3if3060.assessment1.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.d3if3060.assessment1.model.Laporan
import kotlin.concurrent.Volatile

@Database(entities = [Laporan::class], version = 1, exportSchema = false)
abstract class LaporanDb : RoomDatabase() {

    abstract val dao: LaporanDao

    companion object {

        @Volatile
        private var INSTANCE: LaporanDb? = null

        fun getInstance(context: Context): LaporanDb {
            synchronized(this) {
                var instance = INSTANCE

                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        LaporanDb::class.java,
                        "laporan.db"
                    ).build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}