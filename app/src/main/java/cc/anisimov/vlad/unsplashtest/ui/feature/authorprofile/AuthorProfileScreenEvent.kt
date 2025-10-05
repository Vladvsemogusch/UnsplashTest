package cc.anisimov.vlad.unsplashtest.ui.feature.authorprofile

import cc.anisimov.vlad.unsplashtest.ui.base.UIEvent

sealed class AuthorProfileScreenEvent : UIEvent {
    data object GoBack : AuthorProfileScreenEvent()
}
