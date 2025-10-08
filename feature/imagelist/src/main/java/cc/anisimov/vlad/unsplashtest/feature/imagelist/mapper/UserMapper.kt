package cc.anisimov.vlad.unsplashtest.feature.imagelist.mapper

import cc.anisimov.vlad.unsplashtest.domain.model.User
import cc.anisimov.vlad.unsplashtest.feature.imagelist.model.UserUi
import javax.inject.Inject

class UserMapper
@Inject
constructor() {
    fun map(user: User): UserUi =
        UserUi(
            id = user.id,
            name = user.name,
            bio = user.bio,
            profileImageUrl = user.profileImageUrl,
        )
}
