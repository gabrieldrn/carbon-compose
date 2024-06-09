package carbon.compose.progressbar

import androidx.compose.runtime.Immutable

/**
 * The [ProgressBar] is offered in two different sizes—big (8px) and small (4px). The big progress
 * bar height is typically used when there is large amounts of space on a page. The small progress
 * bar height is commonly used when space is restricted and can be placed within cards, data tables,
 * or side panels.
 *
 * (From [Progress bar documentation](https://carbondesignsystem.com/components/progress-bar/usage))
 */
@Immutable
public enum class ProgressBarSize {
    Big, Small
}
