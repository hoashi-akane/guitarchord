package jp.ahoashi.guitarchord.entity

import jp.ahoashi.guitarchord.entity.Chord.FingerPosition

object G {
    val gMajor =
        Chord(
            alphabet = "G",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MAJOR,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition.EMPTY,
                            middle = FingerPosition(fret = 2, string = 5),
                            ling = FingerPosition(fret = 3, string = 6),
                            little = FingerPosition(fret = 3, string = 1),
                        ),
                    openString = setOf(2, 3, 4),
                ),
        )

    val gMinor =
        Chord(
            alphabet = "G",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MINOR,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 3, string = 1..6),
                            middle = FingerPosition.EMPTY,
                            ling = FingerPosition(fret = 5, string = 5),
                            little = FingerPosition(fret = 5, string = 4),
                        ),
                ),
        )

    val gM7 =
        Chord(
            alphabet = "G",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.M7,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 2, string = 1),
                            middle = FingerPosition(fret = 2, string = 5),
                            ling = FingerPosition(fret = 3, string = 6),
                            little = FingerPosition.EMPTY,
                        ),
                    openString = setOf(2, 3, 4),
                ),
        )

    val g7 =
        Chord(
            alphabet = "G",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.SEVENTH,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 1),
                            middle = FingerPosition(fret = 2, string = 5),
                            ling = FingerPosition(fret = 3, string = 6),
                            little = FingerPosition.EMPTY,
                        ),
                    openString = setOf(2, 3, 4),
                ),
        )

    val gMinor7 =
        Chord(
            alphabet = "G",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MINOR7,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 3, string = 1..6),
                            middle = FingerPosition(fret = 5, string = 5),
                            ling = FingerPosition.EMPTY,
                            little = FingerPosition.EMPTY,
                        ),
                ),
        )

    val gmM7 =
        Chord(
            alphabet = "Gm",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MM7,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 3, string = 1..6),
                            middle = FingerPosition(fret = 4, string = 4),
                            ling = FingerPosition(fret = 5, string = 5),
                            little = FingerPosition.EMPTY,
                        ),
                ),
        )

    val gsus4 =
        Chord(
            alphabet = "G",
            sharp = true,
            type =
                Chord.ChordType(
                    type = TYPE.SUS4,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 2),
                            middle = FingerPosition(fret = 3, string = 6),
                            ling = FingerPosition(fret = 3, string = 5),
                            little = FingerPosition(fret = 3, string = 1),
                        ),
                    openString = setOf(3, 4),
                ),
        )

    val g7sus4 =
        Chord(
            alphabet = "G7",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.SEVEN_SUS4,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 1..2),
                            middle = FingerPosition(fret = 3, string = 6),
                            ling = FingerPosition(fret = 3, string = 5),
                            little = FingerPosition.EMPTY,
                        ),
                    openString = setOf(3, 4),
                ),
        )

    val gAdd9 =
        Chord(
            alphabet = "G",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.ADD9,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 2, string = 3),
                            middle = FingerPosition(fret = 3, string = 6),
                            ling = FingerPosition(fret = 3, string = 1),
                            little = FingerPosition.EMPTY,
                        ),
                    openString = setOf(2, 4),
                ),
        )

    val gMAdd9 =
        Chord(
            alphabet = "Gm",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MADD9,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 3, string = 2..3),
                            middle = FingerPosition.EMPTY,
                            ling = FingerPosition(fret = 5, string = 4),
                            little = FingerPosition(fret = 5, string = 1),
                        ),
                ),
        )

    val map =
        mapOf(
            TYPE.MAJOR to gMajor,
            TYPE.MINOR to gMinor,
            TYPE.M7 to gM7,
            TYPE.SEVENTH to g7,
            TYPE.MINOR7 to gMinor7,
            TYPE.MM7 to gmM7,
            TYPE.SUS4 to gsus4,
            TYPE.SEVEN_SUS4 to g7sus4,
            TYPE.ADD9 to gAdd9,
            TYPE.MADD9 to gMAdd9,
        )
}
