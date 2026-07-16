package jp.ahoashi.guitarchord.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import jp.ahoashi.guitarchord.core.AppTheme

@Composable
expect fun GuitarchordTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    theme: AppTheme = AppTheme.TEAL,
    content: @Composable () -> Unit,
)
