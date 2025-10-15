package cc.anisimov.vlad.unsplashtest.domain.mapper

import cc.anisimov.vlad.unsplashtest.domain.model.dto.PhotoBookmarkDto
import cc.anisimov.vlad.unsplashtest.domain.model.dto.PhotoDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class DtoPhotoMapperTest {
    private val mapper = DtoPhotoMapper()

    @Test
    fun `maps bookmarked flag by ids`() {
        //  Given
        val photos =
            listOf(
                PhotoDto.stub.copy(id = "1"),
                PhotoDto.stub.copy(id = "2"),
            )
        val bookmarks = listOf(PhotoBookmarkDto("2"))

        //  When
        val mappedPhotos = mapper.map(photos, bookmarks)

        // Then
        assertEquals(2, mappedPhotos.size)
        assertFalse(mappedPhotos.find { it.id == "1" }!!.isBookmarked)
        assertTrue(mappedPhotos.find { it.id == "2" }!!.isBookmarked)
    }

    @Test
    fun `maps all as unbookmarked when bookmarks empty`() {
        //  Given
        val photos =
            listOf(
                PhotoDto.stub.copy(id = "1"),
            )

        //  When
        val result = mapper.map(photos, emptyList())

        //  Then
        assertTrue(result.none { it.isBookmarked })
    }
}
