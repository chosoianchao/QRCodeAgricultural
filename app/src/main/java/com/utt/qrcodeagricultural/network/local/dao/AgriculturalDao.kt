package com.utt.qrcodeagricultural.network.local.dao

import androidx.room.*
import com.utt.qrcodeagricultural.model.Agricultural

@Dao
interface AgriculturalDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAgricultural(agricultural: Agricultural)

    @Query("Select * From Agricultural")
    suspend fun getAll(): List<Agricultural>

    @Query("SELECT * FROM Agricultural WHERE Name LIKE '%' || :search || '%'")
    suspend fun searchName(search: String): List<Agricultural>

    @Delete
    suspend fun deleteData(agricultural: Agricultural)
}