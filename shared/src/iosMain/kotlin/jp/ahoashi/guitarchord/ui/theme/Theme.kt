package jp.ahoashi.guitarchord.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import jp.ahoashi.guitarchord.core.AppTheme

@Composable
actual fun GuitarchordTheme(
    darkTheme: Boolean,
    theme: AppTheme,
    content: @Composable () -> Unit,
) {
    MaterialTheme(
        colorScheme =
            if (theme == AppTheme.MONO) {
                if (darkTheme) monoDarkScheme else monoLightScheme
            } else if (darkTheme) {
                darkScheme
            } else {
                lightScheme
            },
        typography = AppTypography,
        content = content,
    )
}
