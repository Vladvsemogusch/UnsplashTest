package cc.anisimov.vlad.unsplashtest.ui.feature.imagelist

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cc.anisimov.vlad.unsplashtest.R
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import cc.anisimov.vlad.unsplashtest.domain.model.User
import cc.anisimov.vlad.unsplashtest.ui.base.UIEvent
import cc.anisimov.vlad.unsplashtest.ui.base.handleEvents
import cc.anisimov.vlad.unsplashtest.ui.feature.destinations.AuthorProfileRouteDestination
import cc.anisimov.vlad.unsplashtest.ui.feature.imagelist.component.ImageItem
import cc.anisimov.vlad.unsplashtest.ui.feature.imagelist.component.ImageListScreenTopAppBar
import cc.anisimov.vlad.unsplashtest.ui.feature.imagelist.component.OnBottomItemReached
import cc.anisimov.vlad.unsplashtest.ui.theme.UnsplashTestTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@RootNavGraph(start = true)
@Destination
@Composable
fun ImageListRoute(
    navigator: DestinationsNavigator,
    viewModel: ImageListViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = LocalContext.current
    handleEvents(eventsFlow = viewModel.eventsFlow) {
        handleEvent(it, navigator, snackbarHostState, context)
    }

    ImageListScreen(
        screenState = screenState,
        screenActions = viewModel,
        snackbarHostState = snackbarHostState
    )
}

private suspend fun handleEvent(
    event: UIEvent,
    navigator: DestinationsNavigator,
    snackbarHostState: SnackbarHostState,
    context: Context
) {
    when (event) {
        is ImageListScreenEvent.GoToAuthorProfile -> {
            navigator.navigate(
                direction = AuthorProfileRouteDestination(event.author),
                onlyIfResumed = true
            )
        }

        is ImageListScreenEvent.ShowError -> {
            val text = if (event.message != null) {
                context.getString(R.string.error_message, event.message)
            } else {
                context.getString(R.string.error_no_message)
            }
            snackbarHostState.showSnackbar(text)
        }

        else -> throw IllegalArgumentException("Unsupported event: $event")
    }
}

@Composable
private fun ImageListScreen(
    screenState: ImageListScreenState,
    screenActions: ImageListScreenActions,
    snackbarHostState: SnackbarHostState
) {
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = { ImageListScreenTopAppBar() }) { paddingValues ->
        when (screenState) {
            is ImageListScreenState.InitialLoading -> {
                ImageListScreenLoading(paddingValues)
            }

            is ImageListScreenState.Content -> {
                ImageListScreenContent(
                    paddingValues,
                    screenState,
                    screenActions
                )
            }
        }
    }
}

@Composable
private fun ImageListScreenLoading(paddingValues: PaddingValues) {
    Box(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
    ) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}

@Composable
private fun ImageListScreenContent(
    paddingValues: PaddingValues,
    contentState: ImageListScreenState.Content,
    screenActions: ImageListScreenActions
) {
    val listState = rememberLazyListState()
    listState.OnBottomItemReached(
        bottomItemIndexFromEnd = 2,
        executable = screenActions::onListBottomItemReached,
    )
    LazyColumn(modifier = Modifier.padding(paddingValues), state = listState) {
        items(items = contentState.photoList, key = { photo -> photo.id }) { photo ->
            ImageItem(photo, screenActions)
        }
        if (contentState is ImageListScreenState.Content.LoadingMore) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    }
}

@Preview
@Composable
private fun ImageListScreenContentLoadedPreview() {
    val screenState = ImageListScreenState.Content.Loaded(
        photoList = listOf()
    )
    val screenActions = object : ImageListScreenActions {
        override fun onBookmarkClick(photo: Photo) {}
        override fun onAuthorClick(author: User) {}
        override fun onListBottomItemReached() {}
    }
    UnsplashTestTheme {
        ImageListScreen(
            screenState = screenState,
            screenActions = screenActions,
            snackbarHostState = remember {
                SnackbarHostState()
            },
        )
    }
}

@Preview
@Composable
private fun ImageListScreenContentLoadingMorePreview() {
    val screenState = ImageListScreenState.Content.LoadingMore(
        photoList = listOf()
    )
    val screenActions = object : ImageListScreenActions {
        override fun onBookmarkClick(photo: Photo) {}
        override fun onAuthorClick(author: User) {}
        override fun onListBottomItemReached() {}
    }
    UnsplashTestTheme {
        ImageListScreen(
            screenState = screenState,
            screenActions = screenActions,
            snackbarHostState = remember {
                SnackbarHostState()
            },
        )
    }
}

@Preview
@Composable
private fun ImageListScreenLoadingPreview() {
    val screenState = ImageListScreenState.InitialLoading
    val screenActions = object : ImageListScreenActions {
        override fun onBookmarkClick(photo: Photo) {}
        override fun onAuthorClick(author: User) {}
        override fun onListBottomItemReached() {}

    }
    UnsplashTestTheme {
        ImageListScreen(
            screenState = screenState,
            screenActions = screenActions,
            snackbarHostState = remember {
                SnackbarHostState()
            },
        )
    }
}