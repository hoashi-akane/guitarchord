package jp.ahoashi.guitarchord

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import jp.ahoashi.guitarchord.core.SettingsRepository
import jp.ahoashi.guitarchord.entity.Chord
import jp.ahoashi.guitarchord.entity.ChordList
import jp.ahoashi.guitarchord.entity.TYPE
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * TODO: エラー表示（コードが見つからない、半音上げ#
 */
class ChordScreenViewModel(
    val settingsRepository: SettingsRepository,
) : ViewModel() {
        val uiState: MutableStateFlow<ChordScreenUiState> = MutableStateFlow(ChordScreenUiState.Empty)
        val sharpError: MutableSharedFlow<Unit> = MutableSharedFlow()

        fun getSettingStream() = settingsRepository.getSettingStream()

        fun setLefty(lefty: Boolean) {
            viewModelScope.launch {
                settingsRepository.setLefty(lefty)
            }
        }

        fun setAlphabet(alphabet: String) {
            val chord = findChord(alphabet, uiState.value.sharp, uiState.value.type)
            chord ?: sharpError.tryEmit(Unit)

            uiState.update {
                it.copy(alphabet = alphabet, type = it.type, chord = chord)
            }
        }

        fun setSharp(sharp: Sharp) {
            val chord = findChord(uiState.value.alphabet, sharp, uiState.value.type)
            chord ?: sharpError.tryEmit(Unit)

            uiState.update {
                it.copy(sharp = sharp, chord = chord)
            }
        }

        fun setType(type: TYPE?) {
            val chord = findChord(uiState.value.alphabet, uiState.value.sharp, type)
            chord ?: sharpError.tryEmit(Unit)

            uiState.update {
                it.copy(type = type, chord = chord)
            }
        }

        fun findChord(
            alphabet: String,
            sharp: Sharp,
            type: TYPE?,
        ): Chord? = ChordList.findChord(alphabet, sharp == Sharp.SET, type)

        data class ChordScreenUiState(
            val alphabet: String,
            val sharp: Sharp,
            val type: TYPE?,
            val chord: Chord?,
        ) {
            companion object {
                val Empty =
                    ChordScreenUiState(
                        alphabet = "",
                        sharp = Sharp.UNSET,
                        type = null,
                        chord = null,
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
