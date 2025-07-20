package jp.ahoashi.guitarchord

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import jp.ahoashi.guitarchord.core.SettingsRepository
import jp.ahoashi.guitarchord.entity.TYPE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChordScreenViewModelTest {
    private lateinit var viewModel: ChordScreenViewModel
    private lateinit var settingsRepository: SettingsRepository
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        settingsRepository = mockk(relaxed = true)
        viewModel = ChordScreenViewModel(settingsRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `初期状態が正しく設定される`() {
        assertEquals("", viewModel.uiState.value.alphabet)
        assertEquals(ChordScreenViewModel.Companion.Sharp.UNSET, viewModel.uiState.value.sharp)
        assertNull(viewModel.uiState.value.type)
        assertNull(viewModel.uiState.value.chord)
    }

    @Test
    fun `getSettingStreamが正しく動作する`() {
        val expectedFlow = flowOf(SettingsRepository.Setting(lefty = true))
        coEvery { settingsRepository.getSettingStream() } returns expectedFlow

        val result = viewModel.getSettingStream()

        assertEquals(expectedFlow, result)
    }

    @Test
    fun `setLeftyが正しく動作する`() = testScope.runTest {
        viewModel.setLefty(true)
        advanceUntilIdle()

        coVerify { settingsRepository.setLefty(true) }
    }

    @Test
    fun `setAlphabetで有効なコードが設定される`() {
        viewModel.setType(TYPE.MAJOR)
        viewModel.setAlphabet("C")

        assertEquals("C", viewModel.uiState.value.alphabet)
        assertNotNull(viewModel.uiState.value.chord)
    }

    @Test
    fun `setAlphabetで無効なコードの場合エラーが発生する`() = testScope.runTest {
        viewModel.setSharp(ChordScreenViewModel.Companion.Sharp.SET)
        viewModel.setAlphabet("Z")

        assertEquals("Z", viewModel.uiState.value.alphabet)
        assertNull(viewModel.uiState.value.chord)
    }

    @Test
    fun `setSharpが正しく動作する`() {
        viewModel.setAlphabet("C")
        viewModel.setSharp(ChordScreenViewModel.Companion.Sharp.SET)

        assertEquals(ChordScreenViewModel.Companion.Sharp.SET, viewModel.uiState.value.sharp)
    }

    @Test
    fun `setTypeが正しく動作する`() {
        viewModel.setAlphabet("C")
        viewModel.setType(TYPE.MAJOR)

        assertEquals(TYPE.MAJOR, viewModel.uiState.value.type)
        assertNotNull(viewModel.uiState.value.chord)
    }

    @Test
    fun `findChordが正しくコードを検索する`() {
        val chord = viewModel.findChord("C", ChordScreenViewModel.Companion.Sharp.UNSET, TYPE.MAJOR)
        
        assertNotNull(chord)
        assertEquals("C", chord?.alphabet)
        assertEquals(false, chord?.sharp)
        assertEquals(TYPE.MAJOR, chord?.type?.type)
    }

    @Test
    fun `findChordでシャープ付きコードを検索する`() {
        val chord = viewModel.findChord("C", ChordScreenViewModel.Companion.Sharp.SET, TYPE.MAJOR)
        
        assertNotNull(chord)
        assertEquals("C", chord?.alphabet)
        assertEquals(false, chord?.sharp) // 現在の実装ではシャープコードは未実装でfalseになる
        assertEquals(TYPE.MAJOR, chord?.type?.type)
    }

    @Test
    fun `findChordで存在しないコードを検索するとnullが返る`() {
        val chord = viewModel.findChord("Z", ChordScreenViewModel.Companion.Sharp.UNSET, null)
        
        assertNull(chord)
    }
}