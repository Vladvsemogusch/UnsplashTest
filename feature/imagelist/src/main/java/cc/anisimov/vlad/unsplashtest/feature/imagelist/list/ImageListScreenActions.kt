package cc.anisimov.vlad.unsplashtest.feature.imagelist.list

import androidx.compose.runtime.Stable
import cc.anisimov.vlad.unsplashtest.feature.imagelist.model.PhotoUi
import cc.anisimov.vlad.unsplashtest.feature.imagelist.model.UserUi

@Stable
interface ImageListScreenActions {
    fun onBookmarkClick(photo: PhotoUi)

    fun onAuthorClick(author: UserUi)

    fun onListBottomItemReached()

    companion object {
        val Empty by lazy {
            object : ImageListScreenActions {
                override fun onBookmarkClick(photo: PhotoUi) {}

                override fun onAuthorClick(author: UserUi) {}

                override fun onListBottomItemReached() {}
            }
        }
    }
}
