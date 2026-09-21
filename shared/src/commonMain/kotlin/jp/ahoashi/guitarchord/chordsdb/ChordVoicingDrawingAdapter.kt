package jp.ahoashi.guitarchord.chordsdb

import jp.ahoashi.guitarchord.chordsdb.model.ChordVoicing

/**
 * 指板図に描く「1本の指」。同じ指が複数弦を押さえる場合(バレー)は、その弦の範囲をまとめて持つ。
 */
data class DiagramFinger(
    // 1(人差し指)〜4(小指)
    val finger: Int,
    // 絶対フレット
    val fret: Int,
    // 押さえている弦(1=高音側〜6=低音側)。バレーなら複数弦にまたがる。
    val strings: IntRange,
)

/**
 * chords-dbは各弦のフレットと指をそのまま持つので、同じ指番号の弦をまとめて描画用の指に変換する。
 * バレーで他の指がより高いフレットを押さえている弦は、その指の弦として扱われ、
 * バレーの指は両端の弦の範囲(最小〜最大)にまたがって描かれる。
 */
fun ChordVoicing.diagramFingers(): List<DiagramFinger> =
    strings
        .filter { it.fret > 0 && it.finger in 1..4 }
        .groupBy { it.finger }
        .map { (finger, group) ->
            val stringNumbers = group.map { it.stringNumber }
            DiagramFinger(
                finger = finger,
                fret = group.first().fret,
                strings = stringNumbers.min()..stringNumbers.max(),
            )
        }
        .sortedBy { it.finger }

fun ChordVoicing.openStringNumbers(): Set<Int> =
    strings.filter { it.fret == 0 }.map { it.stringNumber }.toSet()

fun ChordVoicing.muteStringNumbers(): Set<Int> =
    strings.filter { it.fret < 0 }.map { it.stringNumber }.toSet()
