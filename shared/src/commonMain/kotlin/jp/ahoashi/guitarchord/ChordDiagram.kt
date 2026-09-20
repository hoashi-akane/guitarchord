package jp.ahoashi.guitarchord

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import jp.ahoashi.guitarchord.chordsdb.DiagramFinger
import jp.ahoashi.guitarchord.chordsdb.diagramFingers
import jp.ahoashi.guitarchord.chordsdb.model.ChordVoicing
import jp.ahoashi.guitarchord.chordsdb.muteStringNumbers
import jp.ahoashi.guitarchord.chordsdb.openStringNumbers
import jp.ahoashi.guitarchord.generated.resources.Res
import jp.ahoashi.guitarchord.generated.resources.index_finger
import jp.ahoashi.guitarchord.generated.resources.little_finger
import jp.ahoashi.guitarchord.generated.resources.middle_finger
import jp.ahoashi.guitarchord.generated.resources.ring_finger
import org.jetbrains.compose.resources.stringResource

/**
 * コード図(指板)。1つの押さえ方(voicing)を描画する。
 *
 * @param voicing 描画する押さえ方。nullなら空の指板だけを描く。
 * @param sizeScale 全体の拡大率。1fが標準サイズで、複数の押さえ方を並べる際に小さく描くために使う。
 */
@Composable
fun ChordDiagram(
    voicing: ChordVoicing?,
    isLefty: Boolean,
    modifier: Modifier = Modifier,
    sizeScale: Float = 1f,
) {
    val fretTextMeasurer = rememberTextMeasurer(8)
    val fingerTextMeasurer = rememberTextMeasurer(6)
    val textStyle = TextStyle(fontSize = (14 * sizeScale).sp)

    val fingers = voicing?.diagramFingers() ?: emptyList()
    val max = fingers.maxOfOrNull { it.fret } ?: 0
    val startFret = if (max <= 4) 0 else max - 4

    val fingerNameList =
        listOf(
            stringResource(Res.string.index_finger),
            stringResource(Res.string.middle_finger),
            stringResource(Res.string.ring_finger),
            stringResource(Res.string.little_finger),
        )

    val textColor = MaterialTheme.colorScheme.onSurface
    val primaryColor = MaterialTheme.colorScheme.primary
    val outline = MaterialTheme.colorScheme.outlineVariant
    val background = MaterialTheme.colorScheme.background
    val firstLineColor = if (startFret == 0) primaryColor else outline

    Canvas(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(start = (10 * sizeScale).dp, end = (10 * sizeScale).dp, top = (40 * sizeScale).dp)
                .height((200 * sizeScale).dp),
    ) {
        // dp指定の寸法を、拡大率を掛けたpxに変換する
        fun px(value: Float) = value.dp.toPx() * sizeScale

        val offsetY = size.height / 5f
        val offsetX = size.width / 4f
        val openStrings = voicing?.openStringNumbers() ?: emptySet()
        val muteStrings = voicing?.muteStringNumbers() ?: emptySet()

        scale(scaleX = if (isLefty) -1f else 1f, scaleY = 1f) {
            // 開放弦の記号を表示
            openStrings.forEach {
                drawCircle(
                    color = primaryColor,
                    radius = px(8f),
                    style = Stroke(width = px(2f)),
                    center =
                        Offset(
                            x = -px(8f) + -px(4f),
                            y = offsetY * (it - 1).toFloat(),
                        ),
                )
            }

            // ミュート弦の記号を表示
            muteStrings.forEach {
                val centerX = -px(8f) + -px(4f)
                val centerY = offsetY * (it - 1).toFloat()
                val halfSize = px(8f)
                drawLine(
                    color = primaryColor,
                    start = Offset(centerX - halfSize, centerY - halfSize),
                    end = Offset(centerX + halfSize, centerY + halfSize),
                    strokeWidth = px(2f),
                )
                drawLine(
                    color = primaryColor,
                    start = Offset(centerX + halfSize, centerY - halfSize),
                    end = Offset(centerX - halfSize, centerY + halfSize),
                    strokeWidth = px(2f),
                )
            }

            // 開始の太線
            drawLine(
                color = firstLineColor,
                start = Offset(0f, 0f),
                end = Offset(0f, size.height),
                strokeWidth = px(2f),
            )
            // 基準の線を描画
            for (i in 0..5) {
                drawLine(
                    color = outline,
                    start = Offset(0f, offsetY * i),
                    end = Offset(x = size.width, offsetY * i),
                    strokeWidth = px(2f),
                )
            }

            // フレットの縦線と番号を描画
            for (i in 1..4) {
                drawLine(
                    color = outline,
                    start = Offset(x = offsetX * i, y = 0f),
                    end = Offset(x = offsetX * i, y = size.height),
                    strokeWidth = px(2f),
                )

                val textResult = fretTextMeasurer.measure((startFret + i).toString(), textStyle)
                val textCenter =
                    Offset(
                        x = offsetX * i - offsetX / 2,
                        y = -px(40f) + textResult.size.height / 2,
                    )
                scale(
                    scaleX = if (isLefty) -1f else 1f,
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
            drawFingers(
                textMeasurer = fingerTextMeasurer,
                textStyle = textStyle,
                firstFret = startFret,
                fingers = fingers,
                fingerNameList = fingerNameList,
                offsetX = offsetX,
                offsetY = offsetY,
                primary = primaryColor,
                background = background,
                isLefty = isLefty,
                pxScale = sizeScale,
            )
        }
    }
}

private fun DrawScope.drawFingers(
    textMeasurer: TextMeasurer,
    textStyle: TextStyle,
    firstFret: Int,
    fingers: List<DiagramFinger>,
    fingerNameList: List<String>,
    offsetX: Float,
    offsetY: Float,
    primary: Color,
    background: Color,
    isLefty: Boolean,
    pxScale: Float,
) {
    fingers.forEach { finger ->
        val x = (finger.fret - firstFret) * offsetX - (offsetX / 2)
        val y = (finger.strings.first - 1) * offsetY
        // 複数弦押し(バレー)の場合は、RoundRectを利用して描画する
        val radius = 16.dp.toPx() * pxScale
        val textResult = textMeasurer.measure(fingerNameList[finger.finger - 1], textStyle)

        val endY = (finger.strings.last - 1) * offsetY
        val circleSize = radius * 2
        val height =
            if (finger.strings.first == finger.strings.last) {
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
            style = Stroke(width = 2.dp.toPx() * pxScale),
            topLeft = Offset(x - radius, y - radius),
            size = Size(circleSize, height),
            cornerRadius = CornerRadius(radius, radius),
        )

        // テキストの中心位置を計算
        val textCenterY =
            if (finger.strings.first == finger.strings.last) {
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
