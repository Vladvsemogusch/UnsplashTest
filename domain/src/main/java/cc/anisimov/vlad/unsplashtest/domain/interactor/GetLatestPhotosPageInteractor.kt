package cc.anisimov.vlad.unsplashtest.domain.interactor

import cc.anisimov.vlad.unsplashtest.domain.di.DispatcherDefault
import cc.anisimov.vlad.unsplashtest.domain.mapper.DtoPhotoMapper
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import cc.anisimov.vlad.unsplashtest.domain.repository.PhotoBookmarkRepository
import cc.anisimov.vlad.unsplashtest.domain.repository.PhotoRepository
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
    private val photoMapper: DtoPhotoMapper,
    @param:DispatcherDefault private val dispatcher: CoroutineDispatcher,
) {
    suspend operator fun invoke(page: Int): Flow<List<Photo>> =
        withContext(dispatcher) {
            val photoModelsDto = photoRepository.getLatestPhotos(page)
            val photoBookmarksDtoFlow = bookmarkRepository.getAllBookmarks()
            photoBookmarksDtoFlow.map { photoMapper.map(photoModelsDto, it) }
        }
}
