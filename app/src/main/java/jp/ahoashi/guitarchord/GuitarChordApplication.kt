package jp.ahoashi.guitarchord

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.android.ump.ConsentInformation
import com.google.android.ump.UserMessagingPlatform
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class GuitarChordApplication : Application() {
    private lateinit var consentInformation: ConsentInformation

    override fun onCreate() {
        super.onCreate()
        consentInformation = UserMessagingPlatform.getConsentInformation(this)

        CoroutineScope(Dispatchers.IO).launch {
            // Initialize the Google Mobile Ads SDK on a background thread.
            MobileAds.initialize(this@GuitarChordApplication) {}
        }
    }
}
