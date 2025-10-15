package cc.anisimov.vlad.unsplashtest.data.db

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import cc.anisimov.vlad.unsplashtest.data.db.dao.PhotoBookmarkDao
import cc.anisimov.vlad.unsplashtest.data.db.entity.PhotoBookmarkEntity
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PhotoBookmarkDaoTest {
    //  Switch all background tasks to run synchronously
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var database: AppDatabase
    private lateinit var bookmarkDao: PhotoBookmarkDao

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        database =
            Room
                .inMemoryDatabaseBuilder(context, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        bookmarkDao = database.getPhotoBookmarkDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insert_emits_new_id() =
        runTest {
            // Given
            val photoIds = listOf("a", "b")

            // When
            bookmarkDao.getBookmarksByPhotoIdsFlow(photoIds).test {
                // Then
                assertEquals(emptyList<PhotoBookmarkEntity>(), awaitItem())

                bookmarkDao.insertBookmark(PhotoBookmarkEntity("a"))
                assertEquals(listOf("a"), awaitItem().map { it.photoId })

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun replace_does_not_duplicate() =
        runTest {
            // Given
            val photoIds = listOf("a", "b")

            // When
            bookmarkDao.getBookmarksByPhotoIdsFlow(photoIds).test {
                // Then
                awaitItem()

                bookmarkDao.insertBookmark(PhotoBookmarkEntity("a"))
                assertEquals(listOf("a"), awaitItem().map { it.photoId })

                bookmarkDao.insertBookmark(PhotoBookmarkEntity("a"))
                assertEquals(listOf("a"), awaitItem().map { it.photoId })

                cancelAndIgnoreRemainingEvents()
            }
        }

    @Test
    fun delete_emits_removal() =
        runTest {
            // Given
            val photoIds = listOf("a", "b")

            // When
            bookmarkDao.getBookmarksByPhotoIdsFlow(photoIds).test {
                // Then
                awaitItem()

                val bookmark = PhotoBookmarkEntity("a")
                bookmarkDao.insertBookmark(bookmark)
                assertEquals(listOf("a"), awaitItem().map { it.photoId })
                bookmarkDao.deleteBookmark(bookmark)
                assertEquals(emptyList<PhotoBookmarkEntity>(), awaitItem())

                cancelAndIgnoreRemainingEvents()
            }
        }
}
