package com.rgi.amhisevikari.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rgi.amhisevikari.data.local.entity.BhajanEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BhajanDao {
    @Query("SELECT * FROM bhajans WHERE categoryId = :categoryId ORDER BY `order` ASC")
    fun getBhajansByCategory(categoryId: String): Flow<List<BhajanEntity>>

    @Query("SELECT * FROM bhajans ORDER BY `order` ASC")
    fun getAllBhajans(): Flow<List<BhajanEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBhajans(bhajans: List<BhajanEntity>)

    @Query("SELECT MAX(lastUpdated) FROM bhajans")
    suspend fun getLastUpdatedTime(): Long?
}
