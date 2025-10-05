package cc.anisimov.vlad.unsplashtest.ui.feature.authorprofile.component

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import cc.anisimov.vlad.unsplashtest.R
import cc.anisimov.vlad.unsplashtest.ui.theme.UnsplashTestTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AuthorProfileScreenTopAppBar(onBackPress: () -> Unit) {
    TopAppBar(
        colors =
            TopAppBarDefaults
                .topAppBarColors()
                .copy(containerColor = MaterialTheme.colorScheme.primary),
        navigationIcon = {
            IconButton(onClick = onBackPress) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_left),
                    contentDescription = null,
                )
            }
        },
        title = {
            Text(
                text = stringResource(id = R.string.author_profile),
                style = MaterialTheme.typography.displayLarge,
            )
        },
    )
}

@Preview
@Composable
private fun AuthorProfileScreenTopAppBarPreview() {
    UnsplashTestTheme {
        AuthorProfileScreenTopAppBar(onBackPress = {})
    }
}
