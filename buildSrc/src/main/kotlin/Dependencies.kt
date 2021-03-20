package cz.ackee.composeworkshop

import org.gradle.api.JavaVersion

object Versions {
    const val kotlin = "1.4.31"
    const val compose = "1.0.0-beta02"

    val java = JavaVersion.VERSION_1_8
}

object Libs {

    object Compose {

        const val runtime = "androidx.compose.runtime:runtime:${Versions.compose}"
        const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:${Versions.compose}"
        const val ui = "androidx.compose.ui:ui:${Versions.compose}"
        const val material = "androidx.compose.material:material:${Versions.compose}"
        const val materialIcons = "androidx.compose.material:material-icons-extended:${Versions.compose}"
        const val foundation = "androidx.compose.foundation:foundation:${Versions.compose}"
        const val foundationLayout = "androidx.compose.foundation:foundation-layout:${Versions.compose}"
        const val animation = "androidx.compose.animation:animation:${Versions.compose}"
        const val uiTooling = "androidx.compose.ui:ui-tooling:${Versions.compose}"
        const val navigation = "androidx.navigation:navigation-compose:1.0.0-alpha09"
        const val activity = "androidx.activity:activity-compose:1.3.0-alpha02"
    }

    object Kotlin {

        const val kotlin = "org.jetbrains.kotlin:kotlin-stdlib:${Versions.kotlin}"
        const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"

        private const val coroutinesVersion = "1.4.3"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion"
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion"
    }

    object AndroidCore {

        const val coreKtx = "androidx.core:core-ktx:1.5.0-alpha04"
        const val activityKtx = "androidx.activity:activity-ktx:1.1.0"
        const val material = "com.google.android.material:material:1.1.0"
        const val appCompat = "androidx.appcompat:appcompat:1.3.0-alpha02"
    }

    object Paging {

        private const val version = "3.0.0-beta02"

        const val common = "androidx.paging:paging-common-ktx:$version"
        const val runtime = "androidx.paging:paging-runtime-ktx:$version"
        const val compose = "androidx.paging:paging-compose:1.0.0-alpha08"
    }

    object Lifecycle {

        private const val version = "2.3.0"

        const val common = "androidx.lifecycle:lifecycle-common-java8:$version"
        const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
        const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$version"

        const val compose = "androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha03"
    }

    object Room {

        private const val version = "2.2.6"

        const val ktx = "androidx.room:room-ktx:$version"
        const val compiler = "androidx.room:room-compiler:$version"
    }

    object Koin {

        private const val version = "2.2.2"

        const val core = "org.koin:koin-android:$version"
        const val scope = "org.koin:koin-androidx-scope:$version"
        const val viewModel = "org.koin:koin-androidx-viewmodel:$version"
    }

    object Network {

        private const val retrofitVersion = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofitVersion"
        const val retrofitMoshiConverter = "com.squareup.retrofit2:converter-moshi:$retrofitVersion"

        private const val moshiVersion = "1.11.0"
        const val moshi = "com.squareup.moshi:moshi:$moshiVersion"
        const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:$moshiVersion"
    }

    object Accompanist {

        private const val version = "0.6.2"
        const val coil = "com.google.accompanist:accompanist-coil:$version"
    }

    object Other {

        const val timber = "com.jakewharton.timber:timber:4.7.1"
    }
}

object TestingLibs {

    const val composeUiTest = "androidx.ui:ui-test:${Versions.compose}"
    const val junit = "junit:junit:4.13"
    const val mockK = "io.mockk:mockk:1.9.3"
    const val espresso = "androidx.test.espresso:espresso-core:3.3.0"
}