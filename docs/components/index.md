# Available components

!!! info
    Section under construction

!!! warning
    Only few components with limited implementations are available at the moment

## Foundation

- Themes
    - [White](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.foundation.color/-white-theme.html)
    - [Grey10](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.foundation.color/-gray10-theme.html)
    - [Grey90](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.foundation.color/-gray90-theme.html)
    - [Grey100](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.foundation.color/-gray100-theme.html)
- [Typography](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.foundation.text/-carbon-typography/index.html) 
    (IBM Plex)
- [Layering](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.foundation.color/-layer/index.html)

## Components

- [Accordion](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.accordion/-accordion.html)
- [Button](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.button/-button.html)
- [Breadcrumb](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.breadcrumb/-breadcrumb.html)
- [Icon button](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.button/-icon-button.html)
- [Checkbox](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.checkbox/-checkbox.html)
- ContentSwitcher
  - [Default content switcher](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.contentswitcher/-content-switcher.html)
  - [Icon content switcher](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.contentswitcher/-icon-content-switcher.html)
- Dropdown
    - [Default dropdown](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.dropdown/-dropdown.html)
    - [Multi-select dropdown](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.dropdown.multiselect/-multiselect-dropdown.html)
- Loading
    - [Default loading](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.loading/-loading.html)
    - [Small loading](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.loading/-small-loading.html)
- Notification
    - [Actionable inline notification](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.notification/-actionable-inline-notification.html)
    - [Actionable toast notification](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.notification/-actionable-toast-notification.html)
    - [Callout notification](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.notification/-callout-notification.html)
    - [Inline notification](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.notification/-inline-notification.html)
    - [Toast notification](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.notification/-toast-notification.html)
- Popover
    - (No tip) [Popover box](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.popover/-popover-box.html)
    - (Caret tip) [Caret tip popover](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.popover.carettip/-popover-caret-tip-box.html)
    - (Tab tip) [Icon button with popover](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.button/-icon-button-with-popover.html)
- Progress bar
    - [Default progress bar](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.progressbar/-progress-bar.html)
    - [Indeterminate progress bar](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.progressbar/-indeterminate-progress-bar.html)
- [Radio button](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.radiobutton/-radio-button.html)
- Slider
    - [Default slider](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.slider/-slider.html)
- Tag
    - [Read-only tag](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.tag/-read-only-tag.html.html)
- Text input
    - [Default text input](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.textinput/-text-input.html)
    - [Text area](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.textinput/-text-area.html)
    - [Password input](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.textinput/-password-input.html)
- Toggle
    - [Default toggle](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.toggle/-toggle.html)
    - [Small toggle](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.toggle/-small-toggle.html)
- [Tooltip](https://gabrieldrn.github.io/carbon-compose/api/carbon/com.gabrieldrn.carbon.tooltip/-tooltip-box.html)

### Missing components size options

**Some components miss size options depending on the platform.**

The main reason is due to accessibility. Some platforms recommend a minimum size for touch targets to be easily
clickable.

In Android and iOS, the recommended minimum touch target size is 48x48dp and 44x44pt respectively. As long as Carbon
doesn't have an official documentation over the mobile version, the library will follow each platform's guidelines.

[Android touch target size](https://support.google.com/accessibility/android/answer/7101858?hl=en)
[iOS UI design](https://developer.apple.com/design/tips/)

For instance, you will see that the Button component is missing the `Small` and `Medium` sizes. The dropdown is also
missing the `Small` and `Medium` sizes.

!!! warning
    The library still provide sizes for some components that does not comply with accessibility guidelines. They will be 
    removed until the library reaches the 1.0.0 version. 
