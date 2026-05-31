package com.rgi.amhisevikari.domain.model

data class Category(
    val id: String,
    val name: String,
    val enName: String,
    val order: Int,
    val lastUpdated: Long,
    val imageUrl: String? = null,
    val searchToken: String = ""
)
