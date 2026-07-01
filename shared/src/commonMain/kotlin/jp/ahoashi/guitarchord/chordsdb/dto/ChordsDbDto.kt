package jp.ahoashi.guitarchord.chordsdb.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChordsDbRootDto(
    val main: MainDto,
    val tunings: TuningsDto,
    val keys: List<String>,
    val suffixes: List<String>,
    // キーは "C", "Csharp", "Eb" などASCII安全名。エントリ内の"key"フィールド(表示名)とは別物。
    val chords: Map<String, List<ChordEntryDto>>,
)

@Serializable
data class MainDto(
    val strings: Int,
    val fretsOnChord: Int,
    val name: String,
    val numberOfChords: Int,
)

@Serializable
data class TuningsDto(
    val standard: List<String>,
)

@Serializable
data class ChordEntryDto(
    val key: String,
    val suffix: String,
    val positions: List<PositionDto>,
)

@Serializable
data class PositionDto(
    // 6要素、6弦→1弦の順。-1=ミュート、0=開放、N>=1はbaseFretからの相対フレット。
    val frets: List<Int>,
    // 6要素。0=フィンガーなし、1〜4=人差し指〜小指。
    val fingers: List<Int>,
    val baseFret: Int,
    val barres: List<Int> = emptyList(),
    val capo: Boolean = false,
    val midi: List<Int> = emptyList(),
)
