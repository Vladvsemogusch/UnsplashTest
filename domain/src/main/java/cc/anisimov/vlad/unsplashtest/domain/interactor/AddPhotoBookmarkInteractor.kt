package cc.anisimov.vlad.unsplashtest.domain.interactor

import cc.anisimov.vlad.unsplashtest.domain.repository.PhotoBookmarkRepository
import javax.inject.Inject

class AddPhotoBookmarkInteractor
@Inject
constructor(
    private val bookmarkRepository: PhotoBookmarkRepository,
) {
    suspend operator fun invoke(photoId: String) = bookmarkRepository.addPhotoBookmark(photoId)
}
