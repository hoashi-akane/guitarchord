package jp.ahoashi.guitarchord.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import jp.ahoashi.guitarchord.core.SettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SettingsRepositoryImplTest {
    private lateinit var repository: SettingsRepositoryImpl
    
    @Before
    fun setup() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        repository = SettingsRepositoryImpl(context)
    }
    
    @Test
    fun testSetAndGetLefty() = runBlocking {
        // 初期状態の確認
        val initialSetting = repository.getSettingStream().first()
        assertFalse("初期状態ではleftyはfalseであるべき", initialSetting.lefty)
        
        // leftyをtrueに設定
        repository.setLefty(true)
        val settingAfterTrue = repository.getSettingStream().first()
        assertTrue("setLefty(true)後はleftyがtrueであるべき", settingAfterTrue.lefty)
        
        // leftyをfalseに設定
        repository.setLefty(false)
        val settingAfterFalse = repository.getSettingStream().first()
        assertFalse("setLefty(false)後はleftyがfalseであるべき", settingAfterFalse.lefty)
    }
}