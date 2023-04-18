import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {

    id ("com.android.application")
    id ("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")

}

val key: String = gradleLocalProperties(rootDir).getProperty("OPEN_AI_API_KEY")
android {
    namespace ="com.peterchege.aiimagegenerator"
    compileSdk =33

    defaultConfig {
        applicationId ="com.peterchege.aiimagegenerator"
        minSdk = 21
        targetSdk = 33
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
        kotlinCompilerExtensionVersion ="1.4.4"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }


}

dependencies {
    

    implementation ("androidx.core:core-ktx:1.10.0")
    implementation ("androidx.compose.ui:ui:1.5.0-alpha02")
    implementation ("androidx.compose.material:material:1.5.0-alpha02")
    implementation ("androidx.compose.ui:ui-tooling-preview:1.5.0-alpha02")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    implementation ("androidx.activity:activity-compose:1.7.0")
    testImplementation ("junit:junit:4.13.2")
    androidTestImplementation ("androidx.test.ext:junit:1.1.5")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation ("androidx.compose.ui:ui-test-junit4:1.5.0-alpha02")
    debugImplementation ("androidx.compose.ui:ui-tooling:1.5.0-alpha02")


    implementation ("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation ("com.squareup.okhttp3:okhttp:5.0.0-alpha.2")
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.2")
    implementation("androidx.navigation:navigation-compose:2.5.3")

    // view model
    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")

    //coroutines
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    // dagger hilt
    implementation ("com.google.dagger:hilt-android:2.45")
    kapt ("com.google.dagger:hilt-android-compiler:2.45")
//    implementation "androidx.hilt:hilt-lifecycle-viewmodel:1.0.0-alpha03"
    kapt ("androidx.hilt:hilt-compiler:1.0.0")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")
    // coil
    implementation ("io.coil-kt:coil-compose:2.3.0")

// room
    implementation ("androidx.room:room-runtime:2.5.1")
    kapt ("androidx.room:room-compiler:2.5.1")

    // Kotlin Extensions and Coroutines support for Room
    implementation ("androidx.room:room-ktx:2.5.1")
    //pager
    implementation ("com.google.accompanist:accompanist-pager:0.28.0")
    implementation ("com.google.accompanist:accompanist-pager-indicators:0.28.0")

    testImplementation ("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.4")
}