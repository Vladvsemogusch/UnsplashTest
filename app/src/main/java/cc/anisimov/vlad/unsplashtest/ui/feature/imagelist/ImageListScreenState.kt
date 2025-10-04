package cc.anisimov.vlad.unsplashtest.ui.feature.imagelist

import cc.anisimov.vlad.unsplashtest.domain.model.Photo

sealed interface ImageListScreenState {
    data object InitialLoading : ImageListScreenState

    sealed class Content(open val photoList: List<Photo>) : ImageListScreenState {
        data class Ready(override val photoList: List<Photo>) : Content(photoList)
        data class LoadingMore(override val photoList: List<Photo>) : Content(photoList)
    }
}

