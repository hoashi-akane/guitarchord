package jp.ahoashi.guitarchord.data

import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@RunWith(AndroidJUnit4::class)
@Config(sdk = [33])
@OptIn(ExperimentalCoroutinesApi::class)
class SettingsRepositoryImplTest {
    private lateinit var repository: SettingsRepositoryImpl
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        
        val context = ApplicationProvider.getApplicationContext<android.content.Context>()
        repository = SettingsRepositoryImpl(context, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun initialStateReturnsFalseForLefty() = runTest(testDispatcher) {
        val result = repository.getSettingStream().first()
        
        assertFalse("初期状態ではleftyがfalseであること", result.lefty)
    }

    @Test
    fun setLeftyTrueAndGetStreamReturnsTrue() = runTest(testDispatcher) {
        // leftyをtrueに設定
        repository.setLefty(true)
        
        // 設定が反映されることを確認
        val result = repository.getSettingStream().first()
        
        assertTrue("setLefty(true)後はleftyがtrueであること", result.lefty)
    }

    @Test
    fun setLeftyFalseAndGetStreamReturnsFalse() = runTest(testDispatcher) {
        // 一度trueに設定
        repository.setLefty(true)
        
        // falseに設定し直し
        repository.setLefty(false)
        
        // 設定が反映されることを確認
        val result = repository.getSettingStream().first()
        
        assertFalse("setLefty(false)後はleftyがfalseであること", result.lefty)
    }

    @Test
    fun setLeftyMultipleTimesWorksCorrectly() = runTest(testDispatcher) {
        // 複数回の設定変更をテスト
        repository.setLefty(true)
        var result = repository.getSettingStream().first()
        assertTrue("1回目: leftyがtrueであること", result.lefty)
        
        repository.setLefty(false)
        result = repository.getSettingStream().first()
        assertFalse("2回目: leftyがfalseであること", result.lefty)
        
        repository.setLefty(true)
        result = repository.getSettingStream().first()
        assertTrue("3回目: leftyがtrueであること", result.lefty)
    }
}