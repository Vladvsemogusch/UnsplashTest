package cc.anisimov.vlad.unsplashtest.ui.feature.imagelist

import cc.anisimov.vlad.unsplashtest.domain.model.User
import cc.anisimov.vlad.unsplashtest.ui.base.UIEvent

sealed class ImageListScreenEvent : UIEvent {

    data class GoToAuthorProfile(val author: User) : ImageListScreenEvent()
    data class ShowError(val message: String?) : ImageListScreenEvent()
}