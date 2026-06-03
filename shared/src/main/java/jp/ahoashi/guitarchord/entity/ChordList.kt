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
            "E" -> {
                if (sharp) {
                    // TODO: E# GET
                    E.map[type]
                } else {
                    E.map[type]
                }
            }
            "F" -> {
                if (sharp) {
                    // TODO: F# GET
                    F.map[type]
                } else {
                    F.map[type]
                }
            }
            "G" -> {
                if (sharp) {
                    // TODO: G# GET
                    G.map[type]
                } else {
                    G.map[type]
                }
            }
            "A" -> {
                if (sharp) {
                    // TODO: A# GET
                    A.map[type]
                } else {
                    A.map[type]
                }
            }
            "B" -> {
                if (sharp) {
                    // TODO: B# GET
                    B.map[type]
                } else {
                    B.map[type]
                }
            }
            else -> {
                null
            }
        }
}
