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

val abstractThemeDoc =
    """
        Themes are used to modify existing components to fit a specific visual style. By using Carbon’s tokens, developers can easily customize all of their components by changing a set of universal variables, eliminating the need to modify individual components.

        Themes serve as an organizational framework for color in Carbon, with each theme based on a specific primary background color. And they actually get their names from their background color.

        See [Themes guidelines](https://carbondesignsystem.com/guidelines/themes/overview/) for more information.
    """
        .trimIndent()

val containerColorMemberDoc =
    """
        Returns the container color based on a provided [layer].
        
        @param layer Associated layer. Defaults to layer 00.
    """
        .trimIndent()
