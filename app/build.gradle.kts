import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
}

android {
    namespace = "com.vdemelo.marvel"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vdemelo.marvel"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String",
            "PUBLIC_API_KEY",
            gradleLocalProperties(rootDir).getProperty("PUBLIC_API_KEY")
        )
        buildConfigField(
            "String",
            "PRIVATE_API_KEY",
            gradleLocalProperties(rootDir).getProperty("PRIVATE_API_KEY")
        )
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
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
}

val pagingVersion: String by rootProject.extra

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    //Koin
    implementation("io.insert-koin:koin-android:3.5.0")
    implementation("io.insert-koin:koin-androidx-navigation:3.5.0")
    testImplementation("io.insert-koin:koin-test:3.5.0")
    testImplementation("io.insert-koin:koin-test-junit4:3.5.0")

    // Gson
    implementation("com.google.code.gson:gson:2.10")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp 3
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    // Paging 3
    implementation("androidx.paging:paging-runtime-ktx:$pagingVersion")
    testImplementation("androidx.paging:paging-common-ktx:$pagingVersion")

    // Room
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-paging:2.6.1")

    //Picasso
    implementation("com.squareup.picasso:picasso:2.71828")

    //Tests
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    testImplementation("androidx.arch.core:core-testing:2.2.0")
    androidTestImplementation("androidx.arch.core:core-testing:2.2.0")
    // Optional -- Robolectric environment
    testImplementation("androidx.test:core-ktx:1.5.0")
    // Optional -- Mockito framework
    testImplementation("org.mockito:mockito-core:5.7.0")
    // Optional -- mockito-kotlin
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    // Optional -- Mockk framework
    testImplementation("io.mockk:mockk:1.13.9")

    // My Personal Libs
    implementation(project(":common"))

}
