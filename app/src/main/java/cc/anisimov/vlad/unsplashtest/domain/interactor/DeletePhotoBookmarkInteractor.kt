package cc.anisimov.vlad.unsplashtest.domain.interactor

import cc.anisimov.vlad.unsplashtest.data.repository.PhotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeletePhotoBookmarkInteractor @Inject constructor(private val repository: PhotoRepository) {

    suspend operator fun invoke(photoId: String) = withContext(Dispatchers.IO){
        repository.deletePhotoBookmark(photoId)
    }
}