package jp.ahoashi.guitarchord

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.FlowRowScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
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
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.ahoashi.guitarchord.ChordScreenViewModel.Companion.Sharp
import jp.ahoashi.guitarchord.core.SettingsRepository.Setting
import jp.ahoashi.guitarchord.entity.Chord
import jp.ahoashi.guitarchord.entity.TYPE
import jp.ahoashi.guitarchord.generated.resources.Res
import jp.ahoashi.guitarchord.generated.resources.index_finger
import jp.ahoashi.guitarchord.generated.resources.little_finger
import jp.ahoashi.guitarchord.generated.resources.middle_finger
import jp.ahoashi.guitarchord.generated.resources.ring_finger
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
    val text = rememberTextMeasurer(8)
    val fingerText = rememberTextMeasurer(6)
    val uiState by viewModel.uiState.collectAsState()
    val scrollableState = rememberScrollState()

    val setting by viewModel.getSettingStream().collectAsState()

    val fingerAlign = uiState.chord?.type?.fingerAlign
    val fingers =
        if (fingerAlign != null) {
            listOf(
                fingerAlign.index,
                fingerAlign.middle,
                fingerAlign.ling,
                fingerAlign.little,
            )
        } else {
            emptyList()
        }

    val max =
        fingers
            .filter { it != Chord.FingerPosition.EMPTY }
            .maxOfOrNull { it.fret } ?: 0
    val startFret =
        if (max <= 4) {
            0
        } else {
            max - 4
        }

    val fingerNameList =
        listOf(
            stringResource(Res.string.index_finger),
            stringResource(Res.string.middle_finger),
            stringResource(Res.string.ring_finger),
            stringResource(Res.string.little_finger),
        )

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

        Row {
            val textColor = MaterialTheme.colorScheme.onSurface
            val primaryColor = MaterialTheme.colorScheme.primary
            val outline = MaterialTheme.colorScheme.outlineVariant
            val background = MaterialTheme.colorScheme.background
            val firstLineColor = if (startFret == 0) primaryColor else outline
            Canvas(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp, top = 40.dp)
                        .height(200.dp),
            ) {
                val offsetY = size.height / 5f
                val offsetX = size.width / 4f
                // 開放弦の記号を表示 TODO: 位置調整が課題だが、○を描画するだけであればCanvasで描画しない方が楽
                val openStrings = uiState.chord?.type?.openString ?: emptySet()
                val muteStrings = uiState.chord?.type?.muteString ?: emptySet()

                scale(scaleX = if (setting.lefty) -1f else 1f, scaleY = 1f) {
                    openStrings.forEach {
                        drawCircle(
                            color = primaryColor,
                            radius = 8.dp.toPx(),
                            style = Stroke(width = 2.dp.toPx()),
                            center =
                                Offset(
                                    x = -8.dp.toPx() + -4.dp.toPx(),
                                    y = offsetY * (it - 1).toFloat(),
                                ),
                        )
                    }

                    muteStrings.forEach {
                        val centerX = -8.dp.toPx() + -4.dp.toPx()
                        val centerY = offsetY * (it - 1).toFloat()
                        val halfSize = 8.dp.toPx()
                        drawLine(
                            color = primaryColor,
                            start = Offset(centerX - halfSize, centerY - halfSize),
                            end = Offset(centerX + halfSize, centerY + halfSize),
                            strokeWidth = 2.dp.toPx(),
                        )
                        drawLine(
                            color = primaryColor,
                            start = Offset(centerX + halfSize, centerY - halfSize),
                            end = Offset(centerX - halfSize, centerY + halfSize),
                            strokeWidth = 2.dp.toPx(),
                        )
                    }

                    // 開始の太線
                    drawLine(
                        color = firstLineColor,
                        start = Offset(0f, 0f),
                        end = Offset(0f, size.height),
                        strokeWidth = 2.dp.toPx(),
                    )
                    // 基準の線を描画
                    for (i in 0..5) {
                        drawLine(
                            color = outline,
                            start = Offset(0f, offsetY * i),
                            end = Offset(x = size.width, offsetY * i),
                            strokeWidth = 2.dp.toPx(),
                        )
                    }

                    // フレットの縦線と番号を描画
                    for (i in 1..4) {
                        drawLine(
                            color = outline,
                            start = Offset(x = offsetX * i, y = 0f),
                            end = Offset(x = offsetX * i, y = size.height),
                            strokeWidth = 2.dp.toPx(),
                        )

                        val textResult = text.measure((startFret + i).toString())
                        val textCenter =
                            Offset(
                                x = offsetX * i - offsetX / 2,
                                y = -40.dp.toPx() + textResult.size.height / 2,
                            )
                        scale(
                            scaleX = if (setting.lefty) -1f else 1f,
                            scaleY = 1f,
                            pivot = textCenter,
                        ) {
                            drawText(
                                textLayoutResult = textResult,
                                color = textColor,
                                topLeft =
                                    Offset(
                                        x = textCenter.x - textResult.size.width / 2,
                                        y = textCenter.y - textResult.size.height / 2,
                                    ),
                            )
                        }
                    }

                    // 指の位置を描画
                    DrawFingers(
                        textMeasurer = fingerText,
                        firstFlet = startFret,
                        fingers = fingers,
                        fingerNameList = fingerNameList,
                        offsetX = offsetX,
                        offsetY = offsetY,
                        primary = primaryColor,
                        background = background,
                        isLefty = setting.lefty,
                    )
                }
            }
        }
        AlphabetButtons(uiState, { viewModel.setAlphabet(it) }) {
            viewModel.setSharp(it)
        }
        TypeButtons(uiState) { viewModel.setType(it) }
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
                .padding(top = 30.dp),
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

private fun DrawScope.DrawFingers(
    textMeasurer: TextMeasurer,
    firstFlet: Int,
    fingers: List<Chord.FingerPosition>,
    fingerNameList: List<String>,
    offsetX: Float,
    offsetY: Float,
    primary: Color,
    background: Color,
    isLefty: Boolean,
) {
    fingers.forEachIndexed { index, finger ->
        if (finger.fret == 0) {
            // フレットが0の場合は開放弦なので表示しない
            return@forEachIndexed
        }
        val x = (finger.fret - firstFlet) * offsetX - (offsetX / 2)
        val y = (finger.string.start - 1) * offsetY
        //  複数弦押しの場合は、RoundRectを利用して描画する
        val radius = 16.dp.toPx()
        val textResult = textMeasurer.measure(fingerNameList[index])

        // 単弦でも複数弦でも同じロジックで処理
        val endY = (finger.string.last - 1) * offsetY
        val circleSize = radius * 2
        val height =
            if (finger.string.start == finger.string.last) {
                circleSize
            } else {
                endY - y + circleSize
            }

        // Stroke単体だと線が重なるのでbgと同じ色を重ねて消す
        drawRoundRect(
            color = background,
            style = Fill,
            topLeft = Offset(x - radius, y - radius),
            size = Size(circleSize, height),
            cornerRadius = CornerRadius(radius, radius),
        )

        drawRoundRect(
            color = primary,
            style = Stroke(width = 2.dp.toPx()),
            topLeft = Offset(x - radius, y - radius),
            size = Size(circleSize, height),
            cornerRadius = CornerRadius(radius, radius),
        )

        // テキストの中心位置を計算
        val textCenterY =
            if (finger.string.start == finger.string.last) {
                y
            } else {
                (y + endY) / 2
            }

        val textCenter = Offset(x = x, y = textCenterY)

        // 文字の中心から左右反転して、文字だけ正しい位置に調整
        scale(scaleX = if (isLefty) -1f else 1f, scaleY = 1f, pivot = textCenter) {
            drawText(
                textLayoutResult = textResult,
                color = primary,
                topLeft =
                    Offset(
                        x = textCenter.x - textResult.size.width / 2,
                        y = textCenter.y - textResult.size.height / 2,
                    ),
            )
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
