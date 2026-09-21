package jp.ahoashi.guitarchord.chordsdb

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class ChordsDbKeyMapperTest {
    @Test
    fun naturalKeyReturnsSameLetter() {
        assertEquals("C", ChordsDbKeyMapper.toDbChordsKey("C", sharp = false))
    }

    @Test
    fun sharpKeyUsesSharpSpellingWhenAvailable() {
        assertEquals("Csharp", ChordsDbKeyMapper.toDbChordsKey("C", sharp = true))
    }

    @Test
    fun sharpKeyUsesEnharmonicFlatSpellingWhenNoSharpEntry() {
        assertEquals("Eb", ChordsDbKeyMapper.toDbChordsKey("D", sharp = true))
    }

    @Test
    fun sharpKeyReturnsNullWhenNotRepresentedInChordsDb() {
        assertNull(ChordsDbKeyMapper.toDbChordsKey("E", sharp = true))
    }
}
