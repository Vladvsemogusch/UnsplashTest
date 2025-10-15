package cc.anisimov.vlad.unsplashtest.domain.interactor

import app.cash.turbine.test
import cc.anisimov.vlad.core.common.rule.MainDispatcherRule
import cc.anisimov.vlad.unsplashtest.domain.mapper.DtoPhotoMapper
import cc.anisimov.vlad.unsplashtest.domain.model.dto.PhotoBookmarkDto
import cc.anisimov.vlad.unsplashtest.domain.model.dto.PhotoDto
import cc.anisimov.vlad.unsplashtest.domain.repository.PhotoBookmarkRepository
import cc.anisimov.vlad.unsplashtest.domain.repository.PhotoRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GetLatestPhotosPageInteractorTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private lateinit var photoRepository: PhotoRepository
    private lateinit var bookmarkRepository: PhotoBookmarkRepository
    private val photoMapper: DtoPhotoMapper = DtoPhotoMapper()

    @Before
    fun setUp() {
        photoRepository = mockk()
        bookmarkRepository = mockk()
    }

    @Test
    fun `updating bookmarks updates interactor emits`() =
        runTest {
            // Given
            val page = 1
            coEvery { photoRepository.getLatestPhotos(page) } returns
                    listOf(
                        PhotoDto.stub.copy(id = "1"),
                        PhotoDto.stub.copy(id = "2"),
                    )
            coEvery {
                bookmarkRepository.getBookmarksByPhotoIdsFlow(
                    listOf(
                        "1",
                        "2",
                    ),
                )
            } returns
                    flowOf(
                        listOf(
                            PhotoBookmarkDto("1"),
                            PhotoBookmarkDto("2"),
                        ),
                        listOf(
                            PhotoBookmarkDto("2"),
                        ),
                    )

            val getLatestPhotosPageInteractor =
                GetLatestPhotosPageInteractor(
                    photoRepository,
                    bookmarkRepository,
                    photoMapper,
                    Dispatchers.Main,
                )

            // When
            getLatestPhotosPageInteractor(page).test {
                // Then
                //  listOf(PhotoBookmarkDto("1"), PhotoBookmarkDto("2"))
                val emittedPhotos = awaitItem()
                assertTrue(emittedPhotos.size == 2)
                assertTrue(emittedPhotos[0].isBookmarked && emittedPhotos[1].isBookmarked)

                //  listOf(PhotoBookmarkDto("2"))
                val emittedPhotos2 = awaitItem()
                assertTrue(emittedPhotos2.size == 2)
                assertTrue(emittedPhotos2[0].isBookmarked.not() && emittedPhotos2[1].isBookmarked)

                cancelAndIgnoreRemainingEvents()
            }

            coVerify(exactly = 1) { photoRepository.getLatestPhotos(page) }
            coVerify(exactly = 1) {
                bookmarkRepository.getBookmarksByPhotoIdsFlow(
                    listOf(
                        "1",
                        "2",
                    ),
                )
            }
        }
}
