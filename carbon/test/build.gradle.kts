import com.gabrieldrn.carbon.Configuration
import org.jetbrains.compose.ExperimentalComposeLibrary

plugins {
    id("carbon.kmp.library")
    id("carbon.detekt")
}

carbonLibrary {
    artifactId.set("carbon-test")
    artifactGroup.set(Configuration.artifactGroup)
    version.set(Configuration.version)
}

kotlin {
    @OptIn(ExperimentalComposeLibrary::class)
    sourceSets {
        commonMain.dependencies {
            api(project(":carbon:common"))
            implementation(compose.foundation)
            implementation(compose.uiTest)
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
    namespace = "com.gabrieldrn.carbon.test"

    dependencies {
        debugImplementation(libs.androidx.compose.ui.test.manifest)
    }
}
