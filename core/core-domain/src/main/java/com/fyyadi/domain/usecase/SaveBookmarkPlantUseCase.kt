package com.fyyadi.domain.usecase

import com.fyyadi.domain.model.Plant
import com.fyyadi.domain.repository.BookmarkRepository
import javax.inject.Inject

class SaveBookmarkPlantUseCase @Inject constructor(
    private val repository: BookmarkRepository
) {
    suspend operator fun invoke(plant: Plant) =
        repository.savePlant(plant)
}