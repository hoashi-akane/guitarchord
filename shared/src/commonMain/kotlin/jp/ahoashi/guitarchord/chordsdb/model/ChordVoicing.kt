package jp.ahoashi.guitarchord.chordsdb.model

data class ChordVoicingSet(
    val key: String,
    val suffix: String,
    val voicings: List<ChordVoicing>,
)

data class ChordVoicing(
    val baseFret: Int,
    val strings: List<StringFret>,
    val barres: List<Int>,
    val isCapoSuggested: Boolean,
    // ビルトイン(chords-db)か、ユーザーが追加したものか。UIで区別できるようにするため。
    val source: VoicingSource = VoicingSource.BUILTIN,
)

enum class VoicingSource {
    BUILTIN,
    USER,
}

data class StringFret(
    // 1(1弦/高音側)〜6(6弦/低音側)
    val stringNumber: Int,
    // 絶対フレット。ミュート=-1、開放=0
    val fret: Int,
    // 0=フィンガーなし、1〜4=人差し指〜小指
    val finger: Int,
)
