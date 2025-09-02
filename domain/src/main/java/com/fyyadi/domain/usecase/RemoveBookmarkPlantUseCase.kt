package com.fyyadi.domain.usecase

import com.fyyadi.domain.repository.BookmarkRepository
import javax.inject.Inject

class RemoveBookmarkPlantUseCase @Inject constructor(
    private val repository: BookmarkRepository
) {
    suspend operator fun invoke(id: Int)  =
        repository.removePlant(id)
}