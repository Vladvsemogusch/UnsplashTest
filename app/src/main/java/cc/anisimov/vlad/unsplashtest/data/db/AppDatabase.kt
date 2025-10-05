package cc.anisimov.vlad.unsplashtest.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import cc.anisimov.vlad.unsplashtest.data.db.dao.PhotoBookmarkDao
import cc.anisimov.vlad.unsplashtest.data.db.entity.PhotoBookmarkEntity


@Database(
    entities = [
        PhotoBookmarkEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getPhotoBookmarkDao(): PhotoBookmarkDao

    companion object {
        private const val DATABASE_NAME = "app_database"

        fun createInstance(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                DATABASE_NAME
            )
                //  Should create proper migration strategy in production
                .fallbackToDestructiveMigration(true)
                .build()
        }
    }
}