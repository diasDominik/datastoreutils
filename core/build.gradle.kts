import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.mavenPublish)
    signing
}

kotlin {
    androidTarget {
        publishLibraryVariants("release")
        compilations.all {
            compileTaskProvider.configure {
                compilerOptions {
                    jvmTarget.set(JvmTarget.JVM_1_8)
                }
            }
        }
    }
    
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    macosX64()
    macosArm64()

    sourceSets {
        commonMain.dependencies {
            implementation(libs.datastore.preferences)
        }
    }
}

android {
    namespace = "uk.dominikdias.datastoreutils"
    compileSdk = 36
    defaultConfig {
        minSdk = 23
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

signing {
    isRequired = gradle.taskGraph.allTasks.any { it !is PublishToMavenLocal }
}
