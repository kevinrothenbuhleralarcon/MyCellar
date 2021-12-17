package ch.kra.myapplication.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Red300Dark,
    primaryVariant = Red500,
    onPrimary = Color.White,
    background = Color.Black,
    secondary = Red300Dark,
    onSecondary = Color.White,
    onSurface = Color.White
)

private val LightColorPalette = lightColors(
    primary = Red500,
    primaryVariant = Red500Light,
    onPrimary = Color.White,
    background = Red300Light,
    secondary = Red300,
    onSecondary = Color.Black,
    onSurface = Color.Black

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