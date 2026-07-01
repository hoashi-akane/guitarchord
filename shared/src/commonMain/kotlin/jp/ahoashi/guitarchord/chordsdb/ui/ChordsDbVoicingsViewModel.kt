package jp.ahoashi.guitarchord.chordsdb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.ahoashi.guitarchord.chordsdb.ChordVoicingRepository
import jp.ahoashi.guitarchord.chordsdb.model.ChordVoicing
import jp.ahoashi.guitarchord.chordsdb.model.ChordVoicingSet
import jp.ahoashi.guitarchord.core.SettingsRepository
import jp.ahoashi.guitarchord.core.SettingsRepository.Setting
import jp.ahoashi.guitarchord.entity.TYPE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChordsDbVoicingsViewModel(
    private val repository: ChordVoicingRepository,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    val settingState: StateFlow<Setting> = settingsRepository.getSettingStream()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Eagerly,
            initialValue = Setting(),
        )

    init {
        load()
    }

    fun setAlphabet(alphabet: String) {
        _uiState.update { it.copy(alphabet = alphabet) }
        load()
    }

    fun setType(type: TYPE) {
        _uiState.update { it.copy(type = type) }
        load()
    }

    fun nextVoicing() {
        _uiState.update { state ->
            val size = state.voicingSet?.voicings?.size ?: 0
            if (size == 0) state else state.copy(voicingIndex = (state.voicingIndex + 1) % size)
        }
    }

    fun previousVoicing() {
        _uiState.update { state ->
            val size = state.voicingSet?.voicings?.size ?: 0
            if (size == 0) state else state.copy(voicingIndex = (state.voicingIndex - 1 + size) % size)
        }
    }

    private fun load() {
        val alphabet = _uiState.value.alphabet
        val type = _uiState.value.type
        viewModelScope.launch {
            val voicingSet = repository.findChordVoicingSet(alphabet, sharp = false, type = type)
            _uiState.update { it.copy(voicingSet = voicingSet, voicingIndex = 0) }
        }
    }

    data class UiState(
        val alphabet: String = "C",
        val type: TYPE = TYPE.MAJOR,
        val voicingSet: ChordVoicingSet? = null,
        val voicingIndex: Int = 0,
    ) {
        val currentVoicing: ChordVoicing?
            get() = voicingSet?.voicings?.getOrNull(voicingIndex)
    }
}
