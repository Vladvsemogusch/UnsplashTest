package cc.anisimov.vlad.unsplashtest.ui.feature.authorprofile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import cc.anisimov.vlad.unsplashtest.R
import cc.anisimov.vlad.unsplashtest.ui.base.UIEvent
import cc.anisimov.vlad.unsplashtest.ui.base.component.UrlImage
import cc.anisimov.vlad.unsplashtest.ui.base.handleEvents
import cc.anisimov.vlad.unsplashtest.ui.theme.UnsplashTestTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(navArgsDelegate = AuthorProfileNavArgs::class)
@Composable
fun AuthorProfileRoute(
    navigator: DestinationsNavigator,
    viewModel: AuthorProfileViewModel = hiltViewModel()
) {
    handleEvents(eventsFlow = viewModel.eventsFlow) { event ->
        handleEvent(event = event, navigator = navigator)
    }

    AuthorProfileScreen(viewModel.screenState, viewModel)
}

private fun handleEvent(event: UIEvent, navigator: DestinationsNavigator) {
    when (event) {
        is AuthorProfileScreenEvent.GoBack -> {
            navigator.popBackStack()
        }

        else -> throw IllegalArgumentException("Unsupported event: $event")
    }
}

@Composable
fun AuthorProfileScreen(
    screenState: AuthorProfileScreenState,
    screenActions: AuthorProfileScreenActions,
) {
    Scaffold(topBar = { AuthorProfileScreenTopAppBar(screenActions::onBackPress) }) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UrlImage(
                modifier = Modifier
                    .padding(top = 48.dp)
                    .size(96.dp),
                url = screenState.profileImageUrl,
                shape = CircleShape
            )
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = screenState.name,
                style = MaterialTheme.typography.displayLarge
            )
            if (screenState.bio != null) {
                HorizontalDivider(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .width(32.dp),
                    color = MaterialTheme.colorScheme.primary,
                    thickness = 1.dp
                )
                Text(
                    modifier = Modifier.padding(top = 16.dp),
                    text = stringResource(id = R.string.bio),
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    modifier = Modifier
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .background(
                            color = MaterialTheme.colorScheme.onSurface.copy(
                                alpha = 0.05f
                            ),
                            shape = MaterialTheme.shapes.medium
                        )
                        .padding(8.dp),
                    text = screenState.bio,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorProfileScreenTopAppBar(onBackPress: () -> Unit) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors()
            .copy(containerColor = MaterialTheme.colorScheme.secondary),
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
                style = MaterialTheme.typography.displayLarge
            )
        }
    )
}

@Preview
@Composable
fun AuthorProfileScreenContentPreview() {
    val screenState = AuthorProfileScreenState(
        name = "Test Name", bio = "Test Bio", profileImageUrl = ""
    )
    val screenActions = object : AuthorProfileScreenActions {
        override fun onBackPress() {}
    }
    UnsplashTestTheme {
        AuthorProfileScreen(
            screenState = screenState,
            screenActions = screenActions,
        )
    }
}
