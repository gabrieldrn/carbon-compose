<img src="https://github.com/user-attachments/assets/cf499dee-c867-4613-96a8-c018bdb3f112" width="150" height="150">

# Carbon | Compose Multiplatform

![Carbon catalog pres](https://github.com/user-attachments/assets/d55e6893-ab18-498f-abec-1e2998dfe293)

[![Kotlin](https://img.shields.io/badge/2.0.0-blue?logo=kotlin&logoColor=white&color=7F52FF)](http://kotlinlang.org)
[![Compose Multiplatform](https://img.shields.io/badge/1.6.11-green?logo=jetpackcompose&logoColor=white&color=4284F3)](https://www.jetbrains.com/lp/compose-multiplatform)

[![CI | compile & tests](https://github.com/gabrieldrn/carbon-compose/actions/workflows/ci-lib-workflow.yml/badge.svg)](https://github.com/gabrieldrn/carbon-compose/actions/workflows/ci-lib-workflow.yml)

A [Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) implementation of the [Carbon Design System](https://github.com/carbon-design-system/carbon).

**Supported platforms:**
- Android
- iOS
- Desktop

> [!CAUTION]
> This library is **still under development** and **unofficial**. Contributions are welcomed.

# 🤳 Catalog App

For testing purposes, this project provides a _catalog_ app for supported targets.

As this is a KMP project, please follow [those steps](https://www.jetbrains.com/help/kotlin-multiplatform-dev/multiplatform-setup.html) to setup your environment accordingly.

## Android 🤖

You can either run the app from the Android App run configuration in Android Studio, or compile an APK by executing the assemble task in a terminal:
```
./gradlew :catalog:assemble
```

## iOS 

To run the iOS catalog app, open the Xcode project located in `<git repo>/iosCatalog` and run it (⌘R).

## Desktop 🖥️

You can run the desktop version by executing the `desktopRun` Gradle task in a terminal:
```
./gradlew :catalog:desktopRun -DmainClass=com.gabrieldrn.carbon.catalog.MainKt
```

# ✏️ Usage

The Carbon Compose libraries tries to stay simple and respect Carbon guidelines as much as possible. Its usage is similar to the Jepack Compose implementation of [Material Design](https://m3.material.io/develop/android/jetpack-compose).

> [!NOTE]
> A dedicated documentation website will be created.

## Import

Add the dependency to your `build.gradle` file:

```gradle
dependencies {
    implementation("io.github.gabrieldrn:carbon:0.1.0")
}
```

Whatever you have an Android or a KMP project, this will import the library corresponding to the targeted platform.

## Compose tree root

To provide all resources (colors and typography) necessary to create a UI with Carbon, use the `CarbonDesignSystem` root composable at the top of your Compose tree:

```kotlin
setContent {
    CarbonDesignSystem {
        // Your UI content
    }
}
```

This will apply the IBM Plex type family to components + White and Grey 100 themes as the light and dark themes respectively, according to the current platform theme setting.

## Available UI components and structural elements ("foundation")

> [!WARNING]
> Only few components with limited implementations are available at the moment. If you seek documentation about a component, please read written KDoc in the source code until the dedicated documentation website is available.

### Foundation

- Themes
    - White
    - Grey100
- Typography (IBM Plex)
- Layering

### Components

- Button
- Checkbox
- Dropdown
- Loading
- Multi-select
- Progress bar
- Radio button
- Text input
- Toggle
