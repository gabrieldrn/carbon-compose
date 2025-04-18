# Using Carbon

## Theming

To provide all the necessary resources (such as colors and typography) for creating a UI with Carbon, use the 
`CarbonDesignSystem` root composable at the top of your Compose tree:

```kotlin
setContent {
    CarbonDesignSystem {
        // Your UI content
    }
}
```
[`fun CarbonDesignSystem()` :octicons-arrow-up-right-24:](https://gabrieldrn.github.io/carbon-compose/api/-carbon%20-compose/com.gabrieldrn.carbon/-carbon-design-system.html)

This will apply the IBM Plex type family to components and set the `White` and `Grey 100` themes as the default light 
and dark themes, respectively, depending on the theme setting of the current platform.

### Applying themes

The Carbon design system provides a set of [four themes](https://carbondesignsystem.com/elements/color/overview#themes) 
to use in your app, `White`, `Grey 10`, `Grey 90` and `Grey 100`.

You can apply these themes using the `theme` and `uiShellInlineTheme` arguments of `CarbonDesignSystem`:

```kotlin
CarbonDesignSystem(
    theme = WhiteTheme,
    uiShellInlineTheme = Gray100Theme
) {
    // Content
}
```

- The `theme` argument specifies the main Carbon theme to use in regular screens. By default, the composable sets either
  the `White` or `Grey 100` theme depending on the system's dark mode setting.
- The `uiShellInlineTheme` argument defines the 
  [inline theming](https://carbondesignsystem.com/elements/color/usage#inline-theming) used by Carbon UI shell 
  components.

!!! warning
    UI shell elements are still a work in progress. However, a UI shell header is available with limited implementation 
    and documentation.

#### Custom themes

If needed, you can customize existing themes to your liking, or create new ones.

```kotlin
val customTheme1 = WhiteTheme.copy(
    textPrimary = Color.Magenta
)

val customTheme2 = Theme(
    background = Color.Black,
    ...
)

@Composable
fun MyCarbonApp(
    content: @Composable () -> Unit
) {
    CarbonDesignSystem(
        theme = if (isSystemInDarkTheme()) {
            customTheme1
        } else {
            customTheme2
        },
        uiShellInlineTheme = Gray100Theme,
        content = content
    )
}

```

### Layering

`CarbonDesignSystem` also offers a `layer` argument to **globally** set the 
[layering token](https://carbondesignsystem.com/elements/color/usage/#layering-tokens), which maps the layering model 
onto components in your content.

```kotlin
CarbonDesignSystem(
    layer = Layer01
) {
    // Content
}
```

By setting this, all Carbon components will adjust their colors to follow the specified layer level.

#### Local layer

Some parts of your UI may require a higher layer level. You can use the `CarbonLayer` composable to apply the next upper
layer **locally** within your compose tree:

```kotlin
CarbonDesignSystem {
    // Content on Layer 00
    CarbonLayer {
        // Other content what will be on Layer 01
    }
}
```
[`fun CarbonLayer()` :octicons-arrow-up-right-24:](https://gabrieldrn.github.io/carbon-compose/api/-carbon%20-compose/com.gabrieldrn.carbon.foundation.color/-carbon-layer.html)

#### Layer Container Color

When working with layers, the background should be adjusted to reflect the corresponding layer.

Carbon Compose provides a `containerBackground` modifier that you can apply to any composable to automatically set the 
appropriate background:

```kotlin
Box(
    modifier = Modifier
        .fillMaxSize()
        .containerBackground()
) {
    Button()
}
```
[`fun Modifier.containerBackground()` :octicons-arrow-up-right-24:](https://gabrieldrn.github.io/carbon-compose/api/-carbon%20-compose/com.gabrieldrn.carbon.foundation.color/container-background.html)

### Typography

Carbon was designed to work seamlessly with the [IBM Plex](https://www.ibm.com/plex/) font family. Carbon Compose 
includes and provides access to the `TextStyle`s that map to all the 
[type sets in Carbon](https://carbondesignsystem.com/guidelines/typography/type-sets).

You can use the `Carbon.typography` accessor to apply those styles to your texts:

```kotlin
BasicText(
    text = "Lorem ipsum",
    style = Carbon.typography.bodyCompact01
)
```

[`class CarbonTypography` :octicons-arrow-up-right-24:](https://gabrieldrn.github.io/carbon-compose/api/-carbon%20-compose/com.gabrieldrn.carbon.foundation.text/-carbon-typography/index.html)

### Spacing scale

Carbon Compose also provides a [spacing scale](https://carbondesignsystem.com/guidelines/spacing/overview/), as detailed
by the spacing tokens. This scale is accessible through the `SpacingScale` singleton:

```kotlin
Box(
    modifier = Modifier.padding(top = SpacingScale.spacing03)
)
```

[`object SpacingScale` :octicons-arrow-up-right-24:](https://gabrieldrn.github.io/carbon-compose/api/-carbon%20-compose/com.gabrieldrn.carbon.foundation.spacing/-spacing-scale/index.html)

### Motion

Carbon provides [motion guidelines](https://carbondesignsystem.com/elements/motion/overview) for its components:

> Carbon components have motion built in for microinteractions. However, the motion design of the overarching UI — that 
> is, how the components interact with each other and enter and exit the page itself — is up to each product team to 
> implement. (...)

Just like the spacing scale, Carbon Compose provides ready-to-use constants and Bezier curves to implement motion into 
your UI:

```kotlin
val specFloat = tween<Float>(
    durationMillis = Motion.Duration.moderate01,
    easing = Motion.Standard.productiveEasing
)

val rotation by transition.animateFloat(
    transitionSpec = { specFloat },
    label = "Rotation"
) {
    if (it) 180f else 0f
}
```

[`object Motion` :octicons-arrow-up-right-24:](https://gabrieldrn.github.io/carbon-compose/api/-carbon%20-compose/com.gabrieldrn.carbon.foundation.motion/-motion/index.html)
