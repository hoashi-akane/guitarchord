package jp.ahoashi.guitarchord.chordsdb

import jp.ahoashi.guitarchord.entity.TYPE
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ChordVoicingRepositoryImplTest {
    @Test
    fun findChordVoicingSetReturnsBuiltInCMajorVoicings() = runTest {
        val repository = ChordVoicingRepositoryImpl(
            builtIn = BuiltInChordVoicingDataSourceImpl(),
            user = NoOpUserChordVoicingDataSource(),
        )

        val result = repository.findChordVoicingSet(alphabet = "C", sharp = false, type = TYPE.MAJOR)

        assertNotNull(result)
        assertEquals(4, result.voicings.size)

        val openVoicing = result.voicings.first()
        assertEquals(1, openVoicing.baseFret)
        val byString = openVoicing.strings.associateBy { it.stringNumber }
        assertEquals(-1, byString.getValue(6).fret)
        assertEquals(3, byString.getValue(5).fret)
        assertEquals(2, byString.getValue(4).fret)
        assertEquals(0, byString.getValue(3).fret)
        assertEquals(1, byString.getValue(2).fret)
        assertEquals(0, byString.getValue(1).fret)
    }

    @Test
    fun noOpUserDataSourceAlwaysReturnsEmpty() = runTest {
        val voicings = NoOpUserChordVoicingDataSource().findUserVoicings("C", "major")
        assertTrue(voicings.isEmpty())
    }
}
