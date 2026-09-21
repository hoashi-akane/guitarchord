package jp.ahoashi.guitarchord.chordsdb

import jp.ahoashi.guitarchord.chordsdb.dto.ChordsDbRootDto
import jp.ahoashi.guitarchord.chordsdb.model.ChordVoicingSet
import jp.ahoashi.guitarchord.generated.resources.Res
import kotlinx.serialization.json.Json

interface BuiltInChordVoicingDataSource {
    suspend fun findChordVoicingSet(dbKey: String, suffix: String): ChordVoicingSet?
}

class BuiltInChordVoicingDataSourceImpl : BuiltInChordVoicingDataSource {
    private val json = Json { ignoreUnknownKeys = true }
    private var cache: Map<Pair<String, String>, ChordVoicingSet>? = null

    private suspend fun loadCache(): Map<Pair<String, String>, ChordVoicingSet> =
        cache ?: run {
            val bytes = Res.readBytes("files/chords_db_guitar.json")
            val root = json.decodeFromString<ChordsDbRootDto>(bytes.decodeToString())
            root.chords.flatMap { (dbKey, entries) ->
                entries.map { entry -> (dbKey to entry.suffix) to entry.toChordVoicingSet() }
            }.toMap().also { cache = it }
        }

    override suspend fun findChordVoicingSet(dbKey: String, suffix: String): ChordVoicingSet? =
        loadCache()[dbKey to suffix]
}
