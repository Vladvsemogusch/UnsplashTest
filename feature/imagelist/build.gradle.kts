import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    id("kotlin-parcelize")
}

android {
    namespace = "cc.anisimov.vlad.unsplashtest.imagelist"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        consumerProguardFiles("consumer-rules.pro")
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlin {
        compilerOptions {
            jvmTarget = JvmTarget.JVM_17
        }
    }

    packaging {
        resources {
            merges += "META-INF/LICENSE.md"
            merges += "META-INF/LICENSE-notice.md"
        }
    }

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

ksp {
    // Compose Destinations
    arg("compose-destinations.moduleName", "imagelist")
    arg("compose-destinations.htmlMermaidGraph", "$rootDir//navigation-docs")
    arg("compose-destinations.mermaidGraph", "$rootDir/navigation-docs")
}

dependencies {
    implementation(projects.domain)
    implementation(projects.core.ui)
    implementation(projects.core.common)

    implementation(libs.bundles.compose)
    implementation(libs.bundles.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.compose.destinations.core)
    ksp(libs.compose.destinations.ksp)
    implementation(libs.kotlin.coroutines)
    implementation(libs.kotlinx.collections.immutable)

    testImplementation(libs.bundles.test)
    androidTestImplementation(libs.bundles.test.android)
    testImplementation(testFixtures(projects.core.common))
    debugImplementation(libs.compose.ui.test.manifest)
}
