package ch.kra.mycellar.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import ch.kra.myapplication.ui.theme.Shapes
import ch.kra.myapplication.ui.theme.Typography

private val DarkColorPalette = darkColors(
    primary = Color.Black,
    onPrimary = Color.White,
    surface = BG500Dark,
    onSurface = Color.White
)

private val LightColorPalette = lightColors(
    primary = Color.White,
    onPrimary = Color.Black,
    surface = BG500Light,
    onSurface = Color.White

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun MyCellarTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}