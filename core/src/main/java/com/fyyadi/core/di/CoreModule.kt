package com.fyyadi.core.di

import com.fyyadi.core.BuildConfig
import com.fyyadi.core.data.repository.CoreRepositoryImpl
import com.fyyadi.core.domain.repository.CoreRepository
import com.fyyadi.core.domain.usecase.AddUserUseCase
import com.fyyadi.core.domain.usecase.AuthUseCase
import com.fyyadi.core.domain.usecase.CheckUserLoginUseCase
import com.fyyadi.core.domain.usecase.CoreUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    @Singleton
    fun provideSupabaseClient(): SupabaseClient =
        createSupabaseClient(
            supabaseUrl = BuildConfig.SUPABASE_URL,
            supabaseKey = BuildConfig.SUPABASE_ANON_KEY,
        ) {
            defaultSerializer = KotlinXSerializer()
            install(Auth)
            install(Postgrest)
        }

    @Provides
    @Singleton
    fun provideSupabaseAuth(client: SupabaseClient): Auth = client.auth

    @Provides
    @Singleton
    fun provideSupabasePostgrest(client: SupabaseClient): Postgrest = client.postgrest

    @Provides
    @Singleton
    fun provideCoreRepositoryImpl(
        auth: Auth,
        postgrest: Postgrest
    ): CoreRepository = CoreRepositoryImpl(auth, postgrest)

    @Provides
    fun providesCoreUseCases(
        coreRepository: CoreRepository
    ) = CoreUseCase(
        authUseCase = AuthUseCase(coreRepository),
        addUserUseCase = AddUserUseCase(coreRepository),
        checkUserLoginUseCase = CheckUserLoginUseCase(coreRepository)
    )
}