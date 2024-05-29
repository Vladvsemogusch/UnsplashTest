package cc.anisimov.vlad.unsplashtest.ui.feature.image_list.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cc.anisimov.vlad.unsplashtest.R
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import cc.anisimov.vlad.unsplashtest.ui.base.component.UrlImage
import cc.anisimov.vlad.unsplashtest.ui.feature.image_list.ImageListScreenActions

@Composable
fun ImageItem(photo: Photo, screenActions: ImageListScreenActions) {
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
                onClick = { screenActions.onBookmarkClick(photo) },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .background(
                        color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                        shape = MaterialTheme.shapes.small
                    )
            ) {
                Image(painter = painterResource(bookmarkIconResId), contentDescription = null)
            }
            Text(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(16.dp)
                    .clickable {
                        screenActions.onAuthorClick(photo.author)
                    }
                    .background(
                        color = MaterialTheme.colorScheme.background.copy(alpha = 0.5f),
                        shape = MaterialTheme.shapes.small
                    )
                    .padding(vertical = 4.dp, horizontal = 8.dp),
                text = photo.authorName,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}