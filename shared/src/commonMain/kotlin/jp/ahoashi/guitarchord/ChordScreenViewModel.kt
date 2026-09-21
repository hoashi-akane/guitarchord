package jp.ahoashi.guitarchord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.ahoashi.guitarchord.chordsdb.ChordVoicingRepository
import jp.ahoashi.guitarchord.chordsdb.model.ChordVoicing
import jp.ahoashi.guitarchord.chordsdb.model.ChordVoicingSet
import jp.ahoashi.guitarchord.core.AppTheme
import jp.ahoashi.guitarchord.core.SettingsRepository
import jp.ahoashi.guitarchord.core.SettingsRepository.Setting
import jp.ahoashi.guitarchord.entity.TYPE
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * TODO: エラー表示（コードが見つからない、半音上げ#
 */
class ChordScreenViewModel(
    val settingsRepository: SettingsRepository,
    private val chordVoicingRepository: ChordVoicingRepository,
) : ViewModel() {
    val uiState: MutableStateFlow<ChordScreenUiState> = MutableStateFlow(ChordScreenUiState.Empty)
    val sharpError: MutableSharedFlow<Unit> = MutableSharedFlow()

    val settingState: StateFlow<Setting> =
        settingsRepository
            .getSettingStream()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = Setting(),
            )

    // 連続してボタンが押された際に、古い検索結果が新しい結果を上書きしないよう直前の検索を取り消す
    private var loadJob: Job? = null

    fun getSettingStream() = settingState

    fun setLefty(lefty: Boolean) {
        viewModelScope.launch {
            settingsRepository.setLefty(lefty)
        }
    }

    fun setTheme(theme: AppTheme) {
        viewModelScope.launch {
            settingsRepository.setTheme(theme)
        }
    }

    fun setAlphabet(alphabet: String) {
        uiState.update { it.copy(alphabet = alphabet) }
        loadVoicings()
    }

    fun setSharp(sharp: Sharp) {
        uiState.update { it.copy(sharp = sharp) }
        loadVoicings()
    }

    fun setType(type: TYPE?) {
        uiState.update { it.copy(type = type) }
        loadVoicings()
    }

    /** 大きく表示する押さえ方を、次(なければ先頭)へ切り替える。 */
    fun nextVoicing() {
        uiState.update { state ->
            val size = state.voicings.size
            if (size == 0) state else state.copy(voicingIndex = (state.voicingIndex + 1) % size)
        }
    }

    /** 大きく表示する押さえ方を、前(なければ末尾)へ切り替える。 */
    fun previousVoicing() {
        uiState.update { state ->
            val size = state.voicings.size
            if (size == 0) state else state.copy(voicingIndex = (state.voicingIndex - 1 + size) % size)
        }
    }

    private fun loadVoicings() {
        val state = uiState.value
        val type = state.type
        loadJob?.cancel()
        loadJob =
            viewModelScope.launch {
                // 音名とコードタイプが揃うまでは検索しない
                val voicingSet =
                    if (state.alphabet.isEmpty() || type == null) {
                        null
                    } else {
                        chordVoicingRepository.findChordVoicingSet(
                            alphabet = state.alphabet,
                            sharp = state.sharp == Sharp.SET,
                            type = type,
                        )
                    }
                uiState.update { it.copy(voicingSet = voicingSet, voicingIndex = 0) }
                if (voicingSet == null && state.alphabet.isNotEmpty() && type != null) {
                    sharpError.emit(Unit) // コード見つからない -> エラー通知
                }
            }
    }

    data class ChordScreenUiState(
        val alphabet: String,
        val sharp: Sharp,
        val type: TYPE?,
        // 選択中のコードの押さえ方一覧。先頭が基本の押さえ方。
        val voicingSet: ChordVoicingSet?,
        // 大きく表示している押さえ方の位置(voicingSet内のindex)
        val voicingIndex: Int = 0,
    ) {
        val voicings: List<ChordVoicing>
            get() = voicingSet?.voicings ?: emptyList()

        val currentVoicing: ChordVoicing?
            get() = voicings.getOrNull(voicingIndex)

        companion object {
            val Empty =
                ChordScreenUiState(
                    alphabet = "",
                    sharp = Sharp.UNSET,
                    type = null,
                    voicingSet = null,
                )
        }
    }

    companion object {
        enum class Sharp {
            DISABLE,
            SET,
            UNSET,
        }
    }
}
