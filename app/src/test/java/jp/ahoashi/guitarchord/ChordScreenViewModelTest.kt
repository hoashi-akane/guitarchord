package jp.ahoashi.guitarchord

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import jp.ahoashi.guitarchord.chordsdb.ChordVoicingRepository
import jp.ahoashi.guitarchord.chordsdb.model.ChordVoicing
import jp.ahoashi.guitarchord.chordsdb.model.ChordVoicingSet
import jp.ahoashi.guitarchord.chordsdb.model.StringFret
import jp.ahoashi.guitarchord.core.SettingsRepository
import jp.ahoashi.guitarchord.entity.TYPE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ChordScreenViewModelTest {
    private lateinit var viewModel: ChordScreenViewModel
    private lateinit var settingsRepository: SettingsRepository
    private lateinit var chordVoicingRepository: ChordVoicingRepository
    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        settingsRepository = mockk(relaxed = true)
        chordVoicingRepository = mockk()
        viewModel = ChordScreenViewModel(settingsRepository, chordVoicingRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun voicingSet(key: String) =
        ChordVoicingSet(
            key = key,
            suffix = "major",
            voicings =
                listOf(
                    ChordVoicing(
                        baseFret = 1,
                        strings = listOf(StringFret(stringNumber = 1, fret = 0, finger = 0)),
                        barres = emptyList(),
                        isCapoSuggested = false,
                    ),
                ),
        )

    private fun voicingSetWith(count: Int) =
        ChordVoicingSet(
            key = "C",
            suffix = "major",
            voicings =
                List(count) { index ->
                    ChordVoicing(
                        baseFret = index + 1,
                        strings = listOf(StringFret(stringNumber = 1, fret = 0, finger = 0)),
                        barres = emptyList(),
                        isCapoSuggested = false,
                    )
                },
        )

    private fun TestScope.selectCMajor(set: ChordVoicingSet) {
        coEvery { chordVoicingRepository.findChordVoicingSet("C", false, TYPE.MAJOR) } returns set
        viewModel.setType(TYPE.MAJOR)
        viewModel.setAlphabet("C")
        advanceUntilIdle()
    }

    @Test
    fun `初期状態が正しく設定される`() {
        assertEquals("", viewModel.uiState.value.alphabet)
        assertEquals(ChordScreenViewModel.Companion.Sharp.UNSET, viewModel.uiState.value.sharp)
        assertNull(viewModel.uiState.value.type)
        assertNull(viewModel.uiState.value.voicingSet)
    }

    @Test
    fun `getSettingStreamが正しく動作する`() {
        val expectedFlow = flowOf(SettingsRepository.Setting(lefty = true))
        coEvery { settingsRepository.getSettingStream() } returns expectedFlow

        val result = viewModel.getSettingStream()

        assertEquals(expectedFlow, result)
    }

    @Test
    fun `setLeftyが正しく動作する`() =
        testScope.runTest {
            viewModel.setLefty(true)
            advanceUntilIdle()

            coVerify { settingsRepository.setLefty(true) }
        }

    @Test
    fun `音名とコードタイプが揃うと押さえ方が取得される`() =
        testScope.runTest {
            val expected = voicingSet("C")
            coEvery { chordVoicingRepository.findChordVoicingSet("C", false, TYPE.MAJOR) } returns expected

            viewModel.setType(TYPE.MAJOR)
            viewModel.setAlphabet("C")
            advanceUntilIdle()

            assertEquals("C", viewModel.uiState.value.alphabet)
            assertEquals(TYPE.MAJOR, viewModel.uiState.value.type)
            assertEquals(expected, viewModel.uiState.value.voicingSet)
        }

    @Test
    fun `音名だけではリポジトリを検索しない`() =
        testScope.runTest {
            viewModel.setAlphabet("C")
            advanceUntilIdle()

            coVerify(exactly = 0) { chordVoicingRepository.findChordVoicingSet(any(), any(), any()) }
            assertNull(viewModel.uiState.value.voicingSet)
        }

    @Test
    fun `setSharpがリポジトリに半音上げとして渡される`() =
        testScope.runTest {
            val expected = voicingSet("Csharp")
            coEvery { chordVoicingRepository.findChordVoicingSet("C", true, TYPE.MAJOR) } returns expected

            viewModel.setAlphabet("C")
            viewModel.setType(TYPE.MAJOR)
            viewModel.setSharp(ChordScreenViewModel.Companion.Sharp.SET)
            advanceUntilIdle()

            assertEquals(ChordScreenViewModel.Companion.Sharp.SET, viewModel.uiState.value.sharp)
            assertEquals(expected, viewModel.uiState.value.voicingSet)
        }

    @Test
    fun `該当するコードがない場合はvoicingSetがnullになりエラーが通知される`() =
        testScope.runTest {
            coEvery { chordVoicingRepository.findChordVoicingSet("E", true, TYPE.MAJOR) } returns null
            val errors = mutableListOf<Unit>()
            backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.sharpError.collect { errors.add(it) } }
            advanceUntilIdle()

            viewModel.setSharp(ChordScreenViewModel.Companion.Sharp.SET)
            viewModel.setAlphabet("E")
            viewModel.setType(TYPE.MAJOR)
            advanceUntilIdle()

            assertNull(viewModel.uiState.value.voicingSet)
            assertEquals(1, errors.size)
        }

    @Test
    fun `連続して選択した場合は最後の選択の結果が表示される`() =
        testScope.runTest {
            val slowC = voicingSet("C")
            val fastD = voicingSet("D")
            coEvery { chordVoicingRepository.findChordVoicingSet("C", false, TYPE.MAJOR) } coAnswers {
                delay(100)
                slowC
            }
            coEvery { chordVoicingRepository.findChordVoicingSet("D", false, TYPE.MAJOR) } returns fastD

            viewModel.setType(TYPE.MAJOR)
            viewModel.setAlphabet("C")
            advanceTimeBy(10)
            viewModel.setAlphabet("D")
            advanceUntilIdle()

            assertEquals(fastD, viewModel.uiState.value.voicingSet)
        }

    @Test
    fun `nextVoicingで次の押さえ方に切り替わり末尾の次は先頭に戻る`() =
        testScope.runTest {
            selectCMajor(voicingSetWith(3))
            assertEquals(0, viewModel.uiState.value.voicingIndex)

            viewModel.nextVoicing()
            assertEquals(1, viewModel.uiState.value.voicingIndex)
            viewModel.nextVoicing()
            assertEquals(2, viewModel.uiState.value.voicingIndex)
            viewModel.nextVoicing()
            assertEquals(0, viewModel.uiState.value.voicingIndex)
        }

    @Test
    fun `previousVoicingで前の押さえ方に切り替わり先頭の前は末尾になる`() =
        testScope.runTest {
            selectCMajor(voicingSetWith(3))

            viewModel.previousVoicing()
            assertEquals(2, viewModel.uiState.value.voicingIndex)
            viewModel.previousVoicing()
            assertEquals(1, viewModel.uiState.value.voicingIndex)
        }

    @Test
    fun `currentVoicingは選択中の押さえ方を返す`() =
        testScope.runTest {
            val set = voicingSetWith(3)
            selectCMajor(set)

            viewModel.nextVoicing()

            assertEquals(set.voicings[1], viewModel.uiState.value.currentVoicing)
        }

    @Test
    fun `コードを切り替えると先頭の押さえ方に戻る`() =
        testScope.runTest {
            selectCMajor(voicingSetWith(3))
            viewModel.nextVoicing()
            assertEquals(1, viewModel.uiState.value.voicingIndex)

            coEvery { chordVoicingRepository.findChordVoicingSet("D", false, TYPE.MAJOR) } returns voicingSetWith(2)
            viewModel.setAlphabet("D")
            advanceUntilIdle()

            assertEquals(0, viewModel.uiState.value.voicingIndex)
        }

    @Test
    fun `押さえ方がない状態で切り替えても何も起きない`() =
        testScope.runTest {
            viewModel.nextVoicing()
            viewModel.previousVoicing()

            assertEquals(0, viewModel.uiState.value.voicingIndex)
            assertNull(viewModel.uiState.value.currentVoicing)
        }
}
