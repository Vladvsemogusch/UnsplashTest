package cc.anisimov.vlad.unsplashtest.data.mapper

import cc.anisimov.vlad.unsplashtest.data.network.model.UserApiModel
import cc.anisimov.vlad.unsplashtest.domain.model.User
import javax.inject.Inject

class UserMapper @Inject constructor() {

    fun map(userApiModel: UserApiModel): User {
        return with(userApiModel) {
            User(
                id = id,
                bio = bio,
                name = name,
                profileImageUrl = profileImage.large,
            )
        }
    }

}