package jp.ahoashi.guitarchord

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.ahoashi.guitarchord.ChordScreenViewModel.Companion.Sharp
import jp.ahoashi.guitarchord.chordsdb.model.ChordVoicing
import jp.ahoashi.guitarchord.entity.TYPE
import jp.ahoashi.guitarchord.generated.resources.Res
import jp.ahoashi.guitarchord.generated.resources.other_voicings
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

/**
 * TODO: 表示デザインカスタム機能（文字、記号、アルファベット？）
 */
@Composable
fun ChordScreen(
    modifier: Modifier = Modifier,
    viewModel: ChordScreenViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollableState = rememberScrollState()

    val setting by viewModel.getSettingStream().collectAsState()

    Column(
        modifier =
            modifier
                .fillMaxHeight()
                .verticalScroll(scrollableState)
                .statusBarsPadding()
                .padding(start = 20.dp, end = 20.dp, top = 0.dp),
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.Bottom,
        ) {
            Text(
                text = uiState.alphabet + if (uiState.sharp == Sharp.SET) "#" else "",
                fontSize = 42.sp,
                color = MaterialTheme.colorScheme.primary,
                lineHeight = 42.sp,
            )
            val typeText = uiState.type?.displayName ?: ""
            if (typeText.isNotEmpty()) {
                Text(
                    text = typeText,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp),
                    lineHeight = 24.sp,
                )
            }
        }

        // 選択中の押さえ方を大きく表示する(初期表示は先頭=基本の形)
        ChordDiagram(
            voicing = uiState.currentVoicing,
            isLefty = setting.lefty,
        )

        VoicingPager(
            selectedIndex = uiState.voicingIndex,
            total = uiState.voicings.size,
            onPrevious = { viewModel.previousVoicing() },
            onNext = { viewModel.nextVoicing() },
        )

        AlphabetButtons(uiState, { viewModel.setAlphabet(it) }) {
            viewModel.setSharp(it)
        }
        TypeButtons(uiState) { viewModel.setType(it) }

        // 選択中以外の押さえ方は、ボタンの下に小さく並べる
        OtherVoicings(
            voicings = uiState.voicings,
            selectedIndex = uiState.voicingIndex,
            isLefty = setting.lefty,
        )
    }
}

/**
 * 前後の押さえ方へ切り替えるボタン。
 * ラベルの文字数が変わってもボタン位置が動かないよう、ボタンを両端に固定し、間にラベルを置く。
 * 押さえ方が1つ以下のときもボタンは無効にして表示し続け、コード選択で画面の高さが変わらないようにする。
 */
@Composable
private fun VoicingPager(
    selectedIndex: Int,
    total: Int,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
) {
    val canSwitch = total > 1

    // タブレットなど広い画面で < > が端に離れすぎないよう、幅に上限を付けて中央に寄せる
    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .widthIn(max = 280.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            OutlinedButton(onClick = onPrevious, enabled = canSwitch) {
                Text("<")
            }
            Text(
                text = if (total == 0) "- / -" else "${selectedIndex + 1} / $total",
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
            )
            OutlinedButton(onClick = onNext, enabled = canSwitch) {
                Text(">")
            }
        }
    }
}

/**
 * 選択中(大きく表示中)以外の押さえ方を、縮小した指板図で2列に並べる。
 * 番号は押さえ方全体での位置(1始まり)で、切り替えても各押さえ方の番号は変わらない。
 */
@Composable
private fun OtherVoicings(
    voicings: List<ChordVoicing>,
    selectedIndex: Int,
    isLefty: Boolean,
) {
    val others = voicings.withIndex().filter { it.index != selectedIndex }
    if (others.isEmpty()) return

    Column(modifier = Modifier.fillMaxWidth().padding(top = 32.dp, bottom = 24.dp)) {
        Text(
            text = stringResource(Res.string.other_voicings),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        others.chunked(2).forEach { rowItems ->
            Row(modifier = Modifier.fillMaxWidth()) {
                rowItems.forEach { (index, voicing) ->
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = (index + 1).toString(),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(top = 16.dp),
                        )
                        ChordDiagram(
                            voicing = voicing,
                            isLefty = isLefty,
                            sizeScale = 0.6f,
                        )
                    }
                }
                // 奇数個の場合、最後の行の空きを埋めて幅を揃える
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.AlphabetButtons(
    uiState: ChordScreenViewModel.ChordScreenUiState,
    setAlphabet: (String) -> Unit,
    setSharp: (Sharp) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ChordOutlineButton(
            alphabet = "C",
            uiState = uiState,
        ) { setAlphabet(it) }
        ChordOutlineButton(
            alphabet = "D",
            uiState = uiState,
        ) { setAlphabet(it) }
        ChordOutlineButton(
            alphabet = "E",
            uiState = uiState,
        ) { setAlphabet(it) }
        ChordOutlineButton(
            alphabet = "F",
            uiState = uiState,
        ) { setAlphabet(it) }
    }
    Row(modifier = Modifier.fillMaxWidth()) {
        ChordOutlineButton(
            alphabet = "G",
            uiState = uiState,
        ) { setAlphabet(it) }
        ChordOutlineButton(
            alphabet = "A",
            uiState = uiState,
        ) { setAlphabet(it) }
        ChordOutlineButton(
            alphabet = "B",
            uiState = uiState,
        ) { setAlphabet(it) }
        SharpOutlineButton(
            uiState = uiState,
        ) { setSharp(it) }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ColumnScope.TypeButtons(
    uiState: ChordScreenViewModel.ChordScreenUiState,
    setType: (TYPE) -> Unit,
) {
    FlowRow(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        for (type in TYPE.entries) {
            TypeOutlineButton(
                type = type,
                uiState = uiState,
            ) { setType(type) }
        }
    }
}

@Composable
fun RowScope.ChordOutlineButton(
    alphabet: String,
    uiState: ChordScreenViewModel.ChordScreenUiState,
    setAlphabet: (String) -> Unit,
) {
    OutlinedButton(
        onClick = { setAlphabet(alphabet) },
        modifier = Modifier.weight(1f),
        colors =
            ButtonDefaults.outlinedButtonColors(
                containerColor =
                    if (uiState.alphabet == alphabet) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    },
                contentColor =
                    if (uiState.alphabet == alphabet) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.primary
                    },
            ),
    ) {
        Text(alphabet)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRowScope.TypeOutlineButton(
    type: TYPE,
    uiState: ChordScreenViewModel.ChordScreenUiState,
    setType: (TYPE) -> Unit,
) {
    OutlinedButton(
        onClick = { setType(type) },
        modifier = Modifier.defaultMinSize(minWidth = 80.dp),
        colors =
            ButtonDefaults.outlinedButtonColors(
                containerColor =
                    if (uiState.type == type) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    },
                contentColor =
                    if (uiState.type == type) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.primary
                    },
            ),
    ) {
        Text(maxLines = 1, text = type.displayName)
    }
}

@Composable
fun RowScope.SharpOutlineButton(
    uiState: ChordScreenViewModel.ChordScreenUiState,
    setSharp: (Sharp) -> Unit,
) {
    // #未対応の為、レイアウト維持しつつボタンを非表示にしています
    OutlinedButton(
        onClick = { setSharp(Sharp.SET) },
        modifier =
            Modifier
                .weight(1f)
                .alpha(0f),
        enabled = false,
        colors =
            ButtonDefaults.outlinedButtonColors(
                containerColor =
                    if (uiState.sharp == Sharp.SET) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onPrimary
                    },
                contentColor =
                    if (uiState.sharp == Sharp.SET) {
                        MaterialTheme.colorScheme.onPrimary
                    } else {
                        MaterialTheme.colorScheme.primary
                    },
            ),
    ) {
        Text("#")
    }
}

@Composable
@Preview
private fun ChordScreenPreview() {
    ChordScreen(modifier = Modifier.fillMaxSize())
}
