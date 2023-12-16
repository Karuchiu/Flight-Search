plugins {
    id("com.google.dagger.hilt.android") version "2.44" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:8.1.2")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.0")
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
