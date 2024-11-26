# `:code-gen`
This module aims to generate Kotlin implementations of tokens from Carbon.

# Colors
Currently, this module only generates implementations of Carbon themes.

Execution of `main.kt` deserializes the JSON files located in the resources folder, then generates 
Kotlin files into the `:carbon` module at `com.gabrieldrn.carbon.foundation.color`. Each file 
corresponds to a theme, and contains the colors for the theme.

The resulting structure is as follows:
```
com.gabrieldrn.carbon.foundation.color
├── Gray100Theme.kt
├── Gray10Theme.kt
├── Gray90Theme.kt
├── Layer.kt
├── Theme.kt
├── ThemeStaticComposition.kt
├── WhiteTheme.kt
├── ai
│   ├── AiColors.kt
│   ├── Gray100AiColors.kt
│   ├── Gray10AiColors.kt
│   ├── Gray90AiColors.kt
│   └── WhiteAiColors.kt
├── button
│   ├── ButtonColors.kt
│   ├── Gray100ButtonColors.kt
│   ├── Gray10ButtonColors.kt
│   ├── Gray90ButtonColors.kt
│   └── WhiteButtonColors.kt
├── chat
│   ├── ChatColors.kt
│   ├── Gray100ChatColors.kt
│   ├── Gray10ChatColors.kt
│   ├── Gray90ChatColors.kt
│   └── WhiteChatColors.kt
├── notification
│   ├── Gray100NotificationColors.kt
│   ├── Gray10NotificationColors.kt
│   ├── Gray90NotificationColors.kt
│   ├── NotificationColors.kt
│   └── WhiteNotificationColors.kt
└── tag
    ├── Gray100TagColors.kt
    ├── Gray10TagColors.kt
    ├── Gray90TagColors.kt
    ├── TagColors.kt
    └── WhiteTagColors.kt
```
