package carbon.kmp

object Configuration {
    const val majorVersion = 0
    const val minorVersion = 1
    const val patchVersion = 0
    const val versionName = "$majorVersion.$minorVersion.$patchVersion"
    const val versionCode = 1
    const val snapshotVersionName = "$majorVersion.$minorVersion.${patchVersion + 1}-SNAPSHOT"
    const val artifactGroup = "carbon.compose"
}
