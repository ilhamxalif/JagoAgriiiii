package il.massive.jagoagriiiii.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val LightColorPalette = lightColorScheme(
    primary = Color(0xFF388E3C),  // Warna hijau untuk tombol dan elemen utama
    secondary = Color(0xFFFFC107),  // Warna sekunder untuk elemen lain
)

@Composable
fun JagoAgriTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorPalette,
        typography = Typography,
        content = content
    )
}
