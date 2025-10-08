package cc.anisimov.vlad.unsplashtest.feature.imagelist.list

import androidx.compose.runtime.Immutable
import cc.anisimov.vlad.unsplashtest.feature.imagelist.model.PhotoUi
import kotlinx.collections.immutable.ImmutableList

@Immutable
sealed interface ImageListScreenState {
    data object InitialLoading : ImageListScreenState

    @Immutable
    sealed class Content(
        open val photoList: ImmutableList<PhotoUi>,
    ) : ImageListScreenState {
        data class Ready(
            override val photoList: ImmutableList<PhotoUi>,
        ) : Content(photoList)

        data class LoadingMore(
            override val photoList: ImmutableList<PhotoUi>,
        ) : Content(photoList)
    }
}
