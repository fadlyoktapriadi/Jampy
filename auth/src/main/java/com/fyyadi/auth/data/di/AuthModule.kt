package com.fyyadi.auth.data.di

import com.fyyadi.auth.data.repository.AuthRepositoryImpl
import com.fyyadi.auth.domain.repository.AuthRepository
import com.fyyadi.auth.domain.usecase.AddUserUseCase
import com.fyyadi.auth.domain.usecase.AuthUseCase
import com.fyyadi.auth.domain.usecase.CheckUserLoginUseCase
import com.fyyadi.auth.domain.usecase.GetLoginStatusUseCase
import com.fyyadi.auth.domain.usecase.LogoutUseCase
import com.fyyadi.auth.domain.usecase.SaveSessionLoginUseCase
import com.fyyadi.auth.domain.usecase.SignInWithGoogleAuthUseCase
import com.fyyadi.data.source.local.sharedpreference.PreferenceManager
import com.fyyadi.domain.repository.CoreRepository
import com.fyyadi.domain.usecase.GetUserProfileUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepositoryImpl(
        auth: Auth,
        postgrest: Postgrest,
        preference: PreferenceManager
    ): AuthRepository = AuthRepositoryImpl(
        auth, postgrest, preference
    )


    @Provides
    @Singleton
    fun providesAuthUseCases(
        authRepository: AuthRepository,
        coreRepository: CoreRepository
    ) = AuthUseCase(
        signInWithGoogleAuthUseCase = SignInWithGoogleAuthUseCase(authRepository),
        checkUserLoginUseCase = CheckUserLoginUseCase(authRepository),
        addUserUseCase = AddUserUseCase(authRepository),
        getUserProfileUseCase = GetUserProfileUseCase(coreRepository),
        getLoginStatusUseCase = GetLoginStatusUseCase(coreRepository),
        logoutUseCase = LogoutUseCase(authRepository),
        saveSessionLoginUseCase = SaveSessionLoginUseCase(authRepository)
    )
}