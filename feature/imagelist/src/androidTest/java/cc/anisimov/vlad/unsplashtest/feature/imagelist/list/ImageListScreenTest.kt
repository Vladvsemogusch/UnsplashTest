package cc.anisimov.vlad.unsplashtest.feature.imagelist.list

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import cc.anisimov.vlad.unsplashtest.feature.imagelist.model.PhotoUi
import cc.anisimov.vlad.unsplashtest.feature.imagelist.model.UserUi
import kotlinx.collections.immutable.persistentListOf
import org.junit.Rule
import org.junit.Test

class ImageListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun renders_items_and_triggers_author_click() {
        // Given
        val authorName1 = "Author 1"
        val authorName2 = "Author 2"
        val authorName3 = "Author 3"

        val photos =
            listOf(
                PhotoUi.stub.copy(id = "1", author = UserUi.stub.copy(name = authorName1)),
                PhotoUi.stub.copy(id = "2", author = UserUi.stub.copy(name = authorName2)),
                PhotoUi.stub.copy(id = "3", author = UserUi.stub.copy(name = authorName3)),
            )
        val screenState =
            ImageListScreenState.Content.Ready(photoList = persistentListOf(*photos.toTypedArray()))

        var authorClicked: String? = null
        val actions =
            object : ImageListScreenActions {
                override fun onBookmarkClick(photo: PhotoUi) {}

                override fun onAuthorClick(author: UserUi) {
                    authorClicked = author.name
                }

                override fun onListBottomItemReached() {}
            }

        // When
        composeTestRule.setContent {
            ImageListScreen(
                screenState = screenState,
                screenActions = actions,
                snackbarHostState = SnackbarHostState(),
            )
        }

        // Then
        composeTestRule.onAllNodesWithText(authorName1).assertCountEquals(1)
        composeTestRule.onAllNodesWithText(authorName2).assertCountEquals(1)
        composeTestRule.onAllNodesWithText(authorName3).assertCountEquals(1)

        composeTestRule.onNodeWithText(authorName2).performClick()
        assert(authorClicked == authorName2)
    }
}
