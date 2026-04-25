package jp.ahoashi.guitarchord.entity

data class Chord(
    val alphabet: String,
    val sharp: Boolean,
    val type: ChordType,
) {
    data class ChordType(
        val type: TYPE,
        val fingerAlign: Fingers,
        val openString: Set<Int> = setOf(),
    ) {
        val muteString: Set<Int>
            get() {
                val playedStrings = openString.toMutableSet()
                listOf(fingerAlign.index, fingerAlign.middle, fingerAlign.ling, fingerAlign.little)
                    .filter { it != FingerPosition.EMPTY }
                    .forEach { playedStrings.addAll(it.string.toSet()) }
                return (1..6).toSet() - playedStrings
            }
    }

    data class Fingers(
        val index: FingerPosition,
        val middle: FingerPosition,
        val ling: FingerPosition,
        val little: FingerPosition,
    )

    data class FingerPosition(
        val fret: Int,
        val string: IntRange,
    ) {
        constructor(fret: Int, string: Int) : this(fret, string..string)

        companion object {
            val EMPTY = FingerPosition(fret = 0, string = 0..0)
        }
    }
}

// TODO: 未利用部分のコード追加
enum class TYPE(
    val displayName: String,
) {
    MAJOR("Major"),
    MINOR("minor"),
    M7("maj7"),
    SEVENTH("7"),
    MINOR7("m7"),
    MM7("mM7"),
    SUS4("sus4"),
    SEVEN_SUS4("7sus4"),
    ADD9("add9"),
    MADD9("madd9"),
//    SIX,
//    MINOR6,
//    AUG,
//    DIM,
//    DIM7,
//    MINOR7B5,
}
