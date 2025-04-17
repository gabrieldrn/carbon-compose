import com.gabrieldrn.carbon.Configuration
import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    id("carbon.kmp.library")
    id("carbon.detekt")
}

carbonLibrary {
    artifactId.set("carbon-common")
    artifactGroup.set(Configuration.artifactGroup)
    version.set(Configuration.version)
}

kotlin {
    @OptIn(ExperimentalComposeLibrary::class)
    sourceSets {
        commonMain.dependencies {
            implementation(compose.foundation)
            implementation(compose.ui)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(compose.uiTest)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}

android {
    namespace = "com.gabrieldrn.carbon.common"

    dependencies {
        debugImplementation(libs.androidx.compose.ui.test.manifest)
    }
}
