plugins {
    id("com.diffplug.spotless") version "5.3.0"

}
buildscript {

    repositories {
        google()
        mavenCentral()

    }
    dependencies {
        classpath ("com.android.tools.build:gradle:7.4.2")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.10")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.46.1")



    }

}
//apply(from = "${project.rootDir}/jacoco.gradle.kts")
apply(plugin = "com.diffplug.spotless")
spotless {
    kotlin {
        target("**/*.kt")
        licenseHeaderFile(
            rootProject.file("${project.rootDir}/spotless/LICENSE.txt"),
            "^(package|object|import|interface)"
        )
    }
}
