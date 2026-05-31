package com.rgi.amhisevikari.domain.repository

import com.rgi.amhisevikari.domain.model.Bhajan
import com.rgi.amhisevikari.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface BhajanRepository {
    fun getAllCategories(): Flow<List<Category>>
    fun getBhajansByCategory(categoryId: String): Flow<List<Bhajan>>
    fun getAllBhajans(): Flow<List<Bhajan>>
    suspend fun syncFromRemote()
}
