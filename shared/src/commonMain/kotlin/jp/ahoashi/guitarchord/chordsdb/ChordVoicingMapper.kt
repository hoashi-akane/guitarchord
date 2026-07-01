package jp.ahoashi.guitarchord.chordsdb

import jp.ahoashi.guitarchord.chordsdb.dto.ChordEntryDto
import jp.ahoashi.guitarchord.chordsdb.dto.PositionDto
import jp.ahoashi.guitarchord.chordsdb.model.ChordVoicing
import jp.ahoashi.guitarchord.chordsdb.model.ChordVoicingSet
import jp.ahoashi.guitarchord.chordsdb.model.StringFret

internal fun ChordEntryDto.toChordVoicingSet(): ChordVoicingSet =
    ChordVoicingSet(
        key = key,
        suffix = suffix,
        voicings = positions.map { it.toChordVoicing() },
    )

internal fun PositionDto.toChordVoicing(): ChordVoicing {
    require(frets.size == 6) { "frets must have 6 elements, got ${frets.size}" }
    require(fingers.size == 6) { "fingers must have 6 elements, got ${fingers.size}" }
    val strings = frets.indices.map { i ->
        val stringNumber = 6 - i // frets[0]=6弦→stringNumber=6, frets[5]=1弦→stringNumber=1
        val rawFret = frets[i]
        val absoluteFret = if (rawFret >= 1) baseFret - 1 + rawFret else rawFret
        StringFret(stringNumber = stringNumber, fret = absoluteFret, finger = fingers[i])
    }
    return ChordVoicing(
        baseFret = baseFret,
        strings = strings,
        barres = barres,
        isCapoSuggested = capo,
    )
}
