package cc.anisimov.vlad.unsplashtest.ui.feature.imagelist.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import cc.anisimov.vlad.unsplashtest.R
import cc.anisimov.vlad.unsplashtest.ui.theme.UnsplashTestTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageListScreenTopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        colors =
            TopAppBarDefaults
                .topAppBarColors()
                .copy(containerColor = MaterialTheme.colorScheme.primary),
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.displayLarge,
            )
        },
    )
}

@Preview
@Composable
private fun ImageListScreenTopAppBarPreview() {
    UnsplashTestTheme {
        ImageListScreenTopAppBar()
    }
}
