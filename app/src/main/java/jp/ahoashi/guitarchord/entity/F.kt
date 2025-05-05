package jp.ahoashi.guitarchord.entity

import jp.ahoashi.guitarchord.entity.Chord.FingerPosition

object F {
    val fMajor =
        Chord(
            alphabet = "F",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MAJOR,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 1..6),
                            middle = FingerPosition(fret = 2, string = 3),
                            ling = FingerPosition(fret = 3, string = 5),
                            little = FingerPosition(fret = 3, string = 4),
                        ),
                ),
        )

    val fMinor =
        Chord(
            alphabet = "F",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MINOR,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 1..6),
                            middle = FingerPosition.EMPTY,
                            ling = FingerPosition(fret = 3, string = 5),
                            little = FingerPosition(fret = 3, string = 4),
                        ),
                ),
        )

    val fM7 =
        Chord(
            alphabet = "F",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.M7,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 1..6),
                            middle = FingerPosition(fret = 2, string = 4),
                            ling = FingerPosition(fret = 2, string = 3),
                            little = FingerPosition(fret = 3, string = 5),
                        ),
                ),
        )

    val f7 =
        Chord(
            alphabet = "F",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.SEVENTH,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 1..6),
                            middle = FingerPosition(fret = 2, string = 3),
                            ling = FingerPosition(fret = 3, string = 5),
                            little = FingerPosition.EMPTY,
                        ),
                ),
        )

    val fMinor7 =
        Chord(
            alphabet = "F",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MINOR7,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 1..6),
                            middle = FingerPosition(fret = 3, string = 5),
                            ling = FingerPosition.EMPTY,
                            little = FingerPosition.EMPTY,
                        ),
                ),
        )

    private val fmM7 =
        Chord(
            alphabet = "F",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MM7,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 1..6),
                            middle = FingerPosition(fret = 2, string = 4),
                            ling = FingerPosition(fret = 3, string = 5),
                            little = FingerPosition.EMPTY,
                        ),
                ),
        )

    val fSUS4 =
        Chord(
            alphabet = "F",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.SUS4,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 1..6),
                            middle = FingerPosition(fret = 3, string = 5),
                            ling = FingerPosition(fret = 3, string = 4),
                            little = FingerPosition(fret = 3, string = 3),
                        ),
                ),
        )

    val f7SUS4 =
        Chord(
            alphabet = "F",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.SEVEN_SUS4,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 1..6),
                            middle = FingerPosition.EMPTY,
                            ling = FingerPosition(fret = 3, string = 5),
                            little = FingerPosition(fret = 3, string = 3),
                        ),
                ),
        )

    val fAdd9 =
        Chord(
            alphabet = "F",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.ADD9,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 2),
                            middle = FingerPosition(fret = 2, string = 3),
                            ling = FingerPosition(fret = 3, string = 4),
                            little = FingerPosition(fret = 3, string = 1),
                        ),
                ),
        )

    val fMAdd9 =
        Chord(
            alphabet = "F",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MADD9,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 2..3),
                            middle = FingerPosition.EMPTY,
                            ling = FingerPosition(fret = 3, string = 4),
                            little = FingerPosition(fret = 3, string = 1),
                        ),
                ),
        )

    val map =
        mapOf(
            TYPE.MAJOR to fMajor,
            TYPE.MINOR to fMinor,
            TYPE.M7 to fM7,
            TYPE.SEVENTH to f7,
            TYPE.MINOR7 to fMinor7,
            TYPE.MM7 to fmM7,
            TYPE.SUS4 to fSUS4,
            TYPE.SEVEN_SUS4 to f7SUS4,
            TYPE.ADD9 to fAdd9,
            TYPE.MADD9 to fMAdd9,
        )
}
