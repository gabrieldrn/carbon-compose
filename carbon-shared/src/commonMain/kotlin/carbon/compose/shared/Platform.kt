package carbon.compose.shared

internal interface Platform {
    val name: String
}

internal expect fun getPlatform(): Platform
