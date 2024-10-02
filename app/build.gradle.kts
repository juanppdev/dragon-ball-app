plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.kotlin.serialize)

    // Add the Google services Gradle plugin
    alias(libs.plugins.google.services)

    id("kotlin-kapt")

//    id("androidx.navigation.safeargs.kotlin")
//    alias(libs.plugins.androidx.navigation.safe.args)

    // Dagger Hilt
    alias(libs.plugins.google.dagger.hilt)
}

android {
    namespace = "com.mundocode.dragonballapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.mundocode.dragonballapp"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {

        forEach { buildType ->
            buildType.buildConfigField(
                "String",
                "WEB_CLIENT_ID",
                "\"${providers.gradleProperty("web_client_id").get()}\"",
            )
        }

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
    buildFeatures {
        buildConfig = true
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.kotlinx.serialization.core)

    implementation(libs.play.services.identity.credentials)

    //Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.runtime.livedata)
    kapt(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)

    // Android
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.googleid)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(libs.coil.compose)

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    //Firebase
    implementation(platform(libs.firebase.bom.v3223))
    implementation(libs.firebase.auth.ktx)
    implementation(libs.play.services.auth)
    // Declare the dependency for the Cloud Firestore library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation(libs.firebase.firestore)


    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation(libs.google.firebase.auth.ktx)
    implementation(libs.androidx.credentials)

    // Agregaremos las dependencias de los productos de Firebase que deseas usar
    implementation(libs.firebase.analytics)
    implementation(libs.firebase.messaging)

    // Dagger Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.android.navigation.compose)
    kapt(libs.hilt.android.compiler)

    implementation(libs.androidx.datastore.preferences)

    implementation("com.google.android.gms:play-services-auth:21.2.0")
    implementation("com.google.firebase:firebase-auth-ktx:23.0.0")

    implementation("androidx.credentials:credentials:1.2.2")
    implementation("androidx.credentials:credentials-play-services-auth:1.2.2")
    implementation("com.google.android.libraries.identity.googleid:googleid:.1.2.2")

    implementation("com.kiwi.navigation-compose.typed:core:0.10.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.5.0")


}