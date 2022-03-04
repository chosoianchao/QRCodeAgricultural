package com.utt.qrcodeagricultural.network.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.utt.qrcodeagricultural.model.Agricultural
import com.utt.qrcodeagricultural.network.local.dao.AgriculturalDao

@Database(entities = [Agricultural::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {

    abstract val dao: AgriculturalDao
    companion object {
        @Volatile
        private var INSTANCE: AppDB? = null
        fun getInstance(context: Context): AppDB {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDB::class.java,
                        "agricultural-db"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
