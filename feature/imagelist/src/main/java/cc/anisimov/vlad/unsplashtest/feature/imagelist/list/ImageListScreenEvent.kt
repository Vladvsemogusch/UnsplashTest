package cc.anisimov.vlad.unsplashtest.feature.imagelist.list

import cc.anisimov.vlad.unsplashtest.core.ui.base.UIEvent
import cc.anisimov.vlad.unsplashtest.feature.imagelist.model.UserUi

sealed class ImageListScreenEvent : UIEvent {
    data class GoToAuthorProfile(
        val author: UserUi,
    ) : ImageListScreenEvent()

    data class ShowError(
        val message: String?,
    ) : ImageListScreenEvent()
}
