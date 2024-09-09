plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.mycomposedemo"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.mycomposedemo"
        minSdk = 24
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
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    //ktx
    implementation(AndroidX_KTX.fragment)
    implementation(AndroidX_KTX.activity)
    implementation(AndroidX_KTX.core)

    api(Tools.mmkv)

    //lifecycle
    implementation(Lifecycle_KTX.livedata)
    implementation(Lifecycle_KTX.viewmodel)

    implementation(Accompanist.systemuicontroller)
    implementation(Compose.composeConstraintLayout)

    //Coil库
    implementation ("io.coil-kt:coil-compose:2.0.0-rc03")

    implementation("io.sanghun:compose-video:1.2.0")
    implementation("androidx.media3:media3-exoplayer:1.1.0") // [Required] androidx.media3 ExoPlayer dependency
    implementation("androidx.media3:media3-session:1.1.0") // [Required] MediaSession Extension dependency
    implementation("androidx.media3:media3-ui:1.1.0") // [Required] Base Player UI

    //下拉刷新，上拉加载
    implementation(ComposeTools.pullRefresh)

    implementation ("com.google.code.gson:gson:2.10")
    //解析MarkDown文本
    implementation ("com.github.jeziellago:compose-markdown:0.5.2")

    //共享元素
    implementation ("androidx.compose.animation:animation:1.7.0-beta01")

    //OKHTTP
    implementation("com.squareup.okhttp3:okhttp:4.10.0")

    //implementation("me.omico.lux:lux-androidx-compose-material3-pullrefresh")
    implementation(Work.manager)
    implementation(libs.material)
    implementation(libs.androidx.graphics.shapes.android)
    implementation(libs.androidx.core.i18n)


    val nav_version = "2.7.7"

    implementation("androidx.navigation:navigation-compose:$nav_version")

    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.core.ktx)
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0")

    //m3主题色生成
    implementation("com.github.Kyant0:m3color:2024.4")

    //设置页面组件
    implementation(libs.composeSettings.ui)
    implementation(libs.composeSettings.ui.extended)
    //implementation("com.github.alorma.compose-settings:ui-tiles:$version")
    //implementation("com.github.alorma.compose-settings:ui-tiles-extended:$version")

    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}