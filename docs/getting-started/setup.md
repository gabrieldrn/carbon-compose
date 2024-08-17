# Setting up Carbon

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

!!! info
    Only `White` and `Grey 100` are currently available.

- The `theme` argument specifies the main Carbon theme to use in regular screens. By default, the composable sets either
  the `White` or `Grey 100` theme depending on the system's dark mode setting.
- The `uiShellInlineTheme` argument defines the 
  [inline theming](https://carbondesignsystem.com/elements/color/usage#inline-theming) used by Carbon UI shell 
  components.

!!! warning
    UI shell elements are still a work in progress. However, a UI shell header is available with limited implementation 
    and documentation.

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
