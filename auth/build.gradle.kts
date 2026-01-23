import java.util.Properties

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.fyyadi.auth"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        minSdk = 28

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        val localProps = Properties()
        localProps.load(project.rootProject.file("local.properties").inputStream())
        buildConfigField("String", "GOOGLE_CLIENT_ID", "\"${localProps.getProperty("GOOGLE_CLIENT_ID")}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    implementation(project(":core:core-domain"))
    implementation(project(":core:core-presentation"))
    implementation(project(":core:core-data"))

    // Core
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.ktx)

    // UI
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.constraintlayout.compose.android)
    implementation(libs.play.services.basement)

    // Dagger Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
    ksp(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    // Compose
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Supabase
    implementation(platform(libs.supabase.core))
    implementation(libs.postgrest.kt)
    implementation(libs.realtime.kt)

    // Google Identity Services
    implementation(libs.androidx.credentials)
    implementation(libs.googleid)
    implementation(libs.androidx.credentials.play.services.auth)

    // Supabase
    implementation(platform(libs.supabase.core))
    implementation(libs.postgrest.kt)
    implementation(libs.supabase.compose.auth)
    implementation(libs.supabase.compose.auth.ui)
    implementation(libs.auth.kt)
    implementation(libs.realtime.kt)

}