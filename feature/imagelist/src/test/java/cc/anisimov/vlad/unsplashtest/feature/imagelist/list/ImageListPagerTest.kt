package cc.anisimov.vlad.unsplashtest.feature.imagelist.list

import app.cash.turbine.test
import cc.anisimov.vlad.unsplashtest.domain.interactor.GetLatestPhotosPageInteractor
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import io.mockk.coEvery
import io.mockk.coVerifySequence
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Test

class ImageListPagerTest {
    private lateinit var getLatestPhotosInteractor: GetLatestPhotosPageInteractor

    @Before
    fun setUp() {
        getLatestPhotosInteractor = mockk()
    }

    @Test
    fun `aggregates and deduplicates across pages`() =
        runTest {
            // Given
            coEvery { getLatestPhotosInteractor.invoke(1) } returns
                    flowOf(
                        listOf(
                            Photo.stub.copy(id = "1"),
                            Photo.stub.copy(id = "2"),
                        ),
                    )
            coEvery { getLatestPhotosInteractor.invoke(2) } returns
                    flowOf(
                        listOf(
                            Photo.stub.copy(id = "2"),
                            Photo.stub.copy(id = "3"),
                        ),
                    )
            val pager = ImageListPager(getLatestPhotosInteractor)

            // When
            pager.imageListFlow.test {
                expectNoEvents()

                val job =
                    backgroundScope.launch {
                        pager.loadMore()
                        pager.loadMore()
                    }

                // Then
                val page1PhotoIds = awaitItem().map { it.id }
                assertEquals(listOf("1", "2"), page1PhotoIds)

                val page2PhotoIds = awaitItem().map { it.id }
                assertEquals(listOf("1", "2", "3"), page2PhotoIds)

                job.join()
                cancelAndIgnoreRemainingEvents()
            }

            coVerifySequence {
                getLatestPhotosInteractor.invoke(1)
                getLatestPhotosInteractor.invoke(2)
            }
        }

    @Test
    fun `toggles loading flag around load`() =
        runTest {
            // Given
            coEvery { getLatestPhotosInteractor.invoke(any()) } returns flowOf(emptyList())
            val imageListPager = ImageListPager(getLatestPhotosInteractor)

            // When
            imageListPager.isLoadingFlow.test {
                assertEquals(false, awaitItem())

                val job = backgroundScope.launch { imageListPager.loadMore() }

                // Then
                assertEquals(true, awaitItem())
                assertEquals(false, awaitItem())

                job.join()
                expectNoEvents()
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `emits error and resets loading on failure`() =
        runTest {
            // Given
            val testErrorMessage = "test error"
            coEvery { getLatestPhotosInteractor(any()) } throws
                    IllegalStateException(
                        testErrorMessage,
                    )
            val imageListPager = ImageListPager(getLatestPhotosInteractor)

            // When (observe errors first to avoid missing emit)
            imageListPager.errorFlow.test {
                val job = backgroundScope.launch { imageListPager.loadMore() }

                // Then
                val error = awaitItem()
                assertEquals(testErrorMessage, error.message)

                job.join()
                assertFalse(imageListPager.isLoadingFlow.value)

                cancelAndIgnoreRemainingEvents()
            }
        }
}
