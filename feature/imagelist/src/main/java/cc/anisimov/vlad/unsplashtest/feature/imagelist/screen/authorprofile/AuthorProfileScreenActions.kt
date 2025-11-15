package cc.anisimov.vlad.unsplashtest.feature.imagelist.screen.authorprofile

import androidx.compose.runtime.Stable

@Stable
interface AuthorProfileScreenActions {
    fun onBackPress()

    companion object {
        val Empty by lazy {
            object : AuthorProfileScreenActions {
                override fun onBackPress() {}
            }
        }
    }
}
