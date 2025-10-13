import org.jetbrains.kotlin.gradle.dsl.JvmTarget

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}
plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    `java-test-fixtures`
}

dependencies {
    testFixturesImplementation(libs.junit)
    testFixturesImplementation(libs.coroutines.test)
}