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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cc.anisimov.vlad.unsplashtest.R
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import cc.anisimov.vlad.unsplashtest.ui.base.UIEvent
import cc.anisimov.vlad.unsplashtest.ui.base.handleEvents
import cc.anisimov.vlad.unsplashtest.ui.base.navigation.AppGraph
import cc.anisimov.vlad.unsplashtest.ui.feature.imagelist.component.ImageItem
import cc.anisimov.vlad.unsplashtest.ui.feature.imagelist.component.ImageListScreenTopAppBar
import cc.anisimov.vlad.unsplashtest.ui.feature.imagelist.component.onBottomItemReached
import cc.anisimov.vlad.unsplashtest.ui.theme.UnsplashTestTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.generated.destinations.AuthorProfileRouteDestination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination<AppGraph>(start = true)
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
                direction = AuthorProfileRouteDestination(event.author)
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
        topBar = { ImageListScreenTopAppBar() }
    ) { paddingValues ->
        when (screenState) {
            is ImageListScreenState.InitialLoading -> {
                ImageListScreenLoading(paddingValues = paddingValues)
            }

            is ImageListScreenState.Content -> {
                ImageListScreenContent(
                    paddingValues = paddingValues,
                    contentState = screenState,
                    screenActions = screenActions
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

    // Start loading more on reaching third item from the bottom
    listState.onBottomItemReached(
        itemIndexFromBottom = 2,
        doOnBottomItemReached = screenActions::onListBottomItemReached,
    )

    LazyColumn(
        modifier = Modifier.padding(paddingValues),
        state = listState
    ) {
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
private fun ImageListScreenContent_Ready_Preview() {
    val screenState = ImageListScreenState.Content.Ready(
        photoList = listOf(Photo.stub)
    )

    UnsplashTestTheme {
        ImageListScreen(
            screenState = screenState,
            screenActions = ImageListScreenActions.Empty,
            snackbarHostState = remember { SnackbarHostState() },
        )
    }
}

@Preview
@Composable
private fun ImageListScreenContent_LoadingMore_Preview() {
    val screenState = ImageListScreenState.Content.LoadingMore(
        photoList = listOf(Photo.stub)
    )

    UnsplashTestTheme {
        ImageListScreen(
            screenState = screenState,
            screenActions = ImageListScreenActions.Empty,
            snackbarHostState = remember { SnackbarHostState() },
        )
    }
}

@Preview
@Composable
private fun ImageListScreen_InitialLoading_Preview() {
    val screenState = ImageListScreenState.InitialLoading

    UnsplashTestTheme {
        ImageListScreen(
            screenState = screenState,
            screenActions = ImageListScreenActions.Empty,
            snackbarHostState = remember { SnackbarHostState() },
        )
    }
}