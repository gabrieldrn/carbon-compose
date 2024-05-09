package carbon.compose.catalog.radiobutton

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import carbon.compose.loading.Loading

@Composable
fun LoadingDemoScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize()) {
        Loading(modifier.align(Alignment.Center))
    }
}
