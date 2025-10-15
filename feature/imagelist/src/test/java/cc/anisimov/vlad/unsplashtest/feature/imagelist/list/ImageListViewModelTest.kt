package cc.anisimov.vlad.unsplashtest.feature.imagelist.list

import app.cash.turbine.test
import cc.anisimov.vlad.core.common.rule.MainDispatcherRule
import cc.anisimov.vlad.unsplashtest.domain.interactor.AddPhotoBookmarkInteractor
import cc.anisimov.vlad.unsplashtest.domain.interactor.DeletePhotoBookmarkInteractor
import cc.anisimov.vlad.unsplashtest.domain.model.Photo
import cc.anisimov.vlad.unsplashtest.feature.imagelist.mapper.PhotoMapper
import cc.anisimov.vlad.unsplashtest.feature.imagelist.mapper.UserMapper
import cc.anisimov.vlad.unsplashtest.feature.imagelist.model.PhotoUi
import cc.anisimov.vlad.unsplashtest.feature.imagelist.model.UserUi
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.coVerifySequence
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import okio.IOException
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ImageListViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val addBookmarkInteractor = mockk<AddPhotoBookmarkInteractor>()
    private val deleteBookmarkInteractor = mockk<DeletePhotoBookmarkInteractor>()
    private val photoMapper = PhotoMapper(UserMapper())
    private lateinit var imageListPager: ImageListPager
    private lateinit var imageListFlow: MutableSharedFlow<List<Photo>>
    private lateinit var isLoadingFlow: MutableStateFlow<Boolean>
    private lateinit var errorFlow: MutableSharedFlow<Throwable>
    private lateinit var imageListViewModel: ImageListViewModel

    @Before
    fun setUp() {
        imageListPager = mockk(relaxed = false)

        imageListFlow = MutableSharedFlow<List<Photo>>(replay = 1).also { it.tryEmit(emptyList()) }
        isLoadingFlow = MutableStateFlow(false)
        errorFlow = MutableSharedFlow(replay = 1)

        every { imageListPager.imageListFlow } returns imageListFlow
        every { imageListPager.isLoadingFlow } returns isLoadingFlow
        every { imageListPager.errorFlow } returns errorFlow

        coJustRun { imageListPager.loadMore() }
        coJustRun { addBookmarkInteractor(any()) }
        coJustRun { deleteBookmarkInteractor(any()) }

        imageListViewModel =
            ImageListViewModel(
                addPhotoBookmarkInteractor = addBookmarkInteractor,
                deletePhotoBookmarkInteractor = deleteBookmarkInteractor,
                imageListPager = imageListPager,
                photoMapper = photoMapper,
            )
    }

    @Test
    fun `calls load more once on init`() =
        runTest {
            // Given - ViewModel created in setUp

            // When - give viewModel time to init
            advanceUntilIdle()

            // Then
            coVerify(exactly = 1) { imageListPager.loadMore() }
        }

    @Test
    fun `emits navigation on author click`() =
        runTest {
            // Given
            val user = UserUi.stub

            // When
            imageListViewModel.eventsFlow.test {
                imageListViewModel.onAuthorClick(user)

                // Then
                assert(awaitItem() == ImageListScreenEvent.GoToAuthorProfile(user))
                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun `calls appropriate interactor on bookmark click`() =
        runTest {
            // Given
            val notBookmarkedPhoto = PhotoUi.stub.copy(isBookmarked = false)

            // When
            imageListViewModel.onBookmarkClick(notBookmarkedPhoto)
            val bookmarkedPhoto = PhotoUi.stub.copy(isBookmarked = true)
            imageListViewModel.onBookmarkClick(bookmarkedPhoto)
            advanceUntilIdle()

            // Then
            coVerifySequence {
                addBookmarkInteractor(notBookmarkedPhoto.id)
                deleteBookmarkInteractor(bookmarkedPhoto.id)
            }
            confirmVerified(addBookmarkInteractor, deleteBookmarkInteractor)
        }

    @Test
    fun `emits error event on pager error`() =
        runTest {
            // Given
            val errorMessage = "network error"

            // When
            imageListViewModel.eventsFlow.test {
                errorFlow.emit(IOException(errorMessage))

                // Then
                assertEquals(ImageListScreenEvent.ShowError(errorMessage), awaitItem())
                cancelAndIgnoreRemainingEvents()
            }
        }
}
