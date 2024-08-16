# Setup a UI ✏️

## Root composable

To provide all resources (colors and typography) necessary to create a UI with Carbon, use the `CarbonDesignSystem` root
composable at the top of your Compose tree:

```kotlin
setContent {
    CarbonDesignSystem {
        // Your UI content
    }
}
```

This will apply the IBM Plex type family to components + set `White` and `Grey 100` themes as the default light and dark
themes respectively depending on the theme setting of the current platform.

### Configuration

