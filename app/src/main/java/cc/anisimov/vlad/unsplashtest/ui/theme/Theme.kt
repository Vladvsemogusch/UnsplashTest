package cc.anisimov.vlad.unsplashtest.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorPalette = lightColorScheme(
    onPrimary = Color.White,
    onSurface = Color.Black,
    primary = Teal200,
    secondary = Purple500,
    onSecondary = Color.Black,
    surface = Color.White,
    background = Color.White
)

@Composable
fun UnsplashTestTheme(content: @Composable () -> Unit) {
        MaterialTheme(
            colorScheme = LightColorPalette,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
}