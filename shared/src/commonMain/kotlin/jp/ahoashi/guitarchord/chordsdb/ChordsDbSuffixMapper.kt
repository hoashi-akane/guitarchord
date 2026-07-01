package jp.ahoashi.guitarchord.chordsdb

import jp.ahoashi.guitarchord.entity.TYPE

/**
 * 既存の TYPE enum (10種) -> chords-dbのsuffix文字列への固定表。
 * chords-dbは80種以上のsuffixを持つが、今回は既存TYPE enumの範囲に限定する。
 */
internal object ChordsDbSuffixMapper {
    private val typeToSuffix = mapOf(
        TYPE.MAJOR to "major",
        TYPE.MINOR to "minor",
        TYPE.M7 to "maj7",
        TYPE.SEVENTH to "7",
        TYPE.MINOR7 to "m7",
        TYPE.MM7 to "mmaj7",
        TYPE.SUS4 to "sus4",
        TYPE.SEVEN_SUS4 to "7sus4",
        TYPE.ADD9 to "add9",
        TYPE.MADD9 to "madd9",
    )

    fun toDbSuffix(type: TYPE): String? = typeToSuffix[type]
}
