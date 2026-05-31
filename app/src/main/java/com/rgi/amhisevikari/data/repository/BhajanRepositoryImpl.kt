package com.rgi.amhisevikari.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.rgi.amhisevikari.data.local.dao.BhajanDao
import com.rgi.amhisevikari.data.local.dao.CategoryDao
import com.rgi.amhisevikari.data.remote.dto.BhajanDto
import com.rgi.amhisevikari.data.remote.dto.CategoryDto
import com.rgi.amhisevikari.domain.model.Bhajan
import com.rgi.amhisevikari.domain.model.Category
import com.rgi.amhisevikari.domain.repository.BhajanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class BhajanRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao,
    private val bhajanDao: BhajanDao,
    private val firestore: FirebaseFirestore
) : BhajanRepository {

    override fun getAllCategories(): Flow<List<Category>> {
        return categoryDao.getAllCategories().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getBhajansByCategory(categoryId: String): Flow<List<Bhajan>> {
        return bhajanDao.getBhajansByCategory(categoryId).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getAllBhajans(): Flow<List<Bhajan>> {
        return bhajanDao.getAllBhajans().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun syncFromRemote() {
        try {
            val localCategoryLastUpdate = categoryDao.getLastUpdatedTime() ?: 0L
            val localBhajanLastUpdate = bhajanDao.getLastUpdatedTime() ?: 0L

            val categoriesResult = firestore.collection("categories")
                .whereGreaterThan("lastUpdated", localCategoryLastUpdate)
                .get()
                .await()

            if (!categoriesResult.isEmpty) {
                val categoryDocs = categoriesResult.toObjects(CategoryDto::class.java)
                categoryDao.insertCategories(categoryDocs.map { it.toEntity() })
            }

            val bhajansResult = firestore.collection("bhajans")
                .whereGreaterThan("lastUpdated", localBhajanLastUpdate)
                .get()
                .await()

            if (!bhajansResult.isEmpty) {
                val bhajanDocs = bhajansResult.toObjects(BhajanDto::class.java)
                bhajanDao.insertBhajans(bhajanDocs.map { it.toEntity() })
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
