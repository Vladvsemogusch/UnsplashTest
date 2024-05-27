package cc.anisimov.vlad.unsplashtest.ui.image_list

import cc.anisimov.vlad.unsplashtest.domain.model.Photo

sealed interface ImageListScreenState{

    data object Loading : ImageListScreenState

    data class Content(val photoList: List<Photo>) : ImageListScreenState

}