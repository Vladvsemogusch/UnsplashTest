package cc.anisimov.vlad.unsplashtest.ui.feature.author_profile

import cc.anisimov.vlad.unsplashtest.ui.base.UIEvent

sealed class AuthorProfileScreenEvent : UIEvent {

    data object GoBack : AuthorProfileScreenEvent()
}