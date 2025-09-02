package com.fyyadi.domain.usecase

import com.fyyadi.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class IsPlantBookmarkedUseCase @Inject constructor(
    private val repository: BookmarkRepository
) {
    operator fun invoke(id: Int): Flow<Boolean> =
        repository.isBookmarked(id)
}