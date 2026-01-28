package co.hitech.billar_app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColorScheme = lightColorScheme(
    primary = TableGreen,
    secondary = WoodBrown,
    tertiary = AccentGold,
    background = BackgroundLight,
    surface = CardBackground,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val DarkColorScheme = lightColorScheme(
    primary = TableGreen,
    secondary = WoodBrown,
    tertiary = AccentGold,
    background = BackgroundLight,
    surface = CardBackground,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

@Composable
fun BillarappTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme  // Always use light theme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}