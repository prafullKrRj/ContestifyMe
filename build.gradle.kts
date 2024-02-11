buildscript {
    extra.apply {
        set("room_version", "2.5.2")
    }
}
plugins {
    id("com.android.application") version "8.1.1" apply false
    id("com.android.library") version "8.1.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.21" apply false
    id("com.google.dagger.hilt.android") version "2.49" apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}