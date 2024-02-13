package carbon.compose.buildlogic

import com.android.build.api.dsl.CommonExtension

/**
 * Configure Android extension with common settings.
 */
internal fun CommonExtension<*, *, *, *, *>.configureKotlinAndroidCommon() {

    compileSdk = Constants.Versions.COMPILE_SDK

    defaultConfig {
        minSdk = Constants.Versions.MIN_SDK
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    compileOptions {
        sourceCompatibility = Constants.Versions.JAVA
        targetCompatibility = Constants.Versions.JAVA
    }

    kotlinOptions {
        jvmTarget = Constants.Versions.JAVA.toString()
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = Constants.Versions.COMPOSE_COMPILER
    }
}
