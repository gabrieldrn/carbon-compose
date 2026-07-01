# carbon-compose — Claude Context

This is a **Kotlin Multiplatform (KMP) Compose library** implementing [IBM's Carbon Design System](https://carbondesignsystem.com/).

**Carbon is a standalone design system. Never use Material3 or any other design system alongside it.**

---

## Module structure

| Module | Purpose |
|---|---|
| `carbon` | All UI components (main library, published) |
| `carbon-common` | Shared semantic utilities (published) |
| `carbon-test` | Shared test helpers (published) |
| `catalog` / `catalog-android` | Demo apps (all platforms) |
| `code-gen` / `doc-parser` | Build tooling — token code generation |

**Targets:** Android, iOS (arm64 + simulatorArm64), Desktop (JVM), WasmJS.

Build convention plugins: `id("carbon.kmp.library")` and `id("carbon.detekt")`.

---

## Never do

- **No Material3 or any other design system** — Carbon is standalone; never import `androidx.compose.material3.*` or equivalent
- **No hardcoded `dp` values** for spacing — always use `SpacingScale.*`
- **No hardcoded colors** — always derive from `Carbon.theme` tokens
- **No `com.gabrieldrn.carbon.foundation.text.Text`** internal wrapper — use `BasicText` from `androidx.compose.foundation.text`
- **No auto-generated resources** — add fonts/drawables/strings manually to the resources directory

---

## Component implementation workflow

1. **Consult the Component Implementation Reference** section below — all patterns are documented. Only read an existing component directly if encountering an edge case not covered there.
2. **Consult spec**: `https://carbondesignsystem.com/components/<name>/usage/`
3. **Implement files** per anatomy below
4. **Always implement the catalog demo screen** in the same task (see Catalog workflow)

---

## Component anatomy

Components live in:
```
carbon/src/commonMain/kotlin/com/gabrieldrn/carbon/<component>/
```

File breakdown is **flexible** — apply Separation of Concerns based on complexity:

| File | Visibility | Purpose |
|---|---|---|
| `ComponentName.kt` | `public` | Main composable(s) + KDoc + `@Preview` region |
| `ComponentNameColors.kt` | `internal` | Color logic derived from `Theme` |
| `ComponentNameSize.kt` | `public expect` | `expect enum class`, actual per platform |
| `ComponentNameTestTags.kt` | `internal` | `object` with `const val` tag strings |
| `ComponentNameImpl.kt` | `internal` | Layout/drawing implementation, separated from public API |

Simpler components may need fewer files; complex ones may split further (e.g., `domain/`, `base/` subpackages).

---

## Component Implementation Reference

### Composable signature conventions

Parameter order (strict):
1. Required state/value params (`value`, `checked`, `selected`, `state`)
2. Required callback params (`onClick`, `onValueChange`, `onToggleChange`)
3. `modifier: Modifier = Modifier`
4. Optional content params (label, placeholder, helperText, iconPainter)
5. Optional type/size/state enums — all with defaults
6. `interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }` — always last

```kotlin
@Composable
public fun Toggle(
    isToggled: Boolean,                          // 1. required state
    onToggleChange: (Boolean) -> Unit,           // 2. required callback
    modifier: Modifier = Modifier,               // 3. modifier
    label: String = "",                          // 4. optional content
    actionText: String = "",
    interactiveState: ToggleInteractiveState = ToggleInteractiveState.Default,  // 5. optional enums
    toggleType: ToggleType = ToggleType.Default,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }  // 6. always last
)
```

---

### Color class pattern

Full canonical template:

```kotlin
@Immutable
internal class ComponentColors private constructor(
    private val theme: Theme,
    private val variant: ComponentVariant,  // add per-color dependencies as constructor params
) {
    // Static colors — computed once from theme tokens
    val containerColor: Color = when (variant) {
        ComponentVariant.Primary -> theme.buttonPrimary
        ComponentVariant.Secondary -> theme.buttonSecondary
    }

    // Dynamic colors — @Composable returning State<Color> via rememberUpdatedState
    @Composable
    fun borderColor(state: ComponentState): State<Color> =
        rememberUpdatedState(newValue = when (state) {
            ComponentState.Disabled -> Color.Transparent
            ComponentState.ReadOnly -> theme.borderSubtleColor(Carbon.layer)
            else -> theme.borderStrongColor(Carbon.layer)
        })

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ComponentColors) return false
        if (theme != other.theme) return false
        if (variant != other.variant) return false
        return true
    }

    override fun hashCode(): Int {
        var result = theme.hashCode()
        result = 31 * result + variant.hashCode()
        return result
    }

    companion object {
        @Composable
        fun colors(variant: ComponentVariant): ComponentColors =
            ComponentColors(Carbon.theme, variant)
    }
}
```

Rules:
- `private constructor` always
- `rememberUpdatedState` for state-driven colors — never raw `remember`
- Extend `SelectableColors(theme)` for checkbox/radio/toggle
- `equals`/`hashCode` must cover **all** constructor params

---

### Size enum pattern (expect/actual)

```kotlin
// commonMain — ComponentSize.kt
public expect enum class ComponentSize {
    Small,
    Medium,
    Large;
}

// androidMain — ComponentSize.android.kt
public actual enum class ComponentSize {
    @Deprecated(
        message = SMALL_TOUCH_TARGET_SIZE_MESSAGE,
        replaceWith = ReplaceWith("Large")
    )
    Small,
    @Deprecated(
        message = SMALL_TOUCH_TARGET_SIZE_MESSAGE,
        replaceWith = ReplaceWith("Large")
    )
    Medium,
    Large;
}
```

- `SMALL_TOUCH_TARGET_SIZE_MESSAGE` is a shared constant — use the same one across all components
- Size-to-dp/padding extension functions go in the `commonMain` file alongside the `expect` declaration
- Assess per-platform, per-component which sizes fall below accessibility guidelines

---

### State & interaction management

**Animated colors via `Animatable` + `LaunchedEffect` (for components needing smooth transitions):**
```kotlin
val containerColor = remember(colors) { Animatable(colors.containerColor) }
LaunchedEffect(isEnabled, isPressed, isHovered, colors) {
    containerColor.animateTo(
        targetValue = when {
            !isEnabled -> colors.containerDisabledColor
            isPressed  -> colors.containerActiveColor
            isHovered  -> colors.containerHoverColor
            else       -> colors.containerColor
        },
        animationSpec = tween(...)
    )
}
```

**Simpler animation via `animateColorAsState`:**
```kotlin
val backgroundColor: Color by animateColorAsState(
    targetValue = colors.backgroundColor(isEnabled, isToggled).value,
    animationSpec = animationSpec,
    label = "Component background color"
)
```

**Canvas conditional rendering via `derivedStateOf`:**
```kotlin
val drawBorder by remember(borderColor) {
    derivedStateOf { borderColor != Color.Transparent }
}
```

**Platform-specific press state:** When press timing differs between Android and Desktop/WasmJs, extract an `internal expect fun rememberIsXxxPressed(...)` with platform actuals. Desktop/WasmJs: `collectIsPressedAsState()`. Android: `LaunchedEffect` waiting for animation completion to avoid press-state flicker on quick taps.

---

### Modifier chain conventions

Order: **size → draw → border → interaction → pointer affordance**

```kotlin
modifier
    .requiredSize(...)                    // 1. size constraints
    .drawBehind { drawRect(color) }       // 2. background (before border)
    .border(width, color)                 // 3. border
    .clickable(                           // 4. interaction
        interactionSource = interactionSource,
        indication = indication,
        onClick = onClick,
        enabled = isEnabled,
        role = Role.Button
    )
    .pointerHoverIcon(PointerIcon.Hand)   // 5. always last
```

Selectable semantic modifier variants:
```kotlin
// Read-only state:
Modifier.readOnly(
    role = Role.Checkbox,
    interactionSource = interactionSource,
    state = state,
    mergeDescendants = true
)

// Tri-state (checkbox):
Modifier.triStateToggleable(
    state = state,
    interactionSource = interactionSource,
    enabled = isEnabled,
    onClick = onClick,
    indication = null,
    role = Role.Checkbox
)

// Two-state (toggle):
Modifier.toggleable(
    value = isToggled,
    interactionSource = interactionSource,
    enabled = isEnabled,
    onValueChange = onToggleChange,
    indication = null,
    role = Role.Switch
)
```

---

### Drawing (Canvas & drawBehind)

`drawBehind` — solid background only:
```kotlin
.drawBehind { drawRect(color = backgroundColor) }
```

`Canvas` with inset pattern for bordered shapes (inset by half border width to prevent clipping):
```kotlin
Canvas(modifier = Modifier.requiredSize(16.dp)) {
    val borderWidth = 2.dp.toPx()
    inset(borderWidth * .5f) {
        drawRoundRect(color = backgroundColor, cornerRadius = CornerRadius(2f.dp.toPx()))
    }
    if (drawBorder) {
        inset(borderWidth * .5f) {
            drawRoundRect(
                color = borderColor,
                style = Stroke(borderWidth),
                cornerRadius = CornerRadius(2f.dp.toPx())
            )
        }
    }
}
```

`translate` for positioned sub-elements:
```kotlin
translate(left = handleXPos, top = handleYPos) {
    drawRoundRect(color = handleColor, size = Size(size, size), cornerRadius = CornerRadius(size))
    translate(left = iconOffset.x, top = iconOffset.y) {
        icon?.draw(size = intrinsicSize, colorFilter = ColorFilter.tint(iconColor))
    }
}
```

Complex draw logic → extract into `private fun DrawScope.drawXxx()` extension functions.

---

### KDoc conventions

Public composable function — exact structure:
```kotlin
/**
 * # Component Name
 * One-liner summary of the component's purpose.
 *
 * ## Overview
 * 2-3 sentences on general purpose and behavior.
 *
 * ### When to use
 * Guidance on appropriate use cases.
 *
 * ### When not to use
 * Anti-patterns or when to prefer alternatives.
 *
 * (From [Component Name documentation](https://carbondesignsystem.com/components/<name>/usage/))
 *
 * @param label Text that informs the user about the content they need to enter.
 * @param value The input [String] text to be shown.
 * @param onValueChange The callback triggered when the input service updates the text.
 * @param modifier The modifier to be applied to the component.
 * @param state The [ComponentState] of the component.
 * @param interactionSource The [MutableInteractionSource] representing the stream of interactions.
 */
```

Rules:
- Every `public` param → one `@param` tag, no exceptions
- No `@return` (composables return `Unit`)
- `@throws IllegalArgumentException` only when the code actually throws
- Data class params → `@property` tags (not `@param`)
- Enum class → class-level doc + per-value `/** Usage: ... */` one-liner
- `@ExperimentalCarbonApi` → before `@Composable` when API is unstable

---

### Preview conventions

```kotlin
// region Previews

private class ComponentPreviewParameterProvider :
    PreviewParameterProvider<ParamType> {
    override val values: Sequence<ParamType>
        get() = ParamType.entries.asSequence()
        // For combinations: ButtonType.entries.flatMap { t -> ButtonSize.entries.map { s -> t to s } }.asSequence()
}

@Preview(group = "State name")  // group organizes multi-state previews
@Composable
private fun ComponentPreview(
    @PreviewParameter(ComponentPreviewParameterProvider::class)
    param: ParamType,
) {
    CarbonDesignSystem {
        Box(modifier = Modifier.padding(SpacingScale.spacing05)) {
            ComponentName(/* param */)
        }
    }
}

// endregion
```

Rules:
- Always `private`, always at **end of file**, always `CarbonDesignSystem { }` wrapper
- `// region Previews ... // endregion` delimiters required
- For selectable components reuse `InteractiveStatePreviewParameterProvider` from `common.selectable`
- Provider naming: `ComponentNamePreviewParameterProvider`

---

### Testing conventions

File structure in `commonTest/.../carbon/<component>/`:
- `ComponentTest.kt` — layout/behavior/interaction tests
- `ComponentColorsTest.kt` — color token correctness across themes/layers

**Behavioral test template:**
```kotlin
class ComponentTest {
    // Class-level mutable state — set before each assertion, not recreated per test
    private var interactiveState by mutableStateOf<SelectableInteractiveState>(
        SelectableInteractiveState.Default
    )

    private fun ComposeUiTest.setup() {
        setContent {
            CarbonDesignSystem {
                ComponentName(interactiveState = interactiveState, ...)
            }
        }
    }

    @Test
    fun component_condition_expectedBehavior() = runComposeUiTest {
        setup()
        onNodeWithTag(ComponentTestTags.ROOT, useUnmergedTree = true).assertIsDisplayed()

        interactiveState = SelectableInteractiveState.Disabled
        waitForIdle()
        onNodeWithTag(ComponentTestTags.ROOT).assertIsNotEnabled()
    }
}
```

**Color test template:**
```kotlin
class ComponentColorsTest : BaseColorsTest() {  // or BaseSelectableColorsTest for selectable
    @Test
    fun componentColors_someColor_isCorrect() = runComposeUiTest {
        forAllLayersAndStates(ComponentState.entries) { state, layer ->
            assertEquals(
                expected = when (state) { /* expected token */ },
                actual = ComponentColors.colors(state).someColor,
                message = "State: $state, Layer: $layer"
            )
        }
    }
}
```

- `forEachParameter<A, B, C, ...>()` — use for multi-param combinations to avoid Android activity recreation
- `@AndroidExcluded` — mark tests that cannot run on Android (filtered via Gradle)
- Node finding: `onNodeWithTag(tag, useUnmergedTree = true)` for merged semantic trees
- Semantic assertions from `:carbon-test`: `assertIsReadOnly()`, `assertHasImageVector(name)`

---

### Catalog demo conventions

File: `catalog/src/commonMain/.../catalog/<component>/ComponentDemoScreen.kt`

```kotlin
// Define variants only if the component has distinct UI variants
private enum class ComponentVariant { Default, Icon }
private val componentVariants = ComponentVariant.entries.map { TabItem(it.name) }

@Composable
fun ComponentDemoScreen(modifier: Modifier = Modifier) {
    var selectedState by rememberSaveable { mutableStateOf(ComponentState.Default) }
    var isEnabled by rememberSaveable { mutableStateOf(true) }

    DemoScreen(
        variants = componentVariants,         // omit → use simple DemoScreen(demoContent) overload
        modifier = modifier,
        displayLayerParameter = false,        // true only if layer changes component appearance
        demoParametersContent = { variant ->
            // Enums → Dropdown with .toDropdownOptions()
            Dropdown(
                label = "State",
                placeholder = "Choose option",
                options = ComponentState.entries.toDropdownOptions(),
                selectedOption = selectedState,
                onOptionSelected = { selectedState = it },
            )
            // Booleans → Toggle
            Toggle(label = "Enabled", isToggled = isEnabled, onToggleChange = { isEnabled = it })
        },
        demoContent = { variant ->
            // LaunchedEffect(variant) { } to reset state on variant switch if needed
            ComponentName(
                interactiveState = selectedState,
                isEnabled = isEnabled,
                modifier = Modifier.fillMaxWidth()
            )
        }
    )
}
```

Register in `Destination.kt`:
```kotlin
ComponentName(
    title = "Component name",
    illustration = Res.drawable.tile_component_name,  // null + // TODO if not yet available
    route = "componentname",
    content = { modifier -> ComponentDemoScreen(modifier = modifier) }
),
```

---

## Catalog workflow

When implementing a new component, always add a catalog demo in the same task:

1. Create `XxxDemoScreen.kt` in `catalog/src/commonMain/kotlin/com/gabrieldrn/carbon/catalog/xxx/`
2. Add entry to `Destination.kt` enum with `route` and `content` lambda
3. For `illustration`: attempt to extract from `https://carbondesignsystem.com/components/overview/components/`; if unavailable, set `illustration = null` and leave a `// TODO: add tile illustration` comment
4. Register in `CatalogNavGraph.kt` if additional navigation wiring is needed

---

## Build & run commands

```bash
# Run catalog — desktop is fastest for UI iteration
./gradlew :catalog:run
# Or use the IDE run config "🔥catalog [desktop]"

# Run all tests — mandatory for all platforms
./gradlew :carbon:allTests
./gradlew :carbon-common:allTests
./gradlew :carbon-test:allTests

# Static analysis
./gradlew detekt

# API verification
./gradlew apiCheck

# Update API dump — run when public API changes, then stage the .api files
./gradlew :carbon:apiDump
./gradlew :carbon-common:apiDump
```

---

## Pre-commit checklist (mandatory, in order)

1. `./gradlew :carbon:allTests` — all platforms, no exceptions
2. `./gradlew detekt` — zero issues
3. `./gradlew apiCheck` — binary compatibility
4. If public API changed: `./gradlew :carbon:apiDump` then stage updated `.api` files

---

## Theming system

- Wrap all content with `CarbonDesignSystem { }` — provides theme, layer, typography, and adaptation via `CompositionLocal`
- Access via `Carbon.theme`, `Carbon.layer`, `Carbon.typography`, `Carbon.adaptation`
- **Four themes:** `WhiteTheme`, `Gray10Theme`, `Gray90Theme`, `Gray100Theme`
- **Four layers:** `Layer00`–`Layer03`. Use `CarbonLayer { }` to step one level up
- Colors are **always** derived from `Carbon.theme` tokens — never hardcoded
- `Modifier.layerBackground()` applies the contextual background for the current layer
- Contextual token helpers live in `ContextualTokens.kt` (e.g., `Theme.layerColor(layer)`)
- `Theme` is **generated code** — do not modify it directly

---

## Foundation tokens

- **Spacing:** `SpacingScale.spacing01` (2dp) → `spacing13` (160dp). Always use these, never raw dp values for Carbon-defined spacing
- **Typography:** `Carbon.typography.bodyCompact01`, `label01`, `body01`, etc. (IBM Plex fonts)
- **Motion:** `Motion.Duration.fast01`, `Motion.Duration.fast02`, `Motion.Entrance.productiveEasing`

---

## Text

Always use **`BasicText`** from `androidx.compose.foundation.text` inside components.
Never use the internal `com.gabrieldrn.carbon.foundation.text.Text` wrapper.
For dynamic colors (hover/press states), use the `color: () -> Color` lambda overload of `BasicText`.

---

## Color classes

```kotlin
@Immutable
internal class ComponentColors private constructor(theme: Theme) {
    val someColor: Color = theme.someToken

    override fun equals(other: Any?): Boolean { ... }
    override fun hashCode(): Int { ... }

    companion object {
        @Composable
        fun colors(): ComponentColors = ComponentColors(Carbon.theme)
    }
}
```

- Always `@Immutable`, always `internal`
- For selectable components (checkbox, radio button, toggle), extend `SelectableColors(theme)`

---

## Selectable interactive state

Selectable components use the `SelectableInteractiveState` sealed class:
- `Default` — enabled, interactable
- `Disabled` — not interactable
- `ReadOnly` — focusable but not interactable
- `Error(errorMessage: String)` — shows error content below
- `Warning(warningMessage: String)` — shows warning content below

`ErrorContent` and `WarningContent` composables from `common.selectable` handle the auxiliary display.

---

## `expect`/`actual` for size enums

Size enums use `expect enum class` in `commonMain` and `actual enum class` in each platform source set (`androidMain`, `desktopMain`, `nativeMain`, `wasmJsMain`).

Enum entries that fall **below a platform's accessibility touch target guidelines** must be marked `@Deprecated` in that platform's `actual` declaration. Assess per-platform, per-component.

---

## API visibility

| Scope | What |
|---|---|
| `public` | Composable API, enums, state classes the consumer needs |
| `internal` | Colors, layout helpers, Impl files, TestTags, draw logic |

- `@ExperimentalCarbonApi` marks unstable APIs (requires opt-in)
- All `public` declarations must have **KDoc** including a link to the Carbon DS documentation page

---

## Icons

Icons are `internal val xyzIcon: ImageVector` properties in `carbon/.../icons/`.
Use them via `rememberVectorPainter(xyzIcon)`.
Available: CheckmarkFilled, Calendar, View, ViewOff, WarningFilled, WarningAlt, ErrorFilled, InformationFilled, Close, ChevronDown/Up/Left/Right.

---

## Resources

Resources (fonts, strings, drawables) are **integrated manually**. Do not auto-generate or assume resource entries exist — they must be added by hand to the resources directory and referenced explicitly.

---

## Testing

- All tests in `commonTest`, using `runComposeUiTest { }`
- Colors tests extend `BaseColorsTest` — use `forAllLayers` and `forAllLayersAndStates` helpers
- Use `forEachParameter()` for parameterized loops (avoids slow Activity recreation on Android)
- Test tags defined in `ComponentTestTags` internal objects, applied via `Modifier.testTag()`

---

## Previews

Each component file ends with a preview region:
```kotlin
// region Previews

@Preview
@Composable
private fun ComponentPreview() {
    CarbonDesignSystem {
        ComponentName(...)
    }
}

// endregion
```

Use `@PreviewParameter` with a provider class for parameterized previews covering all variants/states. Previews are always `private`.

---

## Code style & copyright

- **Every file** starts with the Apache 2.0 copyright header (year = current year, author = Gabriel Derrien):
    ```kotlin
    /*
     * Copyright 2026 Gabriel Derrien
     *
     * Licensed under the Apache License, Version 2.0 (the "License");
     * you may not use this file except in compliance with the License.
     * You may obtain a copy of the License at
     *
     *     http://www.apache.org/licenses/LICENSE-2.0
     *
     * Unless required by applicable law or agreed to in writing, software
     * distributed under the License is distributed on an "AS IS" BASIS,
     * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
     * See the License for the specific language governing permissions and
     * limitations under the License.
     */
    ```
- Static analysis via **Detekt** (`config/detekt/detekt.yml`), zero issues allowed
- Use `@Suppress("RuleName")` only when a bypass is genuinely necessary
- Format code with the IDEA code style formatter (⌥⌘L on macOS)
