plugins {
    id("com.diffplug.spotless") version "5.3.0"
    id("org.jetbrains.kotlinx.kover") version "0.6.1"
}
buildscript {
    val jacocoVersion by extra("0.2")
    repositories {
        google()
        mavenCentral()

    }
    dependencies {
        classpath ("com.android.tools.build:gradle:7.3.1")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
        classpath ("com.google.dagger:hilt-android-gradle-plugin:2.44.2")
        classpath("com.hiya:jacoco-android:$jacocoVersion")


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
