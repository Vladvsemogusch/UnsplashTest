package cc.anisimov.vlad.unsplashtest.data.db.dao

import androidx.room.*
import cc.anisimov.vlad.unsplashtest.data.db.entity.PhotoBookmarkEntity

@Dao
abstract class PhotoBookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertBookmark(bookmark: PhotoBookmarkEntity)

    @Delete
    abstract suspend fun deleteBookmark(bookmark: PhotoBookmarkEntity)

    @Query("SELECT * from photo_bookmark")
    abstract suspend fun getAllBookmarks(): List<PhotoBookmarkEntity>
}