package com.rgi.amhisevikari.domain.model

data class Bhajan(
    val id: String,
    val categoryId: String,
    val title: String,
    val lyrics: String,
    val order: Int,
    val lastUpdated: Long,
    val searchToken: String = ""
)
