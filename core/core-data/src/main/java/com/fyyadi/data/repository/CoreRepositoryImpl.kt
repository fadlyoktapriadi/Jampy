package com.fyyadi.data.repository

import com.fyyadi.data.source.local.sharedpreference.PreferenceManager
import com.fyyadi.domain.model.UserProfile
import com.fyyadi.domain.repository.CoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CoreRepositoryImpl @Inject constructor(
    private val preferenceManager: PreferenceManager
) : CoreRepository {

    override fun saveUserLogin(user: UserProfile): Flow<Result<Unit>> = flow {
        val result = runCatching {
            preferenceManager.isLoggedIn = true
            preferenceManager.userEmail = user.userEmail
            preferenceManager.userFullName = user.userFullName
            preferenceManager.userPhotoProfileUrl = user.photoProfile
            preferenceManager.userRole = user.role
        }
        emit(result)
    }

    override fun clearUserLogin(): Flow<Result<Unit>> = flow {
        val result = runCatching {
            preferenceManager.clearUserData()
        }
        emit(result)
    }

    override fun isUserLoggedIn(): Flow<Result<Boolean>> = flow {
        val result = runCatching {
            preferenceManager.isLoggedIn
        }
        emit(result)
    }

    override fun getUserProfile(): Flow<Result<UserProfile?>> = flow {
        val result = runCatching {
            if (preferenceManager.isLoggedIn) {
                UserProfile(
                    userId = null,
                    userFullName = preferenceManager.userFullName,
                    userEmail = preferenceManager.userEmail,
                    photoProfile = preferenceManager.userPhotoProfileUrl,
                    role = preferenceManager.userRole
                )
            } else null
        }
        emit(result)
    }
}