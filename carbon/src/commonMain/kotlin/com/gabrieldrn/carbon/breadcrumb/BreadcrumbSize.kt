/*
 * Copyright 2025 Gabriel Derrien
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

package com.gabrieldrn.carbon.breadcrumb

/**
 * Breadcrumb size type.
 */
public enum class BreadcrumbSize {

    /**
     * Medium breadcrumb size.
     * Medium breadcrumbs are typically used when there is no page header and are placed at the top
     * of a page. The default size of breadcrumb is the medium size.
     */
    Medium,

    /**
     * Small breadcrumb size.
     * Small breadcrumbs are commonly used in page headers. They can also be used in condensed
     * spaces and for smaller breakpoints. You may also choose to use the small breadcrumb when
     * trying to achieve a balanced content hierarchy and need a smaller breadcrumb to pair with.
     */
    Small
}
