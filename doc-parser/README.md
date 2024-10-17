# Doc-parser
This module aims to generate Kotlin implementations of tokens from Carbon.

# Colors
Currently, this module only generates implementations of Carbon themes.

`extract_colors_tokens_to_json.py` extracts the colors tokens from the Carbon website at a very specific URL, cleans
them, and saves them in a JSON file.

Execution of `main.kt` serializes the JSON file then generates Kotlin files into the `:carbon` module at 
`com.gabrieldrn.carbon.foundation.color`.

The resulting structure is as follows:
```
com.gabrieldrn.carbon.foundation.color
├── Gray100Theme.kt
├── Gray10Theme.kt
├── Gray90Theme.kt
├── Theme.kt <-- Themes interface with Core tokens
├── WhiteTheme.kt
├── ai <-- Components and some elements are placed in their respective packages
|   ├── AiColors.kt
|   ├── Gray100AiColors.kt
|   ├── Gray10AiColors.kt
|   ├── Gray90AiColors.kt
|   └── WhiteAiColors.kt
├── chat
|   ├── ChatColors.kt
|   ├── Gray100ChatColors.kt
|   ├── Gray10ChatColors.kt
|   ├── Gray90ChatColors.kt
|   └── WhiteChatColors.kt
├── notification
|   ├── Gray100NotificationColors.kt
|   ├── Gray10NotificationColors.kt
|   ├── Gray90NotificationColors.kt
|   ├── NotificationColors.kt
|   └── WhiteNotificationColors.kt
└── tag
    ├── Gray100TagColors.kt
    ├── Gray10TagColors.kt
    ├── Gray90TagColors.kt
    ├── TagColors.kt
    └── WhiteTagColors.kt
```
