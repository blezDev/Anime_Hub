import com.android.builder.model.v2.models.Versions

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")

}

android {
    namespace = "com.blez.anime_player_compose"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.blez.anime_player_compose"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {

        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2024.01.00"))
    implementation("androidx.compose.ui:ui:1.7.0-alpha01")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3:1.2.0-rc01")
    implementation("com.google.firebase:firebase-analytics:21.5.1")
    implementation("com.google.firebase:firebase-crashlytics:18.6.2")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.activity:activity-compose:1.8.2")
    //Google one-tap sign
    implementation("com.github.stevdza-san:OneTapCompose:1.0.11")

    //coil
    implementation("io.coil-kt:coil-compose:2.5.0")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:okhttp:5.0.0-alpha.12")
    implementation("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.12")


    //Hilt
    implementation("com.google.dagger:hilt-android:2.50")
    ksp("com.google.dagger:hilt-android-compiler:2.50")


    //from quickstart
    implementation("com.google.devtools.ksp:symbol-processing-api:1.9.22-1.0.17")

    //Paging 3
    implementation("androidx.paging:paging-runtime-ktx:3.2.1")
    // optional - Jetpack Compose integration
    implementation("androidx.paging:paging-compose:3.3.0-alpha02")



    // Compose dependencies
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.navigation:navigation-compose:2.7.6")
    implementation("androidx.compose.material:material-icons-extended:1.6.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")


    implementation("androidx.compose.animation:animation:1.6.0")

    //Lottie Animation
    implementation("com.airbnb.android:lottie-compose:6.1.0")


    dependencies {
        val media3_version = "1.2.1"

        // For media playback using ExoPlayer
        implementation("androidx.media3:media3-exoplayer:$media3_version")

        // For DASH playback support with ExoPlayer
        implementation("androidx.media3:media3-exoplayer-dash:$media3_version")
        // For HLS playback support with ExoPlayer
        implementation("androidx.media3:media3-exoplayer-hls:$media3_version")
        // For RTSP playback support with ExoPlayer
        implementation("androidx.media3:media3-exoplayer-rtsp:$media3_version")
        // For ad insertion using the Interactive Media Ads SDK with ExoPlayer
        implementation("androidx.media3:media3-exoplayer-ima:$media3_version")

        // For loading data using the Cronet network stack
        implementation("androidx.media3:media3-datasource-cronet:$media3_version")
        // For loading data using the OkHttp network stack
        implementation("androidx.media3:media3-datasource-okhttp:$media3_version")
        // For loading data using librtmp
        implementation("androidx.media3:media3-datasource-rtmp:$media3_version")

        // For building media playback UIs
        implementation("androidx.media3:media3-ui:$media3_version")
        // For building media playback UIs for Android TV using the Jetpack Leanback library
        implementation("androidx.media3:media3-ui-leanback:$media3_version")

        // For exposing and controlling media sessions
        implementation("androidx.media3:media3-session:$media3_version")

        // For extracting data from media containers
        implementation("androidx.media3:media3-extractor:$media3_version")

        // For integrating with Cast
        implementation("androidx.media3:media3-cast:$media3_version")

        // For scheduling background operations using Jetpack Work's WorkManager with ExoPlayer
        implementation("androidx.media3:media3-exoplayer-workmanager:$media3_version")

        // For transforming media files
        implementation("androidx.media3:media3-transformer:$media3_version")

        // Utilities for testing media components (including ExoPlayer components)
        implementation("androidx.media3:media3-test-utils:$media3_version")
        // Utilities for testing media components (including ExoPlayer components) via Robolectric
        implementation("androidx.media3:media3-test-utils-robolectric:$media3_version")

        // Common functionality for media database components
        implementation("androidx.media3:media3-database:$media3_version")
        // Common functionality for media decoders
        implementation("androidx.media3:media3-decoder:$media3_version")
        // Common functionality for loading data
        implementation("androidx.media3:media3-datasource:$media3_version")
        // Common functionality used across multiple media libraries
        implementation("androidx.media3:media3-common:$media3_version")

        implementation("androidx.credentials:credentials:1.3.0-alpha01")
        implementation("androidx.credentials:credentials-play-services-auth:1.3.0-alpha01")
        implementation("com.google.android.libraries.identity.googleid:googleid:1.1.0")
    }


    //Google Font
    implementation("androidx.compose.ui:ui-text-google-fonts:1.6.1")

    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")

    ksp("androidx.room:room-compiler:$room_version")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$room_version")



    implementation("androidx.navigation:navigation-compose:2.7.7")

}