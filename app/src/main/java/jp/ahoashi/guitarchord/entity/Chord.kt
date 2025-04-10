package jp.ahoashi.guitarchord.entity

data class Chord(
    val alphabet: String,
    val sharp: Boolean,
    val type: ChordType,
) {
    data class ChordType(
        val type: TYPE,
        val fingerAlign: Fingers,
        val openString: Set<Int>,
    )

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
        companion object {
            val EMPTY = FingerPosition(fret = 0, string = 0..0)
        }
    }
}

enum class TYPE {
    MAJOR,
    MINOR,
    M7,
    SEVENTH,
    MINOR7,
    MM7,
    SUS4,
    SEVEN_SUS4,
    ADD9,
    MADD9,
    SIX,
    MINOR6,
    AUG,
    DIM,
    DIM7,
    MINOR7B5,
}
