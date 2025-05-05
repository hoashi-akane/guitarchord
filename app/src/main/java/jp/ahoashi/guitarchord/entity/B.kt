package jp.ahoashi.guitarchord.entity

import jp.ahoashi.guitarchord.entity.Chord.FingerPosition

object B {
    val bMajor =
        Chord(
            alphabet = "B",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MAJOR,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 2, string = 1..5),
                            middle = FingerPosition(fret = 4, string = 4),
                            ling = FingerPosition(fret = 4, string = 3),
                            little = FingerPosition(fret = 4, string = 2),
                        ),
                ),
        )

    val bMinor =
        Chord(
            alphabet = "B",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MINOR,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 2, string = 1..5),
                            middle = FingerPosition(fret = 3, string = 2),
                            ling = FingerPosition(fret = 4, string = 4),
                            little = FingerPosition(fret = 4, string = 3),
                        ),
                ),
        )

    val bM7 =
        Chord(
            alphabet = "B",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.M7,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 2, string = 1..5),
                            middle = FingerPosition(fret = 3, string = 3),
                            ling = FingerPosition(fret = 4, string = 4),
                            little = FingerPosition(fret = 4, string = 2),
                        ),
                ),
        )

    val b7 =
        Chord(
            alphabet = "B",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.SEVENTH,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 4),
                            middle = FingerPosition(fret = 2, string = 5),
                            ling = FingerPosition(fret = 2, string = 3),
                            little = FingerPosition(fret = 2, string = 1),
                        ),
                    openString = setOf(2),
                ),
        )

    val bMinor7 =
        Chord(
            alphabet = "B",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MINOR7,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 2, string = 1..5),
                            middle = FingerPosition(fret = 3, string = 2),
                            ling = FingerPosition(fret = 4, string = 4),
                            little = FingerPosition.EMPTY,
                        ),
                ),
        )

    val bmM7 =
        Chord(
            alphabet = "B",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MM7,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 2, string = 1..5),
                            middle = FingerPosition(fret = 3, string = 3),
                            ling = FingerPosition(fret = 3, string = 2),
                            little = FingerPosition(fret = 4, string = 4),
                        ),
                ),
        )

    val bsus4 =
        Chord(
            alphabet = "B",
            sharp = true,
            type =
                Chord.ChordType(
                    type = TYPE.SUS4,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 2, string = 1..5),
                            middle = FingerPosition(fret = 4, string = 4),
                            ling = FingerPosition(fret = 4, string = 3),
                            little = FingerPosition(fret = 5, string = 2),
                        ),
                ),
        )

    val b7sus4 =
        Chord(
            alphabet = "B",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.SEVEN_SUS4,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 2, string = 1..5),
                            middle = FingerPosition(fret = 4, string = 4),
                            ling = FingerPosition.EMPTY,
                            little = FingerPosition(fret = 5, string = 2),
                        ),
                ),
        )

    val bAdd9 =
        Chord(
            alphabet = "B",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.ADD9,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 7, string = 2),
                            middle = FingerPosition(fret = 8, string = 3),
                            ling = FingerPosition(fret = 9, string = 4),
                            little = FingerPosition(fret = 9, string = 1),
                        ),
                ),
        )

    val bMAdd9 =
        Chord(
            alphabet = "B",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MADD9,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 7, string = 2..3),
                            middle = FingerPosition.EMPTY,
                            ling = FingerPosition(fret = 9, string = 4),
                            little = FingerPosition(fret = 9, string = 1),
                        ),
                ),
        )

    val map =
        mapOf(
            TYPE.MAJOR to bMajor,
            TYPE.MINOR to bMinor,
            TYPE.M7 to bM7,
            TYPE.SEVENTH to b7,
            TYPE.MINOR7 to bMinor7,
            TYPE.MM7 to bmM7,
            TYPE.SUS4 to bsus4,
            TYPE.SEVEN_SUS4 to b7sus4,
            TYPE.ADD9 to bAdd9,
            TYPE.MADD9 to bMAdd9,
        )
}
