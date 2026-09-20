package jp.ahoashi.guitarchord.chordsdb.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.ahoashi.guitarchord.chordsdb.model.ChordVoicingSet
import jp.ahoashi.guitarchord.chordsdb.muteStringNumbers
import jp.ahoashi.guitarchord.chordsdb.openStringNumbers
import jp.ahoashi.guitarchord.chordsdb.toDrawableFingers
import jp.ahoashi.guitarchord.entity.Chord
import jp.ahoashi.guitarchord.entity.TYPE
import jp.ahoashi.guitarchord.generated.resources.Res
import jp.ahoashi.guitarchord.generated.resources.chords_db_debug
import jp.ahoashi.guitarchord.generated.resources.index_finger
import jp.ahoashi.guitarchord.generated.resources.little_finger
import jp.ahoashi.guitarchord.generated.resources.middle_finger
import jp.ahoashi.guitarchord.generated.resources.ring_finger
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

/**
 * chords-db連携の確認用画面（デバッグ用）。
 * 見た目はChordScreenと同じ指板描画を流用しつつ、1コードにつき複数存在する
 * 押さえ方(voicing)をページ送りで切り替えられるようにしている。
 */
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ChordsDbVoicingsScreen(
    onBack: () -> Unit,
    viewModel: ChordsDbVoicingsViewModel = koinViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val setting by viewModel.settingState.collectAsState()
    val text = rememberTextMeasurer(8)
    val fingerText = rememberTextMeasurer(6)

    val voicing = uiState.currentVoicing
    val fingerAlign = voicing?.toDrawableFingers()
    val fingers =
        if (fingerAlign != null) {
            listOf(fingerAlign.index, fingerAlign.middle, fingerAlign.ling, fingerAlign.little)
        } else {
            emptyList()
        }

    val max =
        fingers
            .filter { it != Chord.FingerPosition.EMPTY }
            .maxOfOrNull { it.fret } ?: 0
    val startFret = if (max <= 4) 0 else max - 4

    val fingerNameList =
        listOf(
            stringResource(Res.string.index_finger),
            stringResource(Res.string.middle_finger),
            stringResource(Res.string.ring_finger),
            stringResource(Res.string.little_finger),
        )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(Res.string.chords_db_debug)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null)
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
        ) {
            Row(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
            ) {
                Text(
                    text = uiState.alphabet,
                    fontSize = 42.sp,
                    color = MaterialTheme.colorScheme.primary,
                    lineHeight = 42.sp,
                )
                Text(
                    text = uiState.type.displayName,
                    fontSize = 24.sp,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(start = 4.dp, bottom = 4.dp),
                    lineHeight = 24.sp,
                )
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
                    val openStrings = voicing?.openStringNumbers() ?: emptySet()
                    val muteStrings = voicing?.muteStringNumbers() ?: emptySet()

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

                        drawLine(
                            color = firstLineColor,
                            start = Offset(0f, 0f),
                            end = Offset(0f, size.height),
                            strokeWidth = 2.dp.toPx(),
                        )
                        for (i in 0..5) {
                            drawLine(
                                color = outline,
                                start = Offset(0f, offsetY * i),
                                end = Offset(x = size.width, offsetY * i),
                                strokeWidth = 2.dp.toPx(),
                            )
                        }

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

            VoicingPager(
                voicingSet = uiState.voicingSet,
                voicingIndex = uiState.voicingIndex,
                onPrevious = { viewModel.previousVoicing() },
                onNext = { viewModel.nextVoicing() },
            )

            AlphabetButtons(uiState) { viewModel.setAlphabet(it) }
            TypeButtons(uiState) { viewModel.setType(it) }
        }
    }
}

@Composable
private fun VoicingPager(
    voicingSet: ChordVoicingSet?,
    voicingIndex: Int,
    onPrevious: () -> Unit,
    onNext: () -> Unit,
) {
    val voicing = voicingSet?.voicings?.getOrNull(voicingIndex)
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
            OutlinedButton(onClick = onPrevious, enabled = voicingSet != null) {
                Text("<")
            }
            // ラベル長が変わってもボタン位置がずれないよう、ボタン間の領域を占有して中央に表示する
            Text(
                text =
                    voicing?.let {
                        "${voicingIndex + 1} / ${voicingSet.voicings.size}"
                    } ?: "該当なし",
                modifier =
                    Modifier
                        .weight(1f)
                        .padding(horizontal = 12.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
            )
            OutlinedButton(onClick = onNext, enabled = voicingSet != null) {
                Text(">")
            }
        }
    }
}

@Composable
private fun AlphabetButtons(
    uiState: ChordsDbVoicingsViewModel.UiState,
    setAlphabet: (String) -> Unit,
) {
    FlowRow(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        for (alphabet in listOf("C", "D", "E", "F", "G", "A", "B")) {
            OutlinedButton(
                onClick = { setAlphabet(alphabet) },
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
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun TypeButtons(
    uiState: ChordsDbVoicingsViewModel.UiState,
    setType: (TYPE) -> Unit,
) {
    FlowRow(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        for (type in TYPE.entries) {
            OutlinedButton(
                onClick = { setType(type) },
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
            return@forEachIndexed
        }
        val x = (finger.fret - firstFlet) * offsetX - (offsetX / 2)
        val y = (finger.string.first - 1) * offsetY
        val radius = 16.dp.toPx()
        val textResult = textMeasurer.measure(fingerNameList[index])

        val endY = (finger.string.last - 1) * offsetY
        val circleSize = radius * 2
        val height =
            if (finger.string.first == finger.string.last) {
                circleSize
            } else {
                endY - y + circleSize
            }

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

        val textCenterY =
            if (finger.string.first == finger.string.last) {
                y
            } else {
                (y + endY) / 2
            }

        val textCenter = Offset(x = x, y = textCenterY)

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
