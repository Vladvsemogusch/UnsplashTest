package cc.anisimov.vlad.unsplashtest.feature.imagelist.screen.authorprofile

import cc.anisimov.vlad.unsplashtest.core.ui.base.UIEvent

sealed class AuthorProfileScreenEvent : UIEvent {
    data object GoBack : AuthorProfileScreenEvent()
}
