package cc.anisimov.vlad.unsplashtest.domain.interactor

import cc.anisimov.vlad.unsplashtest.data.repository.PhotoBookmarkRepository
import cc.anisimov.vlad.unsplashtest.data.repository.PhotoRepository
import cc.anisimov.vlad.unsplashtest.di.DispatcherDefault
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import cc.anisimov.vlad.unsplashtest.domain.model.dto.PhotoBookmarkDto
import cc.anisimov.vlad.unsplashtest.domain.model.dto.PhotoDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetLatestPhotosPageInteractor @Inject constructor(
    private val photoRepository: PhotoRepository,
    private val bookmarkRepository: PhotoBookmarkRepository,
    @param:DispatcherDefault private val dispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(page: Int): List<Photo> = withContext(dispatcher) {
        val dtoPhotoModels = photoRepository.getLatestPhotos(page)
        val dtoPhotoBookmarks = bookmarkRepository.getAllBookmarks()
        assemblePhotos(dtoPhotoModels, dtoPhotoBookmarks)
    }

    private fun assemblePhotos(
        apiPhotoModels: List<PhotoDto>,
        photoBookmarkEntities: List<PhotoBookmarkDto>
    ): List<Photo> {
        return apiPhotoModels.map { photo ->
            with(photo) {
                Photo(
                    id = id,
                    description = description,
                    url = urls.small,
                    authorName = authorName,
                    author = author,
                    isBookmarked = photoBookmarkEntities.any { it.photoId == id }
                )
            }
        }
    }

}