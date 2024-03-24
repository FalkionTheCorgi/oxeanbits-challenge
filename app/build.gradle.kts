import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.example.oxeanbits_challenge"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.oxeanbits_challenge"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    packaging {
        resources {
            resources.excludes += "**/*"
        }
    }

    buildTypes {

        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        release {
            isMinifyEnabled = false
            buildConfigField("String", "API_LINK", "\"${properties.getProperty("API_LINK")}\"")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }

        debug {
            buildConfigField("String", "API_LINK", "\"${properties.getProperty("API_LINK")}\"")
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
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    testOptions.unitTests.isIncludeAndroidResources = true
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.io.com.google.accompanist.systemuicontroller)
    implementation(libs.androidx.navigation)
    implementation(libs.androidx.uiautomator)
    implementation(libs.core.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    testImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.com.airbnb.android.lottie.compose)

    implementation(libs.com.squareup.retrofit2)
    implementation(libs.com.squareup.retrofit2.converter.json)
    implementation(libs.gson)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.com.squareup.okhttp3.mockwebserver)

    implementation(libs.io.insert.koin)
    implementation(libs.io.insert.koin.compose)
    implementation(libs.io.insert.koin.workmanager)
    testImplementation(libs.io.insert.koin.test)
    androidTestImplementation(libs.io.insert.koin.android.test)
    androidTestImplementation(libs.io.insert.koin.test)
    implementation(libs.kotlinx.serialization.json)
    testImplementation(libs.io.insert.koin.test.junit4)

    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)
    androidTestImplementation(libs.androidx.room.testing)

    androidTestImplementation(libs.org.robolectric)
    testImplementation(libs.org.robolectric)

    testImplementation(libs.mockk)
    androidTestImplementation(libs.mockk)

}