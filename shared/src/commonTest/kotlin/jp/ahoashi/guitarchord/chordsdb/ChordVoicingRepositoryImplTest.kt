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
    fun firstVoicingOfEveryNaturalChordHasTheChordTones() = runTest {
        // 旧実装を1つ目にしているため、旧実装の入力ミス(構成音の欠け・余分)が混ざっていないことを保証する
        val roots = mapOf("C" to 0, "D" to 2, "E" to 4, "F" to 5, "G" to 7, "A" to 9, "B" to 11)
        // 根音からの半音数。required=コードの性格を決める音(欠けたら誤り)、all=許される音
        val tones =
            mapOf(
                TYPE.MAJOR to (setOf(0, 4) to setOf(0, 4, 7)),
                TYPE.MINOR to (setOf(0, 3) to setOf(0, 3, 7)),
                TYPE.M7 to (setOf(0, 4, 11) to setOf(0, 4, 7, 11)),
                TYPE.SEVENTH to (setOf(0, 4, 10) to setOf(0, 4, 7, 10)),
                TYPE.MINOR7 to (setOf(0, 3, 10) to setOf(0, 3, 7, 10)),
                TYPE.MM7 to (setOf(0, 3, 11) to setOf(0, 3, 7, 11)),
                TYPE.SUS4 to (setOf(0, 5) to setOf(0, 5, 7)),
                TYPE.SEVEN_SUS4 to (setOf(0, 5, 10) to setOf(0, 5, 7, 10)),
                TYPE.ADD9 to (setOf(0, 4, 2) to setOf(0, 4, 7, 2)),
                TYPE.MADD9 to (setOf(0, 3, 2) to setOf(0, 3, 7, 2)),
            )
        val openStrings = listOf(40, 45, 50, 55, 59, 64) // 6弦→1弦(E2 A2 D3 G3 B3 E4)

        for ((alphabet, root) in roots) {
            for (type in TYPE.entries) {
                val first = repository().findChordVoicingSet(alphabet, sharp = false, type = type)!!.voicings.first()
                val played =
                    first.strings
                        .sortedByDescending { it.stringNumber }
                        .withIndex()
                        .filter { it.value.fret >= 0 }
                        .map { (openStrings[it.index] + it.value.fret - root).mod(12) }
                        .toSet()
                val (required, allowed) = tones.getValue(type)
                val label = "$alphabet ${type.displayName} ${first.tab()}"
                assertTrue(played.containsAll(required), "$label: 構成音が欠けている(半音数 ${required - played})")
                assertTrue(allowed.containsAll(played), "$label: 構成音以外が鳴っている(半音数 ${played - allowed})")
            }
        }
    }

    @Test
    fun cMadd9DoesNotContainTheMistakenLegacyVoicing() = runTest {
        val tabs = repository().findChordVoicingSet("C", sharp = false, type = TYPE.MADD9)!!.voicings.map { it.tab() }

        // 旧実装の x32033 は E♭ がなく E が鳴る誤りだったため取り込んでいない
        assertTrue("x32033" !in tabs, "誤った押さえ方が残っている: $tabs")
        assertEquals("x31033", tabs.first())
    }

    @Test
    fun noOpUserDataSourceAlwaysReturnsEmpty() = runTest {
        val voicings = NoOpUserChordVoicingDataSource().findUserVoicings("C", "major")
        assertTrue(voicings.isEmpty())
    }
}
