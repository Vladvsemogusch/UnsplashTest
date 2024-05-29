package cc.anisimov.vlad.unsplashtest.ui.feature.image_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import cc.anisimov.vlad.unsplashtest.domain.model.User
import cc.anisimov.vlad.unsplashtest.ui.base.UIEvent
import cc.anisimov.vlad.unsplashtest.ui.base.handleEvents
import cc.anisimov.vlad.unsplashtest.ui.feature.destinations.AuthorProfileRouteDestination
import cc.anisimov.vlad.unsplashtest.ui.feature.image_list.component.ImageItem
import cc.anisimov.vlad.unsplashtest.ui.feature.image_list.component.ImageListScreenTopAppBar
import cc.anisimov.vlad.unsplashtest.ui.theme.UnsplashTestTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun ImageListRoute(
    navigator: DestinationsNavigator,
    viewModel: ImageListViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()
    handleEvents(eventsFlow = viewModel.eventsFlow) {
        handleEvent(it, navigator)
    }

    ImageListScreen(screenState, viewModel)
}

fun handleEvent(event: UIEvent, navigator: DestinationsNavigator) {
    when (event) {
        is ImageListScreenEvent.GoToAuthorProfile -> {
            navigator.navigate(
                direction = AuthorProfileRouteDestination(event.author),
                onlyIfResumed = true
            )
        }

        else -> throw IllegalArgumentException("Unsupported event: $event")
    }
}


@Composable
private fun ImageListScreen(
    screenState: ImageListScreenState,
    screenActions: ImageListScreenActions
) {
    Scaffold(topBar = { ImageListScreenTopAppBar() }) { paddingValues ->
        when (screenState) {
            is ImageListScreenState.Loading -> {
                ImageListScreenLoading(paddingValues)
            }

            is ImageListScreenState.Content -> {
                ImageListScreenContent(
                    paddingValues,
                    screenState.photoList,
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
    it: PaddingValues,
    photoList: List<Photo>,
    screenActions: ImageListScreenActions
) {
    LazyColumn(modifier = Modifier.padding(it)) {
        items(items = photoList, key = { photo -> photo.id }) { photo ->
            ImageItem(photo, screenActions)
        }
    }
}

@Preview
@Composable
private fun ImageListScreenContentPreview() {
    val screenState = ImageListScreenState.Content(
        photoList = listOf()
    )
    val screenActions = object : ImageListScreenActions {
        override fun onBookmarkClick(photo: Photo) {}
        override fun onAuthorClick(author: User) {}
    }
    UnsplashTestTheme {
        ImageListScreen(
            screenState = screenState,
            screenActions = screenActions,
        )
    }
}

@Preview
@Composable
private fun ImageListScreenLoadingPreview() {
    val screenState = ImageListScreenState.Loading
    val screenActions = object : ImageListScreenActions {
        override fun onBookmarkClick(photo: Photo) {}
        override fun onAuthorClick(author: User) {}
    }
    UnsplashTestTheme {
        ImageListScreen(
            screenState = screenState,
            screenActions = screenActions,
        )
    }
}