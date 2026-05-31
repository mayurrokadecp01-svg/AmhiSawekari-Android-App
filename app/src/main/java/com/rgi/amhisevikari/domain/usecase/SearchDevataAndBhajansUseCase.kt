package com.rgi.amhisevikari.domain.usecase

import javax.inject.Inject

class SearchDevataAndBhajansUseCase @Inject constructor() {

    operator fun invoke(
        query: String,
        categories: List<CategoryWithBhajans>
    ): List<CategoryWithBhajans> {
        if (query.isBlank()) return categories

        return categories.mapNotNull { categoryWithBhajans ->
            val isCategoryMatch = categoryWithBhajans.category.name.contains(query, ignoreCase = true) ||
                                  categoryWithBhajans.category.enName.contains(query, ignoreCase = true) ||
                                  categoryWithBhajans.category.searchToken.contains(query, ignoreCase = true)
            val matchingBhajans = categoryWithBhajans.bhajans.filter { 
                it.title.contains(query, ignoreCase = true) ||
                it.searchToken.contains(query, ignoreCase = true)
            }

            if (isCategoryMatch) {
                // If the Devata name matches, return the whole Devata with all its Bhajans
                categoryWithBhajans
            } else if (matchingBhajans.isNotEmpty()) {
                // If a Bhajan matches, return the Devata with ONLY the matching Bhajans
                categoryWithBhajans.copy(bhajans = matchingBhajans)
            } else {
                null
            }
        }
    }
}
