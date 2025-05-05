package jp.ahoashi.guitarchord.entity

object ChordList {
    fun findChord(
        alphabet: String,
        sharp: Boolean,
        type: TYPE?,
    ): Chord? =
        when (alphabet) {
            "C" -> {
                if (sharp) {
                    // TODO: C# GET
                    C.map[type]
                } else {
                    C.map[type]
                }
            }
            "D" -> {
                if (sharp) {
                    // TODO: D# GET
                    D.map[type]
                } else {
                    D.map[type]
                }
            }
            else -> {
                null
            }
        }
}
