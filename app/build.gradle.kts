import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.jetbrains.kotlin.parcelize)
}

android {
    namespace = "br.com.digital.order"
    compileSdk = 35

    defaultConfig {
        applicationId = "br.com.digital.order"
        minSdk = 23
        targetSdk = 34
        versionCode = 8
        versionName = "1.0.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val proprieties = Properties()
    proprieties.load(project.rootProject.file("local.properties").inputStream())

    signingConfigs {
        create("release") {
            storeFile = file(proprieties.getProperty("STORE_FILE"))
            storePassword = proprieties.getProperty("STORE_PASSWORD")
            keyAlias = proprieties.getProperty("KEY_ALIAS")
            keyPassword = proprieties.getProperty("KEY_PASSWORD")
        }
    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(name = "proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField(
                type = "String",
                name = "BASE_URL_APP",
                value = "\"${proprieties.getProperty("API_KEY_DEV")}\""
            )
        }

        release {
            signingConfig = signingConfigs.getByName(name = "release")
            buildConfigField(
                type = "String",
                name = "BASE_URL_APP",
                value = "\"${proprieties.getProperty("API_KEY_PROD")}\""
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
        compose = true
    }
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
    testImplementation(libs.junit)
    implementation(libs.material)
    implementation(libs.datastore.preferences)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.koin.androidx.compose)
    implementation(libs.logging.interceptor)
    implementation(libs.okhttp)
    implementation(libs.converter.gson)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.mlkit.translate)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}