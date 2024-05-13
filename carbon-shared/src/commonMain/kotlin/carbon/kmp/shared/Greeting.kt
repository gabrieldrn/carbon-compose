package carbon.kmp.shared

public class Greeting {
    private val platform: Platform = getPlatform()

    public fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}
