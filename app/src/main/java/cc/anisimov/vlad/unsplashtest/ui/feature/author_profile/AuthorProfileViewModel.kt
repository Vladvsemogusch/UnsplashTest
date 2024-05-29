package cc.anisimov.vlad.unsplashtest.ui.feature.author_profile

import androidx.lifecycle.SavedStateHandle
import cc.anisimov.vlad.unsplashtest.ui.base.BaseViewModel
import cc.anisimov.vlad.unsplashtest.ui.feature.destinations.AuthorProfileRouteDestination
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthorProfileViewModel @Inject constructor(
    handle: SavedStateHandle,
) : BaseViewModel(), AuthorProfileScreenActions {

    private val author = AuthorProfileRouteDestination.argsFrom(handle).author

    val screenState = AuthorProfileScreenState(
        name = author.name,
        bio = author.bio,
        profileImageUrl = author.profileImageUrl
    )

    override fun onBackPress() {
        sendEvent(AuthorProfileScreenEvent.GoBack)
    }

}