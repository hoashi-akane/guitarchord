package jp.ahoashi.guitarchord.chordsdb

import jp.ahoashi.guitarchord.chordsdb.dto.PositionDto
import kotlin.test.Test
import kotlin.test.assertEquals

class ChordVoicingDrawingAdapterTest {
    // C Major のオープンコード: x32010
    private val openC =
        PositionDto(
            frets = listOf(-1, 3, 2, 0, 1, 0),
            fingers = listOf(0, 3, 2, 0, 1, 0),
            baseFret = 1,
        ).toChordVoicing()

    // C Major のバレー形: 6・5・1弦を人差し指で押さえ、4・3・2弦は他の指が5フレットを押さえる
    private val barreC =
        PositionDto(
            frets = listOf(1, 1, 3, 3, 3, 1),
            fingers = listOf(1, 1, 2, 3, 4, 1),
            baseFret = 3,
            barres = listOf(1),
            capo = true,
        ).toChordVoicing()

    @Test
    fun openVoicingHasOneFingerPerFrettedString() {
        val fingers = openC.diagramFingers()

        assertEquals(
            listOf(
                DiagramFinger(finger = 1, fret = 1, strings = 2..2),
                DiagramFinger(finger = 2, fret = 2, strings = 4..4),
                DiagramFinger(finger = 3, fret = 3, strings = 5..5),
            ),
            fingers,
        )
    }

    @Test
    fun openAndMuteStringsAreReadFromData() {
        assertEquals(setOf(1, 3), openC.openStringNumbers())
        assertEquals(setOf(6), openC.muteStringNumbers())
    }

    @Test
    fun barreFingerSpansFromLowestToHighestString() {
        val index = barreC.diagramFingers().first { it.finger == 1 }

        assertEquals(3, index.fret)
        assertEquals(1..6, index.strings)
    }

    @Test
    fun otherFingersKeepTheirOwnStringsOnTheBarre() {
        val fingers = barreC.diagramFingers().associateBy { it.finger }

        assertEquals(5, fingers.getValue(2).fret)
        assertEquals(4..4, fingers.getValue(2).strings)
        assertEquals(3..3, fingers.getValue(3).strings)
        assertEquals(2..2, fingers.getValue(4).strings)
    }
}
