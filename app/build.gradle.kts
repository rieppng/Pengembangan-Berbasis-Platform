plugins {
    // id("com.android.application")
    // Jika baris di atas error, ganti dengan baris ini (versi catalog):
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.watchwatch.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.watchwatch.app"
        minSdk = 24
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        viewBinding = true   // auto-generate binding class tiap layout XML
        buildConfig = true   // akses BuildConfig.VERSION_NAME di Settings
    }
}

dependencies {
    // ── AndroidX Core ───────────────────────────────────────────────
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // ── Navigation Component (Bottom Nav + Fragment management) ──────
    implementation("androidx.navigation:navigation-fragment:2.8.4")
    implementation("androidx.navigation:navigation-ui:2.8.4")

    // ── Lifecycle: ViewModel & LiveData (MVVM pattern) ───────────────
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.8.7")
    implementation("androidx.lifecycle:lifecycle-livedata:2.8.7")

    // ── Retrofit2 (memanggil WordPress REST API) ─────────────────────
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    // ── OkHttp (HTTP client + logging untuk debug di Logcat) ─────────
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")

    // ── Glide (load & cache gambar dari URL secara efisien) ──────────
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // ── Room (SQLite wrapper untuk fitur Bookmark offline) ───────────
    implementation("androidx.room:room-runtime:2.6.1")
    annotationProcessor("androidx.room:room-compiler:2.6.1")

    // ── Jsoup (HTML parser: ekstrak link Shopee dari konten post) ────
    implementation("org.jsoup:jsoup:1.18.1")

    // ── Testing ──────────────────────────────────────────────────────
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}
