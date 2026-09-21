package jp.ahoashi.guitarchord.chordsdb

/**
 * (alphabet, sharp) -> chords-dbの `chords` マップを引くためのキー名（ASCII安全表記）。
 * chords-dbは音により # 表記／b 表記が固定されているため、単純に alphabet+"#" では導けない。
 */
internal object ChordsDbKeyMapper {
    private val naturalKeys = mapOf(
        "C" to "C",
        "D" to "D",
        "E" to "E",
        "F" to "F",
        "G" to "G",
        "A" to "A",
        "B" to "B",
    )

    // sharp=true のとき、alphabet+"#" に対応する chords-db 側のASCII安全キー。
    // chords-dbにネイティブ収録されているシャープ系表記はC#, F#のみ("Csharp", "Fsharp")。
    // D#, G#, A# はフラット表記(Eb, Ab, Bb)でのみ収録されている。
    // E#, B# は自然音(F, C)と異名同音のため chords-db に該当キーが無い。
    private val sharpToDbKey = mapOf(
        "C" to "Csharp",
        "F" to "Fsharp",
        "D" to "Eb",
        "G" to "Ab",
        "A" to "Bb",
    )

    /** @return chords-dbの`chords`マップを引くためのキー名。対応が無い場合はnull。 */
    fun toDbChordsKey(alphabet: String, sharp: Boolean): String? =
        if (sharp) sharpToDbKey[alphabet] else naturalKeys[alphabet]
}
