plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.diceplanet.app"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.diceplanet.app"
        minSdk = 24
        targetSdk = 36
        versionCode = 4
        versionName = "1.4"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {

    // AndroidX
    implementation(libs.androidx.activity.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)

    // Material
    implementation(libs.material)

    // Navigation
    implementation(libs.androidx.navigation.fragment)

    // RecyclerView
    implementation(libs.androidx.recyclerview)

    // Retrofit
    implementation("androidx.palette:palette-ktx:1.0.0")

    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)

    // QR CODE
    implementation("com.journeyapps:zxing-android-embedded:4.3.0")
    implementation("com.google.zxing:core:3.5.3")
}