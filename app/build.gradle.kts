plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.zavrsnirad"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.zavrsnirad"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    testOptions {
        animationsDisabled = true
    }
    viewBinding {
        enable = true
    }
}

dependencies {

    // Core & UI
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)

    // Lifecycle & ViewModel
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    // Networking
    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.gson)

    // Revolut SDK
    implementation(libs.revolut.merchantcardform)

    // --- TESTIRANJE ---

    // Unit Testing
    testImplementation(libs.testing.junit4)
    testImplementation(libs.testing.mockito.kotlin)
    testImplementation(libs.testing.arch.core)
    testImplementation(libs.testing.kotlinx.coroutines)

    // Instrumentation Testing
    androidTestImplementation(libs.testing.androidx.junit)
    androidTestImplementation(libs.testing.androidx.runner)
    androidTestImplementation(libs.testing.androidx.rules)
    androidTestImplementation(libs.testing.androidx.core)
    androidTestImplementation(libs.testing.espresso.core)
    androidTestImplementation(libs.testing.espresso.contrib)
    androidTestImplementation(libs.testing.espresso.intents)

    // Ne koristimo vise MockWebServer i UI Automator
    // androidTestImplementation(libs.testing.mockwebserver)
    // androidTestImplementation(libs.testing.uiautomator)
}