package cc.anisimov.vlad.unsplashtest.domain.interactor

import cc.anisimov.vlad.unsplashtest.data.repository.PhotoBookmarkRepository
import cc.anisimov.vlad.unsplashtest.data.repository.PhotoRepository
import cc.anisimov.vlad.unsplashtest.di.DispatcherDefault
import cc.anisimov.vlad.unsplashtest.domain.mapper.PhotoMapper
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetLatestPhotosPageInteractor
@Inject
constructor(
    private val photoRepository: PhotoRepository,
    private val bookmarkRepository: PhotoBookmarkRepository,
    private val photoMapper: PhotoMapper,
    @param:DispatcherDefault private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(page: Int): Flow<List<Photo>> =
        withContext(dispatcher) {
            val dtoPhotoModels = photoRepository.getLatestPhotos(page)
            val dtoPhotoBookmarksFlow = bookmarkRepository.getAllBookmarks()
            dtoPhotoBookmarksFlow.map { photoMapper.map(dtoPhotoModels, it) }
        }
    }
