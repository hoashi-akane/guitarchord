package jp.ahoashi.guitarchord

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import jp.ahoashi.guitarchord.entity.Chord
import jp.ahoashi.guitarchord.entity.ChordList
import jp.ahoashi.guitarchord.entity.TYPE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ChordScreenViewModel
    @Inject
    constructor() : ViewModel() {
        val uiState: MutableStateFlow<ChordScreenUiState> = MutableStateFlow(ChordScreenUiState.Empty)

        fun setAlphabet(alphabet: String) {
            uiState.update {
                val chord = findChord(alphabet, it.sharp, it.type)
                it.copy(alphabet = alphabet, type = it.type, chord = chord)
            }
        }

        fun setSharp(sharp: Sharp) {
            uiState.update { it.copy(sharp = sharp) }
        }

        fun findChord(
            alphabet: String,
            sharp: Sharp,
            type: TYPE?,
        ): Chord? =
            when {
                alphabet == "C" -> {
                    if (sharp == Sharp.SET) {
                        ChordList.C.map.getOrDefault(type, ChordList.C.cMajor)
                    } else {
                        ChordList.C.map.getOrDefault(type, ChordList.C.cMajor)
                    }
                }
                else -> {
                    ChordList.C.cMajor
                }
            }

        data class ChordScreenUiState(
            val alphabet: String?,
            val sharp: Sharp,
            val type: TYPE?,
            val chord: Chord?,
        ) {
            companion object {
                val Empty =
                    ChordScreenUiState(
                        alphabet = null,
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
