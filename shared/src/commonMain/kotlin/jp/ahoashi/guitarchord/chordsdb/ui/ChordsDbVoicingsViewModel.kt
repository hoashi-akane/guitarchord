package jp.ahoashi.guitarchord.chordsdb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.ahoashi.guitarchord.chordsdb.ChordVoicingRepository
import jp.ahoashi.guitarchord.chordsdb.model.ChordVoicingSet
import jp.ahoashi.guitarchord.entity.TYPE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChordsDbVoicingsViewModel(
    private val repository: ChordVoicingRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

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

    private fun load() {
        val alphabet = _uiState.value.alphabet
        val type = _uiState.value.type
        viewModelScope.launch {
            val voicingSet = repository.findChordVoicingSet(alphabet, sharp = false, type = type)
            _uiState.update { it.copy(voicingSet = voicingSet) }
        }
    }

    data class UiState(
        val alphabet: String = "C",
        val type: TYPE = TYPE.MAJOR,
        val voicingSet: ChordVoicingSet? = null,
    )
}
