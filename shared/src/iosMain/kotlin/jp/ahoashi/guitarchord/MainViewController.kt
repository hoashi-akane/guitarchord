package jp.ahoashi.guitarchord

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeUIViewController
import org.koin.compose.KoinContext

fun MainViewController() = ComposeUIViewController {
    KoinContext {
        ChordScreen(modifier = Modifier.fillMaxSize())
    }
}
