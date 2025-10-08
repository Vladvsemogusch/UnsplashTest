package cc.anisimov.vlad.unsplashtest.feature.imagelist.authorprofile

import cc.anisimov.vlad.unsplashtest.core.ui.base.UIEvent

sealed class AuthorProfileScreenEvent : UIEvent {
    data object GoBack : AuthorProfileScreenEvent()
}
