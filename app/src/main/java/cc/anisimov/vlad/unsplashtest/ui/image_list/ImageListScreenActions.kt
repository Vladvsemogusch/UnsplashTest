package cc.anisimov.vlad.unsplashtest.ui.image_list

import cc.anisimov.vlad.unsplashtest.domain.model.Photo

interface ImageListScreenActions {

    fun onBookmarkClick(photo: Photo)
}