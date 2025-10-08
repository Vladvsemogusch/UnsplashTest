package cc.anisimov.vlad.unsplashtest.data.db.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo_bookmark")
class PhotoBookmarkEntity(
    @PrimaryKey
    @ColumnInfo(name = "photo_id")
    val photoId: String,
)
