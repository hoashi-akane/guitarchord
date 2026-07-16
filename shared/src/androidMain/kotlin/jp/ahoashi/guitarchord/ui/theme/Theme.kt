package jp.ahoashi.guitarchord.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import jp.ahoashi.guitarchord.core.AppTheme

@Composable
actual fun GuitarchordTheme(
    darkTheme: Boolean,
    theme: AppTheme,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        theme == AppTheme.MONO -> if (darkTheme) monoDarkScheme else monoLightScheme
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> darkScheme
        else -> lightScheme
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content,
    )
}
