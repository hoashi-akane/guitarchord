package jp.ahoashi.guitarchord

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.ump.ConsentDebugSettings
import com.google.android.ump.ConsentRequestParameters
import com.google.android.ump.UserMessagingPlatform
import dagger.hilt.android.AndroidEntryPoint
import jp.ahoashi.guitarchord.topbar.ChordScreenSettingsButton
import jp.ahoashi.guitarchord.ui.theme.GuitarchordTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val canShowAds = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        val consentInformation = UserMessagingPlatform.getConsentInformation(this)

        // 2回目以降の起動で既に同意済みの場合はすぐに広告を表示
        if (consentInformation.canRequestAds()) {
            canShowAds.value = true
        }

        val params =
            ConsentRequestParameters
                .Builder()
                .apply {
                    if (AppConfig.UMP_DEBUG_GEOGRAPHY) {
                        // NOTE:GDPR、欧州規制の各種ダイアログ確認用
//                        consentInformation.reset()
//                        setConsentDebugSettings(
//                            ConsentDebugSettings
//                                .Builder(this@MainActivity)
//                                .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
//                                .addTestDeviceHashedId("FBC43E98BF5FBE7614AD313A0A50D5CC")
//                                .build(),
//                        )
                    }
                }.build()

        consentInformation.requestConsentInfoUpdate(
            this,
            params,
            {
                UserMessagingPlatform.loadAndShowConsentFormIfRequired(this) {
                    canShowAds.value = consentInformation.canRequestAds()
                }
            },
            {
                canShowAds.value = consentInformation.canRequestAds()
            },
        )

        setContent {
            GuitarchordTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Column {
                        Box(modifier = Modifier.weight(1f)) {
                            ChordScreen(modifier = Modifier.fillMaxSize())
                            ChordScreenSettingsButton(
                                modifier =
                                    Modifier
                                        .align(Alignment.TopEnd)
                                        .statusBarsPadding(),
                            )
                        }
                        if (canShowAds.value) {
                            BannerAd(
                                modifier =
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(top = 4.dp)
                                        .navigationBarsPadding(),
                            )
                        }
                    }
                }
            }
        }
    }
}
