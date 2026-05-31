package com.rgi.amhisevikari.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rgi.amhisevikari.domain.model.Category

@Entity(tableName = "categories")
data class CategoryEntity(
    @PrimaryKey val id: String,
    val name: String,
    val enName: String,
    val order: Int,
    val lastUpdated: Long,
    val imageUrl: String? = null,
    val searchToken: String = ""
) {
    fun toDomain() = Category(
        id = id,
        name = name,
        enName = enName,
        order = order,
        lastUpdated = lastUpdated,
        imageUrl = imageUrl,
        searchToken = searchToken
    )
}
