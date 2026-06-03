package jp.ahoashi.guitarchord.entity

import jp.ahoashi.guitarchord.entity.Chord.FingerPosition
import jp.ahoashi.guitarchord.entity.TYPE.ADD9
import jp.ahoashi.guitarchord.entity.TYPE.M7
import jp.ahoashi.guitarchord.entity.TYPE.MADD9
import jp.ahoashi.guitarchord.entity.TYPE.MAJOR
import jp.ahoashi.guitarchord.entity.TYPE.MINOR
import jp.ahoashi.guitarchord.entity.TYPE.MINOR7
import jp.ahoashi.guitarchord.entity.TYPE.MM7
import jp.ahoashi.guitarchord.entity.TYPE.SEVENTH
import jp.ahoashi.guitarchord.entity.TYPE.SEVEN_SUS4
import jp.ahoashi.guitarchord.entity.TYPE.SUS4

object D {
    val dMajor =
        Chord(
            alphabet = "D",
            sharp = false,
            type =
                Chord.ChordType(
                    type = MAJOR,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 2, string = 3..3),
                            middle = FingerPosition(fret = 2, string = 1..1),
                            ling = FingerPosition(fret = 3, string = 2..2),
                            little = FingerPosition.EMPTY, // 使用しない
                        ),
                    openString = setOf(4), // 開放弦
                ),
        )

    val dMinor =
        Chord(
            alphabet = "D",
            sharp = false,
            type =
                Chord.ChordType(
                    type = MINOR,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 1..1),
                            middle = FingerPosition(fret = 2, string = 3..3),
                            ling = FingerPosition(fret = 3, string = 2..2),
                            little = FingerPosition.EMPTY, // 使用しない
                        ),
                    openString = setOf(4), // 開放弦
                ),
        )

    val dM7 =
        Chord(
            alphabet = "D",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.M7,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 2, string = 1..3), // 1弦から3弦を人差し指で押さえる
                            middle = FingerPosition.EMPTY, // 使用しない
                            ling = FingerPosition.EMPTY, // 使用しない
                            little = FingerPosition.EMPTY, // 使用しない
                        ),
                    openString = setOf(4), // 開放弦
                ),
        )

    val d7 =
        Chord(
            alphabet = "D",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.SEVENTH,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 2..2),
                            middle = FingerPosition(fret = 2, string = 3..3),
                            ling = FingerPosition(fret = 2, string = 1..1),
                            little = FingerPosition.EMPTY, // 使用しない
                        ),
                    openString = setOf(4), // 開放弦
                ),
        )

    val dMinor7 =
        Chord(
            alphabet = "D",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MINOR7,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 2..2),
                            middle = FingerPosition(fret = 1, string = 1..1),
                            ling = FingerPosition(fret = 2, string = 3..3),
                            little = FingerPosition.EMPTY, // 使用しない
                        ),
                    openString = setOf(4), // 開放弦
                ),
        )

    val dmM7 =
        Chord(
            alphabet = "D",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MM7,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 1..1),
                            middle = FingerPosition(fret = 2, string = 3..3),
                            ling = FingerPosition(fret = 2, string = 2..2),
                            little = FingerPosition.EMPTY, // 使用しない
                        ),
                    openString = setOf(4), // 開放弦
                ),
        )

    val dsus4 =
        Chord(
            alphabet = "D",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.SUS4,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 2, string = 3..3),
                            middle = FingerPosition.EMPTY, // 中指は使用しない
                            ling = FingerPosition(fret = 3, string = 2..2),
                            little = FingerPosition(fret = 3, string = 1..1),
                        ),
                    openString = setOf(4), // 開放弦
                ),
        )

    val d7Sus4 =
        Chord(
            alphabet = "D",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.SEVEN_SUS4,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 2..2), // 人差し指で2弦1フレット
                            middle = FingerPosition(fret = 2, string = 3..3),
                            ling = FingerPosition.EMPTY,
                            little = FingerPosition(fret = 3, string = 1..1),
                        ),
                    openString = setOf(4), // 開放弦
                ),
        )

    val dAdd9 =
        Chord(
            alphabet = "D",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.ADD9,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 2, string = 3..3), // 人差し指で3弦2フレット
                            middle = FingerPosition(fret = 3, string = 2..2), // 中指で1弦2フレット
                            ling = FingerPosition(fret = 4, string = 4..4), // 薬指で2弦3フレット
                            little = FingerPosition(fret = 5, string = 5..5), // 小指は使用しない
                        ),
                    openString = setOf(1), // 開放弦
                ),
        )

    val dMAdd9 =
        Chord(
            alphabet = "D",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MADD9,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 3, string = 4..4),
                            middle = FingerPosition(fret = 5, string = 5..5),
                            ling = FingerPosition(fret = 5, string = 2..2),
                            little = FingerPosition(fret = 5, string = 1..1),
                        ),
                    openString = setOf(), // 開放弦
                ),
        )

    val map =
        mapOf(
            MAJOR to dMajor,
            MINOR to dMinor,
            M7 to dM7,
            SEVENTH to d7,
            MINOR7 to dMinor7,
            MM7 to dmM7,
            SUS4 to dsus4,
            SEVEN_SUS4 to d7Sus4,
            ADD9 to dAdd9,
            MADD9 to dMAdd9,
        )
}
