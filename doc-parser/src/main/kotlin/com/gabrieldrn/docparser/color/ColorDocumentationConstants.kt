/*
 * Copyright 2024 Gabriel Derrien
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

package com.gabrieldrn.docparser.color

// From https://carbondesignsystem.com/elements/color/overview/#light-themes
val whiteThemeDoc =
    """
        One of the default _light_ themes available in Carbon.
        
        This theme uses White as the global background color and is layered first with components using Gray 10 backgrounds.
        
        The second layer uses White and the third layer used Gray 10.
    """.trimIndent()

// From https://carbondesignsystem.com/elements/color/overview/#light-themes
val g10ThemeDoc =
    """
        One of the default _light_ themes available in Carbon.
        
        This theme uses Gray 10 as the global background color and is layered first with components using White backgrounds.
        
        The second layer uses Gray 10 and the third layer used White.
    """.trimIndent()

// From https://carbondesignsystem.com/elements/color/overview/#dark-themes
val g90ThemeDoc =
    """
        One of the default _dark_ themes available in Carbon.
        
        This theme uses Gray 90 as the global background color and is layered first with components using Gray 80 backgrounds.
        
        The second layer uses Gray 70 and the third layer used Gray 60.
    """.trimIndent()

// From https://carbondesignsystem.com/elements/color/overview/#dark-themes
val g100ThemeDoc =
    """
        One of the default _dark_ themes available in Carbon.
        
        This theme uses Gray 100 as the global background color and is layered first with components using Gray 90 backgrounds.
        
        The second layer uses Gray 80 and the third layer used Gray 70.
    """.trimIndent()

val abstractThemeDoc =
    """
        Themes are used to modify existing components to fit a specific visual style. By using Carbon’s tokens, developers can easily customize all of their components by changing a set of universal variables, eliminating the need to modify individual components.

        Themes serve as an organizational framework for color in Carbon, with each theme based on a specific primary background color. And they actually get their names from their background color.

        See [Themes guidelines](https://carbondesignsystem.com/guidelines/themes/overview/) for more information.
    """.trimIndent()

val containerColorMemberDoc =
    """
        Returns the container color based on a provided [layer].
        
        @param layer Associated layer. Defaults to layer 00.
    """.trimIndent()
