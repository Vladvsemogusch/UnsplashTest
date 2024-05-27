package cc.anisimov.vlad.unsplashtest.ui.image_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import cc.anisimov.vlad.unsplashtest.R
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import cc.anisimov.vlad.unsplashtest.ui.theme.UnsplashTestTheme
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun ImageListRoute(
    navigator: DestinationsNavigator,
    viewModel: ImageListViewModel = hiltViewModel()
) {
    val screenState by viewModel.screenState.collectAsStateWithLifecycle()

    ImageListScreen(screenState, viewModel)
}

@Composable
fun ImageListScreen(screenState: ImageListScreenState, screenActions: ImageListScreenActions) {
    Scaffold(topBar = { ImageListScreenTopAppBar() }) { paddingValues ->
        when (screenState) {
            is ImageListScreenState.Loading -> {
                ImageListScreenLoading(paddingValues)
            }

            is ImageListScreenState.Content -> {
                ImageListScreenContent(
                    paddingValues,
                    screenState.photoList,
                    screenActions::onBookmarkClick
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageListScreenTopAppBar(modifier: Modifier = Modifier) {
    TopAppBar(
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors()
            .copy(containerColor = MaterialTheme.colorScheme.secondary),
        title = {
            Text(
                text = stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.displayLarge
            )
        }
    )
}

@Composable
fun ImageListScreenLoading(paddingValues: PaddingValues) {
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
    onBookmarkClick: (Photo) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(it)) {
        items(items = photoList, key = { photo -> photo.id }) { photo ->
            ImageItem(photo, onBookmarkClick)
        }
    }
}

@Composable
private fun ImageItem(photo: Photo, onBookmarkClick: (Photo) -> Unit) {
    val bookmarkIconResId = if (photo.isBookmarked) {
        R.drawable.ic_bookmark_filled
    } else {
        R.drawable.ic_bookmark
    }
    Card(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
        Box {
            UrlImage(
                url = photo.url,
                modifier = Modifier
                    .fillMaxWidth()
            )
            IconButton(
                onClick = { onBookmarkClick(photo) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                        shape = MaterialTheme.shapes.small
                    ),

                ) {
                Image(painter = painterResource(bookmarkIconResId), contentDescription = null)
            }
            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                text = photo.authorName,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun UrlImage(
    url: String?,
    modifier: Modifier = Modifier,
    scale: ContentScale = ContentScale.Crop,
    shape: Shape = RoundedCornerShape(0.dp),
    colorFilter: ColorFilter? = null
) {
    SubcomposeAsyncImage(
        contentScale = scale,
        contentDescription = null,
        modifier = modifier.clip(shape),
        colorFilter = colorFilter,
        model = url,
    ) {
        val state = painter.state
        if (state is AsyncImagePainter.State.Loading || state is AsyncImagePainter.State.Error) {
            Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            SubcomposeAsyncImageContent()
        }
    }
}

@Preview
@Composable
fun ImageListScreenContentPreview() {
    val screenState = ImageListScreenState.Content(
        photoList = listOf()
    )
    val screenActions = object : ImageListScreenActions {
        override fun onBookmarkClick(photo: Photo) {}
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
fun ImageListScreenLoadingPreview() {
    val screenState = ImageListScreenState.Loading
    val screenActions = object : ImageListScreenActions {
        override fun onBookmarkClick(photo: Photo) {}
    }
    UnsplashTestTheme {
        ImageListScreen(
            screenState = screenState,
            screenActions = screenActions,
        )
    }
}

