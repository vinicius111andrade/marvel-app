import java.io.FileInputStream
import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
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

        try {
            val localProperties = Properties()
            FileInputStream("local.properties").use { inputStream ->
                localProperties.load(inputStream)
            }

            val publicApiKey: String? = localProperties.getProperty("PUBLIC_API_KEY")
            buildConfigField("String", "publicApiKey", "\"$publicApiKey\"")
        } catch (e: Exception) {
            println("Failed to load local.properties file or to read PUBLIC_API_KEY variable: ${e.message}")
            buildConfigField("String", "publicApiKey", "")
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

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    //Koin
    implementation("io.insert-koin:koin-android:3.5.0")
    implementation("io.insert-koin:koin-androidx-navigation:3.5.0")
    testImplementation("io.insert-koin:koin-test-junit4:3.5.0")

    // Gson
    implementation("com.google.code.gson:gson:2.10")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp 3
    implementation("com.squareup.okhttp3:logging-interceptor:4.10.0")

    //Picasso
    implementation("com.squareup.picasso:picasso:2.8")

    // My Personal Libs
    implementation(project(":common"))

}
