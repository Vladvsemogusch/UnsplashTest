plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
    id(libs.plugins.kotlin.parcelize.get().pluginId)
    kotlin("plugin.serialization") version "1.9.21"
}


android {
    namespace = "cc.anisimov.vlad.unsplashtest"
    compileSdk = 36
    defaultConfig {
        applicationId = "cc.anisimov.vlad.unsplashtest"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "0.1"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.compiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

}

ksp {
    arg("room.schemaLocation", "$projectDir/schemas")
}

dependencies {
    //    Compose
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.lifecycle.compose)
    implementation(libs.material)
    //  Compose Destinations
    implementation(libs.compose.destinations.core)
    ksp(libs.compose.destinations.ksp)
    // Hilt
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)
    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.converter)
    // Room
    implementation(libs.room)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    //  Desugaring
    coreLibraryDesugaring(libs.core.desugaring)
    //  Coroutines
    implementation(libs.kotlin.coroutines)
    //  Coil
    implementation(libs.coil)
    implementation(libs.coil.compose)
    //  Kotlinx serialization
    implementation(libs.kotlinx.serialization)
}