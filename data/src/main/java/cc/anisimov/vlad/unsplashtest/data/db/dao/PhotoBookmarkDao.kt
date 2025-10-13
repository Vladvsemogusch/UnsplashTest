package cc.anisimov.vlad.unsplashtest.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import cc.anisimov.vlad.unsplashtest.data.db.entity.PhotoBookmarkEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoBookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmark: PhotoBookmarkEntity)

    @Delete
    suspend fun deleteBookmark(bookmark: PhotoBookmarkEntity)

    @Query("SELECT photo_id FROM photo_bookmark WHERE photo_id IN (:photoIds)")
    fun getBookmarksByPhotoIdsFlow(photoIds: List<String>): Flow<List<PhotoBookmarkEntity>>
}
