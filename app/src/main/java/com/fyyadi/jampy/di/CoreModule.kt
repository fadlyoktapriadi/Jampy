package com.fyyadi.jampy.di

import android.content.Context
import androidx.room.Room
import com.fyyadi.data.repository.AuthRepositoryImpl
import com.fyyadi.data.repository.BookmarkRepositoryImpl
import com.fyyadi.data.repository.CoreRepositoryImpl
import com.fyyadi.data.repository.PlantRepositoryImpl
import com.fyyadi.data.source.local.room.AppDatabase
import com.fyyadi.data.source.local.room.dao.PlantBookmarkDao
import com.fyyadi.data.source.local.sharedpreference.PreferenceManager
import com.fyyadi.domain.repository.AuthRepository
import com.fyyadi.domain.repository.BookmarkRepository
import com.fyyadi.domain.repository.CoreRepository
import com.fyyadi.domain.repository.PlantRepository
import com.fyyadi.domain.usecase.AddUserUseCase
import com.fyyadi.domain.usecase.AuthUseCase
import com.fyyadi.domain.usecase.CheckUserLoginUseCase
import com.fyyadi.domain.usecase.ClearUserLoginUseCase
import com.fyyadi.domain.usecase.CoreUseCase
import com.fyyadi.domain.usecase.GetAllBookmarkedPlantsUseCase
import com.fyyadi.domain.usecase.GetAllPlantsUseCase
import com.fyyadi.domain.usecase.GetLoginStatusUseCase
import com.fyyadi.domain.usecase.GetDetailPlantUseCase
import com.fyyadi.domain.usecase.GetPlantHomeUseCase
import com.fyyadi.domain.usecase.GetUserProfileUseCase
import com.fyyadi.domain.usecase.IsPlantBookmarkedUseCase
import com.fyyadi.domain.usecase.LogoutUseCase
import com.fyyadi.domain.usecase.RemoveBookmarkPlantUseCase
import com.fyyadi.domain.usecase.SaveBookmarkPlantUseCase
import com.fyyadi.domain.usecase.SaveUserLoginUseCase
import com.fyyadi.jampy.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.serializer.KotlinXSerializer
import kotlinx.serialization.json.Json
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
            defaultSerializer = KotlinXSerializer(
                Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    encodeDefaults = true
                }
            )
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
    fun provideCoreRepository(
        preferenceManager: PreferenceManager,
        postgrest: Postgrest,
    ): CoreRepository = CoreRepositoryImpl(preferenceManager, postgrest)

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: Auth,
        postgrest: Postgrest,
        preferenceManager: PreferenceManager
    ): AuthRepository = AuthRepositoryImpl(auth, postgrest, preferenceManager)

    @Provides
    @Singleton
    fun providePlantRepository(
        postgrest: Postgrest
    ): PlantRepository = PlantRepositoryImpl(postgrest)

    @Provides
    @Singleton
    fun provideBookmarkRepository(dao: PlantBookmarkDao): BookmarkRepository =
        BookmarkRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext ctx: Context): AppDatabase =
        Room.databaseBuilder(ctx, AppDatabase::class.java, "jampy.db").build()

    @Provides
    fun providePlantBookmarkDao(db: AppDatabase): PlantBookmarkDao = db.plantBookmarkDao()

    @Provides
    fun providesCoreUseCases(
        coreRepository: CoreRepository,
        bookmarkRepository: BookmarkRepository,
        plantRepository: PlantRepository,
        authRepository: AuthRepository
    ) = CoreUseCase(
        authUseCase = AuthUseCase(authRepository),
        addUserUseCase = AddUserUseCase(authRepository),
        checkUserLoginUseCase = CheckUserLoginUseCase(authRepository),
        clearUserLoginUseCase = ClearUserLoginUseCase(coreRepository),
        getUserProfileUseCase = GetUserProfileUseCase(coreRepository),
        getLoginStatusUseCase = GetLoginStatusUseCase(coreRepository),
        saveUserLoginUseCase = SaveUserLoginUseCase(coreRepository),
        getPlantHomeUseCase = GetPlantHomeUseCase(plantRepository),
        getAllPlantUseCase = GetAllPlantsUseCase(plantRepository),
        getDetailPlantUseCase = GetDetailPlantUseCase(plantRepository),
        getAllBookmarkedPlantsUseCase = GetAllBookmarkedPlantsUseCase(bookmarkRepository),
        saveBookmarkPlantUseCase = SaveBookmarkPlantUseCase(bookmarkRepository),
        removeBookmarkPlantUseCase = RemoveBookmarkPlantUseCase(bookmarkRepository),
        isPlantBookmarkedUseCase = IsPlantBookmarkedUseCase(bookmarkRepository),
        logoutUseCase = LogoutUseCase(authRepository)
    )
}