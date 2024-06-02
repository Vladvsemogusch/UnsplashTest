package cc.anisimov.vlad.unsplashtest.ui.feature.imagelist.component

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember


@Composable
fun LazyListState.OnBottomItemReached(
    bottomItemIndexFromEnd: Int = 0,
    executable: () -> Unit,
) {
    val shouldLoadMore by remember {
        derivedStateOf {
            val lastVisibleItem = layoutInfo.visibleItemsInfo.lastOrNull()
                ?: return@derivedStateOf true

            lastVisibleItem.index == layoutInfo.totalItemsCount - 1 - bottomItemIndexFromEnd
        }
    }

    LaunchedEffect(shouldLoadMore) {
        if (shouldLoadMore) executable()
    }
}