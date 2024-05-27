plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}

android {
    namespace = "cc.anisimov.vlad.unsplashtest"
    compileSdk = 34
    defaultConfig {
        applicationId = "cc.anisimov.vlad.unsplashtest"
        minSdk = 24
        targetSdk = 34
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
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
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

    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.compose.destinations.core)
    ksp(libs.compose.destinations.ksp)
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.lifecycle.compose)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    implementation(libs.room)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)
    coreLibraryDesugaring(libs.core.desugaring)
    implementation(libs.kotlin.coroutines)
    implementation(libs.androidx.core)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.jetpack.navigation.fragment)
    implementation(libs.jetpack.navigation.ui)
    implementation(libs.coil)
    implementation(libs.coil.compose)
    implementation(libs.hilt.navigation.compose)
}