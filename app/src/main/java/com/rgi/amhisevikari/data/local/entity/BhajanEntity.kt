package com.rgi.amhisevikari.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rgi.amhisevikari.domain.model.Bhajan

@Entity(tableName = "bhajans")
data class BhajanEntity(
    @PrimaryKey val id: String,
    val categoryId: String,
    val title: String,
    val lyrics: String,
    val order: Int,
    val lastUpdated: Long,
    val searchToken: String = ""
) {
    fun toDomain() = Bhajan(
        id = id,
        categoryId = categoryId,
        title = title,
        lyrics = lyrics,
        order = order,
        lastUpdated = lastUpdated,
        searchToken = searchToken
    )
}
