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

object ChordList {
    object C {
        private val cMajor =
            Chord(
                alphabet = "C",
                sharp = false,
                type =
                    Chord.ChordType(
                        type = MAJOR,
                        fingerAlign =
                            Chord.Fingers(
                                index = FingerPosition(fret = 1, string = 2..2),
                                middle = FingerPosition(fret = 2, string = 4..4),
                                ling = FingerPosition(fret = 3, string = 5..5),
                                little = FingerPosition.EMPTY, // 使用しない
                            ),
                        openString = setOf(3, 6), // 開放弦
                    ),
            )
        private val cMinor =
            Chord(
                alphabet = "C",
                sharp = false,
                type =
                    Chord.ChordType(
                        type = MINOR,
                        fingerAlign =
                            Chord.Fingers(
                                index = FingerPosition(fret = 3, string = 1..5), // バレーコード
                                middle = FingerPosition(fret = 4, string = 2..2), // 使用しない
                                ling = FingerPosition(fret = 5, string = 4..4),
                                little = FingerPosition(fret = 5, string = 3..3),
                            ),
                        openString = setOf(), // 開放弦なし
                    ),
            )

        private val cM7 =
            Chord(
                alphabet = "C",
                sharp = false,
                type =
                    Chord.ChordType(
                        type = M7,
                        fingerAlign =
                            Chord.Fingers(
                                index = FingerPosition.EMPTY, // 使用しない
                                middle = FingerPosition(fret = 2, string = 4..4),
                                ling = FingerPosition(fret = 3, string = 5..5),
                                little = FingerPosition.EMPTY,
                            ),
                        openString = setOf(1, 2, 3), // 開放弦
                    ),
            )

        private val c7 =
            Chord(
                alphabet = "C",
                sharp = false,
                type =
                    Chord.ChordType(
                        type = SEVENTH,
                        fingerAlign =
                            Chord.Fingers(
                                index = FingerPosition(fret = 1, string = 2..2),
                                middle = FingerPosition(fret = 2, string = 4..4),
                                ling = FingerPosition(fret = 3, string = 5..5),
                                little = FingerPosition(fret = 3, string = 3..3),
                            ),
                        openString = setOf(1), // 開放弦
                    ),
            )

        private val cMinor7 =
            Chord(
                alphabet = "C",
                sharp = false,
                type =
                    Chord.ChordType(
                        type = MINOR7,
                        fingerAlign =
                            Chord.Fingers(
                                index = FingerPosition(fret = 3, string = 1..5), // バレーコード
                                middle = FingerPosition(fret = 4, string = 2..2), // 使用しない
                                ling = FingerPosition(fret = 5, string = 4..4),
                                little = FingerPosition.EMPTY, // 使用しない
                            ),
                        openString = setOf(), // 開放弦なし
                    ),
            )

        private val cmM7 =
            Chord(
                alphabet = "C",
                sharp = false,
                type =
                    Chord.ChordType(
                        type = MM7,
                        fingerAlign =
                            Chord.Fingers(
                                index = FingerPosition(fret = 1, string = 4..4),
                                middle = FingerPosition.EMPTY,
                                ling = FingerPosition(fret = 3, string = 5..5),
                                little = FingerPosition(fret = 3, string = 1..1),
                            ),
                        openString = setOf(2, 3), // 開放弦なし
                    ),
            )

        private val csus4 =
            Chord(
                alphabet = "C",
                sharp = false,
                type =
                    Chord.ChordType(
                        type = SUS4,
                        fingerAlign =
                            Chord.Fingers(
                                index = FingerPosition(fret = 3, string = 1..5), // バレーコード
                                middle = FingerPosition(fret = 5, string = 4..4),
                                ling = FingerPosition(fret = 5, string = 3..3),
                                little = FingerPosition(fret = 6, string = 2..2),
                            ),
                        openString = setOf(), // 開放弦
                    ),
            )

        private val c7Sus4 =
            Chord(
                alphabet = "C",
                sharp = false,
                type =
                    Chord.ChordType(
                        type = SEVEN_SUS4,
                        fingerAlign =
                            Chord.Fingers(
                                index = FingerPosition(fret = 3, string = 1..5), // バレーコード
                                middle = FingerPosition(fret = 5, string = 4..4),
                                ling = FingerPosition.EMPTY,
                                little = FingerPosition(fret = 6, string = 2..2),
                            ),
                        openString = setOf(3), // 開放弦
                    ),
            )

        private val cAdd9 =
            Chord(
                alphabet = "C",
                sharp = false,
                type =
                    Chord.ChordType(
                        type = ADD9,
                        fingerAlign =
                            Chord.Fingers(
                                index = FingerPosition(fret = 2, string = 4..4),
                                middle = FingerPosition(fret = 3, string = 5..5),
                                ling = FingerPosition(fret = 3, string = 2..2),
                                little = FingerPosition.EMPTY, // 使用しない,
                            ),
                        openString = setOf(1, 3), // 開放弦
                    ),
            )

        private val cMAdd9 =
            Chord(
                alphabet = "C",
                sharp = false,
                type =
                    Chord.ChordType(
                        type = MADD9,
                        fingerAlign =
                            Chord.Fingers(
                                index = FingerPosition(fret = 2, string = 4..4),
                                middle = FingerPosition(fret = 3, string = 5..5),
                                ling = FingerPosition(fret = 3, string = 2..2),
                                little = FingerPosition(fret = 3, string = 1..1),
                            ),
                        openString = setOf(1, 3), // 開放弦
                    ),
            )

        private val map =
            mapOf(
                MAJOR to cMajor,
                MINOR to cMinor,
                M7 to cM7,
                SEVENTH to c7,
                MINOR7 to cMinor7,
                MM7 to cmM7,
                SUS4 to csus4,
                SEVEN_SUS4 to c7Sus4,
                ADD9 to cAdd9,
                MADD9 to cMAdd9,
            )
    }
}
