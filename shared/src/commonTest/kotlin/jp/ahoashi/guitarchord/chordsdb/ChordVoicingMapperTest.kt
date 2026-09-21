package jp.ahoashi.guitarchord.chordsdb

import jp.ahoashi.guitarchord.chordsdb.dto.PositionDto
import kotlin.test.Test
import kotlin.test.assertEquals

class ChordVoicingMapperTest {
    @Test
    fun openPositionMapsToAbsoluteFretsMatchingX32010() {
        val position = PositionDto(
            frets = listOf(-1, 3, 2, 0, 1, 0),
            fingers = listOf(0, 3, 2, 0, 1, 0),
            baseFret = 1,
        )

        val voicing = position.toChordVoicing()

        val byString = voicing.strings.associateBy { it.stringNumber }
        assertEquals(-1, byString.getValue(6).fret)
        assertEquals(3, byString.getValue(5).fret)
        assertEquals(2, byString.getValue(4).fret)
        assertEquals(0, byString.getValue(3).fret)
        assertEquals(1, byString.getValue(2).fret)
        assertEquals(0, byString.getValue(1).fret)
    }

    @Test
    fun barrePositionConvertsRelativeFretToAbsoluteUsingBaseFret() {
        val position = PositionDto(
            frets = listOf(1, 1, 3, 3, 3, 1),
            fingers = listOf(1, 1, 2, 3, 4, 1),
            baseFret = 3,
            barres = listOf(1),
            capo = true,
        )

        val voicing = position.toChordVoicing()

        val byString = voicing.strings.associateBy { it.stringNumber }
        assertEquals(3, byString.getValue(6).fret) // relative=1, baseFret=3 -> absolute=3
        assertEquals(5, byString.getValue(4).fret) // relative=3, baseFret=3 -> absolute=5
        assertEquals(3, voicing.baseFret)
        assertEquals(true, voicing.isCapoSuggested)
    }
}
