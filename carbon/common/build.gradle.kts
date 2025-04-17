import com.gabrieldrn.carbon.Configuration

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
    sourceSets {
        commonMain.dependencies {
            implementation(compose.foundation)
            implementation(compose.ui)
        }
    }
}

android {
    namespace = "com.gabrieldrn.carbon.common"
}
