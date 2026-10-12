plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.mywishlistapp"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.example.mywishlistapp"
        minSdk = 27
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.material3)
    // Core Room runtime and KTX extensions
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)

    // Annotation processor using KSP
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.navigation.compose)

    // 1. Import the Compose BOM platform definition
    implementation(platform(libs.androidx.compose.bom))

    // 2. Add individual Compose library dependencies (Notice they omit explicit version strings)
    implementation(libs.androidx.activity.compose) // Integration with ComponentActivity
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3) // Material Design 3 components

    // 3. Add debug instrumentation (For Layout Inspector & Previews)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.junit)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    debugImplementation(libs.androidx.compose.ui.tooling)
}