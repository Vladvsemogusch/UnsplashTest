package cc.anisimov.vlad.unsplashtest.ui.feature.imagelist

import androidx.compose.runtime.Stable
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import cc.anisimov.vlad.unsplashtest.domain.model.User

@Stable
interface ImageListScreenActions {

    fun onBookmarkClick(photo: Photo)

    fun onAuthorClick(author: User)

    fun onListBottomItemReached()
}