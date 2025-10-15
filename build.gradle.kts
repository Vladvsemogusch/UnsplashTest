plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.ktlint) apply false
    alias(libs.plugins.jetbrains.kotlin.jvm) apply false
}

// Apply to all modules
subprojects {
    // KtLint
    plugins.apply("org.jlleitschuh.gradle.ktlint")

    // Preload Mockk agent for test tasks
    // See https://openjdk.org/jeps/451
    tasks.withType<Test>().configureEach {
        doFirst {
            val mockkAgent = classpath.find {
                it.name.contains("byte-buddy-agent")
            }
            if (mockkAgent != null) {
                println("Preload MockK agent (byte-buddy-agent)")
                jvmArgs("-javaagent:${mockkAgent.absolutePath}")
            }
        }
    }
}