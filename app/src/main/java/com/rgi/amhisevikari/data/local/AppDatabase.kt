package com.rgi.amhisevikari.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rgi.amhisevikari.data.local.dao.BhajanDao
import com.rgi.amhisevikari.data.local.dao.CategoryDao
import com.rgi.amhisevikari.data.local.entity.BhajanEntity
import com.rgi.amhisevikari.data.local.entity.CategoryEntity

@Database(entities = [CategoryEntity::class, BhajanEntity::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun bhajanDao(): BhajanDao
}
