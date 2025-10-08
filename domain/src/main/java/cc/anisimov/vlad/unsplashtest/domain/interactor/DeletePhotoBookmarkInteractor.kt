package cc.anisimov.vlad.unsplashtest.domain.interactor

import cc.anisimov.vlad.unsplashtest.domain.repository.PhotoBookmarkRepository
import javax.inject.Inject

class DeletePhotoBookmarkInteractor
@Inject
constructor(
    private val bookmarkRepository: PhotoBookmarkRepository,
) {
    suspend operator fun invoke(photoId: String) = bookmarkRepository.deletePhotoBookmark(photoId)
}
