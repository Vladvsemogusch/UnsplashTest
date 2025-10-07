package cc.anisimov.vlad.unsplashtest.ui.feature.imagelist

import androidx.compose.runtime.Immutable
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import kotlinx.collections.immutable.ImmutableList


@Immutable
sealed interface ImageListScreenState {
    data object InitialLoading : ImageListScreenState

    @Immutable
    sealed class Content(
        open val photoList: ImmutableList<Photo>,
    ) : ImageListScreenState {
        data class Ready(
            override val photoList: ImmutableList<Photo>,
        ) : Content(photoList)
        data class LoadingMore(
            override val photoList: ImmutableList<Photo>,
        ) : Content(photoList)
    }
}
