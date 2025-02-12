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

package com.gabrieldrn.carbon.notification

import androidx.compose.runtime.Stable

/**
 * Notification statuses are designed to convey the emotional tone of the information being
 * communicated. Each status is associated with a specific color and icon, ensuring a consistent and
 * universal user experience.
 *
 * (From [Notification documentation](https://carbondesignsystem.com/components/notification/usage/#notification-status))
 */
@Stable
public enum class NotificationStatus {

    /**
     * Usage: Provide additional information to users that may not be tied to their current action
     * or task.
     */
    Informational,

    /**
     * Usage: Confirm a task was completed as expected.
     */
    Success,

    /**
     * Usage: Inform users that they are taking actions that are not desirable or might have
     * unexpected results.
     */
    Warning,

    /**
     * Inform users of an error or critical failure and optionally block the user from proceeding
     * until the issue has been resolved.
     */
    Error
}
