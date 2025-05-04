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
            else -> {
                null
            }
        }
}
