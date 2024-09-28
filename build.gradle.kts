buildscript {
    repositories {
        google()
        mavenCentral()
    }
    extra.apply {
        set("room_version", "2.6.0")
    }
}

plugins {
    id("com.android.application") version "8.1.4" apply false
    id("com.android.library") version "8.1.4" apply false
    id("org.jetbrains.kotlin.android") version "2.0.20" apply false
    id("org.jetbrains.kotlin.plugin.serialization") version "2.0.20" apply false
    id("com.google.devtools.ksp") version "2.0.20-1.0.24" apply false
    id("org.jetbrains.kotlin.plugin.compose") version "2.0.20" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}