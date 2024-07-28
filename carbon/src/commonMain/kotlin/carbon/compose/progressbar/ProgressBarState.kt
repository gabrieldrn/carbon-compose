package carbon.compose.progressbar

import androidx.compose.runtime.Immutable

/**
 * There are three states for the progress bar: active, success, and error. These states also apply
 * on both indeterminate and determinate progress bars. After the process completes successfully or
 * unsuccesfully, a progress bar can either remain persistent as confirmation or validation, or it
 * can be automatically dismissed depending on what is most suitable for the use case.
 *
 * (From [Progress bar documentation](https://carbondesignsystem.com/components/progress-bar/usage))
 */
@Immutable
public enum class ProgressBarState {
    Active,
    Success,
    Error
}
