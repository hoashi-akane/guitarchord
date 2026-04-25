package jp.ahoashi.guitarchord

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import jp.ahoashi.guitarchord.topbar.ChordScreenSettingsButton
import jp.ahoashi.guitarchord.ui.theme.GuitarchordTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()

        super.onCreate(savedInstanceState)
        setContent {
            GuitarchordTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Box {
                        ChordScreen(modifier = Modifier.fillMaxSize())
                        ChordScreenSettingsButton(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .statusBarsPadding(),
                        )
                    }
                }
            }
        }
    }
}
