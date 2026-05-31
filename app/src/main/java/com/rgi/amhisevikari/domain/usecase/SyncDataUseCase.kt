package com.rgi.amhisevikari.domain.usecase

import com.rgi.amhisevikari.domain.repository.BhajanRepository
import javax.inject.Inject

class SyncDataUseCase @Inject constructor(
    private val repository: BhajanRepository
) {
    suspend operator fun invoke() {
        repository.syncFromRemote()
    }
}
