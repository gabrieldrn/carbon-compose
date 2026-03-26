import com.gabrieldrn.carbon.Configuration

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
    androidLibrary {
        namespace = "com.gabrieldrn.carbon.test"
    }

    sourceSets {
        commonMain.dependencies {
            api(project(":carbon-common"))
            implementation(libs.compose.foundation)
            implementation(libs.compose.uiTest)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.compose.uiTest)
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
        }
    }
}
