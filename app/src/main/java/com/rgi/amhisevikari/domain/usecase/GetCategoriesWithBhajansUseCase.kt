package com.rgi.amhisevikari.domain.usecase

import com.rgi.amhisevikari.domain.model.Bhajan
import com.rgi.amhisevikari.domain.model.Category
import com.rgi.amhisevikari.domain.repository.BhajanRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetCategoriesWithBhajansUseCase @Inject constructor(
    private val repository: BhajanRepository
) {
    operator fun invoke(): Flow<List<CategoryWithBhajans>> {
        return combine(
            repository.getAllCategories(),
            repository.getAllBhajans()
        ) { categories, bhajans ->
            categories.map { category ->
                CategoryWithBhajans(
                    category = category,
                    bhajans = bhajans.filter { it.categoryId == category.id }
                )
            }
        }
    }
}

data class CategoryWithBhajans(
    val category: Category,
    val bhajans: List<Bhajan>
)
