import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")

}


val key: String = gradleLocalProperties(rootDir).getProperty("OPEN_AI_API_KEY")
android {
    namespace ="com.peterchege.aiimagegenerator"
    compileSdk =34

    defaultConfig {
        applicationId ="com.peterchege.aiimagegenerator"
        minSdk = 21
        targetSdk = 34
        versionCode= 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

    }

    buildTypes {
        getByName("debug"){
            buildConfigField("String", "OPEN_AI_API_KEY", key)
        }
        getByName("release") {
            buildConfigField("String", "OPEN_AI_API_KEY", key)
            isMinifyEnabled =false
            proguardFiles (getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose= true
    }
    composeOptions {
        kotlinCompilerExtensionVersion ="1.5.3"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


}

dependencies {
    

    implementation ("androidx.core:core-ktx:1.10.1")
    implementation ("androidx.compose.ui:ui:1.5.0-beta01")
    implementation ("androidx.compose.material:material:1.5.0-beta01")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.5.0-beta01")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation ("androidx.activity:activity-compose:1.7.2")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.5.0-beta01")
    debugImplementation ("androidx.compose.ui:ui-tooling:1.5.0-beta01")


    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    implementation("androidx.navigation:navigation-compose:2.7.1")

    // view model
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.1")


    //coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

    // dagger hilt
    implementation ("com.google.dagger:hilt-android:2.48")
    ksp("com.google.dagger:dagger-compiler:2.48") // Dagger compiler
    ksp("com.google.dagger:hilt-compiler:2.48")   // Hilt compiler
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")
    // coil
    implementation ("io.coil-kt:coil-compose:2.4.0")


    //pager
    implementation ("com.google.accompanist:accompanist-pager:0.30.1")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.30.1")

    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("io.mockk:mockk:1.13.5")
    androidTestImplementation( "io.mockk:mockk-android:1.13.5")
    debugImplementation ("com.github.chuckerteam.chucker:library:4.0.0")
    releaseImplementation ("com.github.chuckerteam.chucker:library-no-op:4.0.0")
}