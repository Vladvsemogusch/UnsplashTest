package cc.anisimov.vlad.unsplashtest.data.repository

import cc.anisimov.vlad.unsplashtest.data.network.UnsplashService
import javax.inject.Inject

class UnsplashRepository @Inject constructor(private val unsplashService: UnsplashService){

    fun getLatestPhotos(){
        val latestPhotosApiModel = unsplashService.getLatestPhotos()
    }
}