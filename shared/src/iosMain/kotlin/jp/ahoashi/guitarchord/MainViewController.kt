package jp.ahoashi.guitarchord

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.UIKitView
import androidx.compose.ui.window.ComposeUIViewController
import jp.ahoashi.guitarchord.licenses.LicensesScreen
import jp.ahoashi.guitarchord.core.AppTheme
import jp.ahoashi.guitarchord.core.SettingsRepository.Setting
import jp.ahoashi.guitarchord.topbar.ChordScreenSettingsButton
import jp.ahoashi.guitarchord.ui.theme.AppTypography
import jp.ahoashi.guitarchord.ui.theme.blueDarkScheme
import jp.ahoashi.guitarchord.ui.theme.blueLightScheme
import jp.ahoashi.guitarchord.ui.theme.darkScheme
import jp.ahoashi.guitarchord.ui.theme.greenDarkScheme
import jp.ahoashi.guitarchord.ui.theme.greenLightScheme
import jp.ahoashi.guitarchord.ui.theme.lightScheme
import jp.ahoashi.guitarchord.ui.theme.redDarkScheme
import jp.ahoashi.guitarchord.ui.theme.redLightScheme
import jp.ahoashi.guitarchord.ui.theme.monoDarkScheme
import jp.ahoashi.guitarchord.ui.theme.monoLightScheme
import jp.ahoashi.guitarchord.ui.theme.yellowDarkScheme
import jp.ahoashi.guitarchord.ui.theme.yellowLightScheme
import org.koin.compose.viewmodel.koinViewModel
import platform.UIKit.UIView

fun MainViewController(
    onPrivacyOptionsClick: (() -> Unit)? = null,
    bannerViewFactory: (() -> UIView)? = null,
) = ComposeUIViewController {
    val viewModel: ChordScreenViewModel = koinViewModel()
    val canShowAds by AdsState.canShowAds.collectAsState()
    val setting by viewModel.getSettingStream().collectAsState(Setting())
    val darkTheme = isSystemInDarkTheme()
    var showLicenses by remember { mutableStateOf(false) }

    val colorScheme = when (setting.theme) {
        AppTheme.TEAL -> if (darkTheme) darkScheme else lightScheme
        AppTheme.RED -> if (darkTheme) redDarkScheme else redLightScheme
        AppTheme.BLUE -> if (darkTheme) blueDarkScheme else blueLightScheme
        AppTheme.GREEN -> if (darkTheme) greenDarkScheme else greenLightScheme
        AppTheme.YELLOW -> if (darkTheme) yellowDarkScheme else yellowLightScheme
        AppTheme.MONO -> if (darkTheme) monoDarkScheme else monoLightScheme
    }

    MaterialTheme(colorScheme = colorScheme, typography = AppTypography) {
        Surface(modifier = Modifier.fillMaxSize()) {
            if (showLicenses) {
                LicensesScreen(onBack = { showLicenses = false })
            } else {
                Column(modifier = Modifier.fillMaxSize()) {
                    Box(modifier = Modifier.weight(1f)) {
                        ChordScreen(modifier = Modifier.fillMaxSize())
                        ChordScreenSettingsButton(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .statusBarsPadding(),
                            viewModel = viewModel,
                            onPrivacyOptionsClick = onPrivacyOptionsClick,
                            onLicensesClick = { showLicenses = true },
                        )
                    }
                    if (canShowAds && bannerViewFactory != null) {
                        UIKitView(
                            factory = bannerViewFactory,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(54.dp),
                        )
                    }
                }
            }
        }
    }
}
