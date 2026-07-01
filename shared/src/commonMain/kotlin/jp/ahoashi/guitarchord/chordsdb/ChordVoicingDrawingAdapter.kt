package jp.ahoashi.guitarchord.chordsdb

import jp.ahoashi.guitarchord.chordsdb.model.ChordVoicing
import jp.ahoashi.guitarchord.entity.Chord

/**
 * 既存のCanvas描画ロジック(ChordScreen.kt)が前提とする Chord.Fingers 形式に変換する。
 * chords-dbはミュート(-1)/開放(0)/フレット済み(finger 1-4)を明示するため、
 * 既存の muteString のような推測ロジックは使わず、実データからそのまま組み立てる。
 */
fun ChordVoicing.toDrawableFingers(): Chord.Fingers {
    val frettedByFinger = strings
        .filter { it.fret > 0 }
        .groupBy { it.finger }

    fun position(fingerNumber: Int): Chord.FingerPosition {
        val group = frettedByFinger[fingerNumber] ?: return Chord.FingerPosition.EMPTY
        val fret = group.first().fret
        val stringNumbers = group.map { it.stringNumber }
        return Chord.FingerPosition(fret = fret, string = stringNumbers.min()..stringNumbers.max())
    }

    return Chord.Fingers(
        index = position(1),
        middle = position(2),
        ling = position(3),
        little = position(4),
    )
}

fun ChordVoicing.openStringNumbers(): Set<Int> =
    strings.filter { it.fret == 0 }.map { it.stringNumber }.toSet()

fun ChordVoicing.muteStringNumbers(): Set<Int> =
    strings.filter { it.fret < 0 }.map { it.stringNumber }.toSet()
