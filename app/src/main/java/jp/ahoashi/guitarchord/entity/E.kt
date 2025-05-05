package jp.ahoashi.guitarchord.entity

import jp.ahoashi.guitarchord.entity.Chord.FingerPosition

object E {
    val eMajor =
        Chord(
            alphabet = "E",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MAJOR,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 3..3),
                            middle = FingerPosition(fret = 2, string = 5..5),
                            ling = FingerPosition(fret = 2, string = 4..4),
                            little = FingerPosition.EMPTY, // 使用しない
                        ),
                    openString = setOf(1, 2, 6), // 開放弦
                ),
        )

    val eMinor =
        Chord(
            alphabet = "E",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MINOR,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition.EMPTY,
                            middle = FingerPosition(fret = 2, string = 5..5),
                            ling = FingerPosition(fret = 2, string = 4..4),
                            little = FingerPosition.EMPTY, // 使用しない
                        ),
                    openString = setOf(1, 2, 3, 6), // 開放弦
                ),
        )

    val eM7 =
        Chord(
            alphabet = "E",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.M7,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 4..4),
                            middle = FingerPosition(fret = 1, string = 3..3),
                            ling = FingerPosition(fret = 2, string = 5..5),
                            little = FingerPosition.EMPTY,
                        ),
                    openString = setOf(1, 2, 6), // 開放弦
                ),
        )

    val e7 =
        Chord(
            alphabet = "E",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.SEVENTH,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 3..3),
                            middle = FingerPosition(fret = 2, string = 5..5),
                            ling = FingerPosition.EMPTY,
                            little = FingerPosition.EMPTY,
                        ),
                    openString = setOf(1, 2, 4, 6), // 開放弦
                ),
        )

    val eMinor7 =
        Chord(
            alphabet = "E",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MINOR7,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition.EMPTY,
                            middle = FingerPosition(fret = 2, string = 5..5),
                            ling = FingerPosition.EMPTY,
                            little = FingerPosition.EMPTY,
                        ),
                    openString = setOf(1, 2, 3, 4, 6), // 開放弦
                ),
        )

    val emM7 =
        Chord(
            alphabet = "E",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MM7,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 4..4),
                            middle = FingerPosition(fret = 2, string = 5..5),
                            ling = FingerPosition.EMPTY,
                            little = FingerPosition.EMPTY,
                        ),
                    openString = setOf(1, 2, 3, 6), // 開放弦
                ),
        )

    val eSUS4 =
        Chord(
            alphabet = "E",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.SUS4,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition.EMPTY,
                            middle = FingerPosition(fret = 2, string = 5..5),
                            ling = FingerPosition(fret = 2, string = 4..4),
                            little = FingerPosition(fret = 2, string = 3..3),
                        ),
                    openString = setOf(1, 2, 6), // 開放弦
                ),
        )

    val e7SUS4 =
        Chord(
            alphabet = "E",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.SEVEN_SUS4,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition.EMPTY,
                            middle = FingerPosition(fret = 2, string = 5..5),
                            ling = FingerPosition(fret = 2, string = 3..3),
                            little = FingerPosition.EMPTY,
                        ),
                    openString = setOf(1, 2, 4, 6), // 開放弦
                ),
        )

    val eADD9 =
        Chord(
            alphabet = "E",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.ADD9,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 3),
                            middle = FingerPosition(fret = 2, string = 5),
                            ling = FingerPosition(fret = 2, string = 4),
                            little = FingerPosition(fret = 2, string = 1),
                        ),
                    openString = setOf(2, 6), // 開放弦
                ),
        )

    val eMAdd9 =
        Chord(
            alphabet = "E",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MADD9,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 2, string = 5),
                            middle = FingerPosition(fret = 2, string = 4),
                            ling = FingerPosition(fret = 2, string = 1),
                            little = FingerPosition.EMPTY,
                        ),
                    openString = setOf(2, 3, 6), // 開放弦
                ),
        )

    val map =
        mapOf(
            TYPE.MAJOR to eMajor,
            TYPE.MINOR to eMinor,
            TYPE.M7 to eM7,
            TYPE.SEVENTH to e7,
            TYPE.MINOR7 to eMinor7,
            TYPE.MM7 to emM7,
            TYPE.SUS4 to eSUS4,
            TYPE.SEVEN_SUS4 to e7SUS4,
            TYPE.ADD9 to eADD9,
            TYPE.MADD9 to eMAdd9,
        )
}
