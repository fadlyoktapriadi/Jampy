package com.fyyadi.jampy.di

import com.fyyadi.data.repository.CoreRepositoryImpl
import com.fyyadi.domain.repository.CoreRepository
import com.fyyadi.domain.usecase.AddUserUseCase
import com.fyyadi.domain.usecase.AuthUseCase
import com.fyyadi.domain.usecase.CheckUserLoginUseCase
import com.fyyadi.domain.usecase.CoreUseCase
import com.fyyadi.jampy.BuildConfig
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