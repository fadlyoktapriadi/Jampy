package com.fyyadi.domain.usecase

import com.fyyadi.domain.model.Plant
import com.fyyadi.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllBookmarkedPlantsUseCase @Inject constructor(
    private val repository: BookmarkRepository
) {
    operator fun invoke() : Flow<Result<List<Plant>>> =
        repository.getAllPlant()
}