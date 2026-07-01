import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.kotlin.multiplatform.library)
    alias(libs.plugins.compose.multiplatform)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    android {
        namespace = "jp.ahoashi.guitarchord.shared"
        compileSdk = 36
        minSdk = 26
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_17)
        }
        androidResources.enable = true
    }

    val xcframeworkName = "shared"
    val xcFramework = XCFramework(xcframeworkName)
    listOf(iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = xcframeworkName
            isStatic = true
            binaryOption("bundleId", "jp.ahoashi.guitarchord.$xcframeworkName")
            xcFramework.add(this)
        }
    }

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(compose.materialIconsExtended)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.lifecycle.viewmodel)
            implementation(libs.koin.core)
            implementation(libs.koin.compose.multiplatform)
            implementation(libs.koin.compose.viewmodel)
        }
        androidMain.dependencies {
            implementation(compose.preview)
        }
    }
}

compose.resources {
    publicResClass = true
    packageOfResClass = "jp.ahoashi.guitarchord.generated.resources"
}
