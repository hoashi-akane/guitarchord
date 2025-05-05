package jp.ahoashi.guitarchord.entity

import jp.ahoashi.guitarchord.entity.Chord.FingerPosition

object A {
    val aMajor =
        Chord(
            alphabet = "A",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MAJOR,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 2, string = 4),
                            middle = FingerPosition(fret = 2, string = 3),
                            ling = FingerPosition(fret = 2, string = 2),
                            little = FingerPosition.EMPTY,
                        ),
                    openString = setOf(1, 5),
                ),
        )

    val aMinor =
        Chord(
            alphabet = "A",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MINOR,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 2),
                            middle = FingerPosition(fret = 2, string = 4),
                            ling = FingerPosition(fret = 2, string = 3),
                            little = FingerPosition.EMPTY,
                        ),
                    openString = setOf(1, 5),
                ),
        )

    val aM7 =
        Chord(
            alphabet = "A",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.M7,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 3),
                            middle = FingerPosition(fret = 2, string = 4),
                            ling = FingerPosition(fret = 2, string = 2),
                            little = FingerPosition.EMPTY,
                        ),
                    openString = setOf(1, 5),
                ),
        )

    val a7 =
        Chord(
            alphabet = "A",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.SEVENTH,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition.EMPTY,
                            middle = FingerPosition(fret = 2, string = 4),
                            ling = FingerPosition(fret = 2, string = 2),
                            little = FingerPosition.EMPTY,
                        ),
                    openString = setOf(1, 3, 5),
                ),
        )

    val aMinor7 =
        Chord(
            alphabet = "A",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MINOR7,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 2),
                            middle = FingerPosition(fret = 2, string = 4),
                            ling = FingerPosition.EMPTY,
                            little = FingerPosition.EMPTY,
                        ),
                    openString = setOf(1, 3, 5),
                ),
        )

    val amM7 =
        Chord(
            alphabet = "Am",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MM7,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 3),
                            middle = FingerPosition(fret = 1, string = 2),
                            ling = FingerPosition(fret = 2, string = 4),
                            little = FingerPosition.EMPTY,
                        ),
                    openString = setOf(1, 5),
                ),
        )

    val asus4 =
        Chord(
            alphabet = "A",
            sharp = true,
            type =
                Chord.ChordType(
                    type = TYPE.SUS4,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 2, string = 4),
                            middle = FingerPosition(fret = 2, string = 3),
                            ling = FingerPosition(fret = 3, string = 2),
                            little = FingerPosition.EMPTY,
                        ),
                    openString = setOf(1, 5),
                ),
        )

    val a7sus4 =
        Chord(
            alphabet = "A",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.SEVEN_SUS4,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 2, string = 4),
                            middle = FingerPosition.EMPTY,
                            ling = FingerPosition(fret = 3, string = 2),
                            little = FingerPosition.EMPTY,
                        ),
                    openString = setOf(1, 3, 5),
                ),
        )

    val aAdd9 =
        Chord(
            alphabet = "A",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.ADD9,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 2, string = 4),
                            middle = FingerPosition(fret = 2, string = 2),
                            ling = FingerPosition.EMPTY,
                            little = FingerPosition(fret = 4, string = 3),
                        ),
                    openString = setOf(1, 5),
                ),
        )

    val aMAdd9 =
        Chord(
            alphabet = "A",
            sharp = false,
            type =
                Chord.ChordType(
                    type = TYPE.MADD9,
                    fingerAlign =
                        Chord.Fingers(
                            index = FingerPosition(fret = 1, string = 2),
                            middle = FingerPosition(fret = 2, string = 4),
                            ling = FingerPosition.EMPTY,
                            little = FingerPosition(fret = 4, string = 3),
                        ),
                    openString = setOf(1, 5),
                ),
        )

    val map =
        mapOf(
            TYPE.MAJOR to aMajor,
            TYPE.MINOR to aMinor,
            TYPE.M7 to aM7,
            TYPE.SEVENTH to a7,
            TYPE.MINOR7 to aMinor7,
            TYPE.MM7 to amM7,
            TYPE.SUS4 to asus4,
            TYPE.SEVEN_SUS4 to a7sus4,
            TYPE.ADD9 to aAdd9,
            TYPE.MADD9 to aMAdd9,
        )
}
