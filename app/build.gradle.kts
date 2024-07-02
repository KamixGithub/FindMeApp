plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.example.findme"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.findme"
        minSdk = 24
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)
    implementation(libs.legacy.support.v4)
    implementation(libs.recyclerview)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.gson)

    testImplementation(libs.mockito)
    testImplementation(libs.junit)
    testImplementation(libs.powermock)
    testImplementation(libs.modules)
    testImplementation(libs.robolectric)
    testImplementation(libs.byte.buddy)

    androidTestImplementation(libs.mockito)
    androidTestImplementation(libs.fragment)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.mockito.android)
    androidTestImplementation ("androidx.fragment:fragment-testing:1.3.6")
    androidTestImplementation ("androidx.test.espresso:espresso-core:3.5.0")
    androidTestImplementation ("androidx.test.ext:junit:1.1.3")

}
