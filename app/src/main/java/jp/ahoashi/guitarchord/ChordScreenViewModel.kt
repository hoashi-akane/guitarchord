package jp.ahoashi.guitarchord

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
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
            uiState.update { it.copy(alphabet = alphabet) }
        }

        fun setSharp(sharp: Sharp) {
            uiState.update { it.copy(sharp = sharp) }
        }

        data class ChordScreenUiState(
            val alphabet: String?,
            val sharp: Sharp,
            val type: TYPE?,
        ) {
            companion object {
                val Empty =
                    ChordScreenUiState(
                        alphabet = null,
                        sharp = Sharp.UNSET,
                        type = null,
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
