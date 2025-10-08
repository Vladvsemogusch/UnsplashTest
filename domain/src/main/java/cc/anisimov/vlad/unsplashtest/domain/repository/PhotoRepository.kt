package cc.anisimov.vlad.unsplashtest.domain.repository

import cc.anisimov.vlad.unsplashtest.domain.model.dto.PhotoDto

interface PhotoRepository {
    suspend fun getLatestPhotos(page: Int): List<PhotoDto>
}
