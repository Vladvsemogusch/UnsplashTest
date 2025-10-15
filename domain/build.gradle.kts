import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.jetbrains.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    implementation(libs.kotlin.coroutines)
    // Needed for @Inject/@Qualifier used in domain (no Hilt here)
    implementation(libs.javax.inject)

    testImplementation(libs.bundles.test)
    testImplementation(testFixtures(projects.core.common))
}
