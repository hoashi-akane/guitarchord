plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.service)
    alias(libs.plugins.crashlytics)
    alias(libs.plugins.licensee)
}

android {
    namespace = "jp.ahoashi.guitarchord"
    compileSdk = 36

    defaultConfig {
        androidResources.localeFilters.addAll(listOf("ja"))

        applicationId = "jp.ahoashi.guitarchord"
        minSdk = 26
        targetSdk = 36
        versionCode = 10101
        versionName = "1.1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    buildFeatures {
        compose = true
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

licensee {
    allow("Apache-2.0")
    allow("MIT")
    allow("BSD-2-Clause")
    allow("BSD-3-Clause")
    allow("ISC")
    allowUrl("https://developer.android.com/studio/terms.html")
    allowUrl("https://developers.google.com/terms/")
}

// ./gradlew :app:copyLicensesToResources でライセンスJSONを再生成する
tasks.register<Copy>("copyLicensesToResources") {
    dependsOn("licenseeAndroidRelease")
    from(layout.buildDirectory.file("reports/licensee/androidRelease/artifacts.json"))
    into(rootProject.file("shared/src/commonMain/composeResources/files"))
    rename { "oss_licenses.json" }
}

dependencies {
    implementation(project(":shared"))
    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.datastore)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.compose.viewmodel)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material.icon)
    implementation(platform(libs.firebase.bom))
    implementation(libs.analytics)
    implementation(libs.crashlytics)
    implementation(libs.play.services.ads)
    implementation(libs.ump)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.androidx.junit)
    testImplementation("androidx.test:core:1.7.0")
    testImplementation("org.robolectric:robolectric:4.16.1")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
