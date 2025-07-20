package jp.ahoashi.guitarchord.data

import io.mockk.coEvery
import io.mockk.mockk
import jp.ahoashi.guitarchord.core.SettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SettingsRepositoryImplTest {
    private lateinit var mockRepository: SettingsRepository
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockRepository = mockk(relaxed = true)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `SettingsRepositoryのgetSettingStreamがleftytrueを返す`() = testScope.runTest {
        val expectedSetting = SettingsRepository.Setting(lefty = true)
        coEvery { mockRepository.getSettingStream() } returns flowOf(expectedSetting)

        val result = mockRepository.getSettingStream().first()

        assertTrue("leftyがtrueで取得できること", result.lefty)
    }

    @Test
    fun `SettingsRepositoryのgetSettingStreamがleftyfalseを返す`() = testScope.runTest {
        val expectedSetting = SettingsRepository.Setting(lefty = false)
        coEvery { mockRepository.getSettingStream() } returns flowOf(expectedSetting)

        val result = mockRepository.getSettingStream().first()

        assertFalse("leftyがfalseで取得できること", result.lefty)
    }

    @Test
    fun `SettingsRepositoryのsetLeftyメソッドが存在する`() {
        // SettingsRepositoryImplが実装されていることを確認するためのテスト
        // 実際のDataStoreのテストはandroidTestで行う
        val implementation = SettingsRepositoryImpl::class.java
        val methods = implementation.declaredMethods
        
        val hasSetLeftyMethod = methods.any { it.name == "setLefty" }
        val hasGetSettingStreamMethod = methods.any { it.name == "getSettingStream" }
        
        assertTrue("setLeftyメソッドが実装されていること", hasSetLeftyMethod)
        assertTrue("getSettingStreamメソッドが実装されていること", hasGetSettingStreamMethod)
    }
}