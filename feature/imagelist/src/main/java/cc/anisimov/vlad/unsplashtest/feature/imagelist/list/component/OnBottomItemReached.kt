package cc.anisimov.vlad.unsplashtest.feature.imagelist.list.component

import android.annotation.SuppressLint
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember

@SuppressLint("ComposableNaming")
@Composable
fun LazyListState.onBottomItemReached(
    itemIndexFromBottom: Int = 0,
    doOnBottomItemReached: () -> Unit,
) {
    val isBottomItemReached by remember {
        derivedStateOf {
            val lastVisibleItem =
                layoutInfo.visibleItemsInfo.lastOrNull()
                    ?: return@derivedStateOf true

            lastVisibleItem.index == layoutInfo.totalItemsCount - 1 - itemIndexFromBottom
        }
    }

    LaunchedEffect(isBottomItemReached) {
        if (isBottomItemReached) doOnBottomItemReached()
    }
}
