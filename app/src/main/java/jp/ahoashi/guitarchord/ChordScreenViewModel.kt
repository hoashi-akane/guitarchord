package jp.ahoashi.guitarchord

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ahoashi.guitarchord.entity.Chord
import jp.ahoashi.guitarchord.entity.ChordList
import jp.ahoashi.guitarchord.entity.TYPE
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

/**
 * TODO: エラー表示（コードが見つからない、半音上げ#
 */
@HiltViewModel
class ChordScreenViewModel
    @Inject
    constructor() : ViewModel() {
        val uiState: MutableStateFlow<ChordScreenUiState> = MutableStateFlow(ChordScreenUiState.Empty)
        val sharpError: MutableSharedFlow<Boolean> = MutableSharedFlow()

        fun setAlphabet(alphabet: String) {
            uiState.update {
                val chord = findChord(alphabet, it.sharp, it.type)
                it.copy(alphabet = alphabet, type = it.type, chord = chord)
            }
        }

        fun setSharp(sharp: Sharp) {
            uiState.update {
                val chord = findChord(it.alphabet, sharp, it.type)
                it.copy(sharp = sharp, chord = chord)
            }
        }

        fun setType(type: TYPE?) {
            uiState.update {
                val chord = findChord(it.alphabet, it.sharp, type)
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
