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

    @Query("SELECT * from photo_bookmark")
    fun getAllBookmarksFlow(): Flow<List<PhotoBookmarkEntity>>
}
