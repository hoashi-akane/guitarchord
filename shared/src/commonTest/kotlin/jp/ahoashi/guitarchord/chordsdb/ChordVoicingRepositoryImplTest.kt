package jp.ahoashi.guitarchord.chordsdb

import jp.ahoashi.guitarchord.chordsdb.model.ChordVoicing
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

    private fun repository() =
        ChordVoicingRepositoryImpl(
            builtIn = BuiltInChordVoicingDataSourceImpl(),
            user = NoOpUserChordVoicingDataSource(),
        )

    /** 6弦→1弦の絶対フレットを "x35543" のような文字列にする(10フレット以上は括弧で囲む) */
    private fun ChordVoicing.tab(): String =
        strings
            .sortedByDescending { it.stringNumber }
            .joinToString("") { if (it.fret < 0) "x" else if (it.fret < 10) "${it.fret}" else "(${it.fret})" }

    @Test
    fun firstVoicingOfCMinorIsTheLegacyRootPosition() = runTest {
        val voicings = repository().findChordVoicingSet("C", sharp = false, type = TYPE.MINOR)!!.voicings

        // 旧実装のCマイナー(6弦ミュート・ルートポジション)が1つ目
        assertEquals("x35543", voicings.first().tab())
    }

    @Test
    fun nearDuplicateOfLegacyVoicingIsRemoved() = runTest {
        val tabs = repository().findChordVoicingSet("C", sharp = false, type = TYPE.MINOR)!!.voicings.map { it.tab() }

        // chords-db側の 335543(旧実装との違いは6弦を鳴らすかどうかだけ) は重複として除かれている
        assertTrue("335543" !in tabs, "重複するポジションが残っている: $tabs")
        assertEquals(tabs.size, tabs.toSet().size, "同じ押さえ方が重複している: $tabs")
    }

    @Test
    fun everyNaturalNoteAndTypeHasVoicings() = runTest {
        for (alphabet in listOf("A", "B", "C", "D", "E", "F", "G")) {
            for (type in TYPE.entries) {
                val set = repository().findChordVoicingSet(alphabet, sharp = false, type = type)
                assertNotNull(set, "$alphabet ${type.displayName} が見つからない")
                assertTrue(set.voicings.isNotEmpty(), "$alphabet ${type.displayName} の押さえ方が空")
                assertTrue(set.voicings.all { it.strings.size == 6 }, "$alphabet ${type.displayName} に6弦でないものがある")
            }
        }
    }

    @Test
    fun noOpUserDataSourceAlwaysReturnsEmpty() = runTest {
        val voicings = NoOpUserChordVoicingDataSource().findUserVoicings("C", "major")
        assertTrue(voicings.isEmpty())
    }
}
