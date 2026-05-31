package com.rgi.amhisevikari.data.remote.dto

import com.rgi.amhisevikari.data.local.entity.BhajanEntity
import com.rgi.amhisevikari.utils.TransliterationUtils

data class BhajanDto(
    val id: String = "",
    val categoryId: String = "",
    val title: String = "",
    val lyrics: String = "",
    val order: Int = 0,
    val lastUpdated: Long = 0L
) {
    fun toEntity() = BhajanEntity(
        id = id,
        categoryId = categoryId,
        title = title,
        lyrics = lyrics,
        order = order,
        lastUpdated = lastUpdated,
        searchToken = TransliterationUtils.transliterateMarathiToEnglish(title)
    )
}
