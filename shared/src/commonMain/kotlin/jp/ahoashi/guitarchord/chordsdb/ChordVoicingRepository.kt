package jp.ahoashi.guitarchord.chordsdb

import jp.ahoashi.guitarchord.chordsdb.model.ChordVoicingSet
import jp.ahoashi.guitarchord.entity.TYPE

interface ChordVoicingRepository {
    /** ビルトイン層とユーザー層をマージ済みの ChordVoicingSet を返す。見つからなければ null。 */
    suspend fun findChordVoicingSet(alphabet: String, sharp: Boolean, type: TYPE): ChordVoicingSet?
}

class ChordVoicingRepositoryImpl(
    private val builtIn: BuiltInChordVoicingDataSource,
    private val user: UserChordVoicingDataSource,
) : ChordVoicingRepository {
    override suspend fun findChordVoicingSet(alphabet: String, sharp: Boolean, type: TYPE): ChordVoicingSet? {
        val dbKey = ChordsDbKeyMapper.toDbChordsKey(alphabet, sharp) ?: return null
        val suffix = ChordsDbSuffixMapper.toDbSuffix(type) ?: return null
        val builtInSet = builtIn.findChordVoicingSet(dbKey, suffix)
        val userVoicings = user.findUserVoicings(dbKey, suffix)
        if (builtInSet == null && userVoicings.isEmpty()) return null

        // ユーザー層はビルトイン層に追記する（上書きではない）。
        return ChordVoicingSet(
            key = builtInSet?.key ?: dbKey,
            suffix = suffix,
            voicings = (builtInSet?.voicings ?: emptyList()) + userVoicings,
        )
    }
}
