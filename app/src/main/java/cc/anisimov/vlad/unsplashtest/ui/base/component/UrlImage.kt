package cc.anisimov.vlad.unsplashtest.ui.base.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.SubcomposeAsyncImage
import coil.compose.SubcomposeAsyncImageContent

@Composable
fun UrlImage(
    modifier: Modifier = Modifier,
    url: String?,
    scale: ContentScale = ContentScale.Crop,
    shape: Shape = RoundedCornerShape(0.dp),
    colorFilter: ColorFilter? = null,
) {
    SubcomposeAsyncImage(
        contentScale = scale,
        contentDescription = null,
        modifier = modifier.clip(shape),
        colorFilter = colorFilter,
        model = url,
    ) {
        if (painter.state is AsyncImagePainter.State.Loading || painter.state is AsyncImagePainter.State.Error) {
            Box(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f),
            ) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        } else {
            SubcomposeAsyncImageContent()
        }
    }
}
