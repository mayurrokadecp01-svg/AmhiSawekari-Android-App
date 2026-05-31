package com.rgi.amhisevikari.data.remote.dto

import com.rgi.amhisevikari.data.local.entity.CategoryEntity
import com.rgi.amhisevikari.utils.TransliterationUtils

data class CategoryDto(
    val id: String = "",
    val name: String = "",
    val enName: String = "",
    val order: Int = 0,
    val lastUpdated: Long = 0L,
    val imageUrl: String? = null
) {
    fun toEntity() = CategoryEntity(
        id = id,
        name = name,
        enName = enName,
        order = order,
        lastUpdated = lastUpdated,
        imageUrl = imageUrl,
        searchToken = TransliterationUtils.transliterateMarathiToEnglish(name)
    )
}
