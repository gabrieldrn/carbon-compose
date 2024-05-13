package carbon.kmp.shared

import platform.UIKit.UIDevice

internal class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

internal actual fun getPlatform(): Platform = IOSPlatform()
