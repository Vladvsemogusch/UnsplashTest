package cc.anisimov.vlad.unsplashtest.ui.feature.image_list

import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import cc.anisimov.vlad.unsplashtest.domain.model.User

interface ImageListScreenActions {

    fun onBookmarkClick(photo: Photo)

    fun onAuthorClick(author: User)
}