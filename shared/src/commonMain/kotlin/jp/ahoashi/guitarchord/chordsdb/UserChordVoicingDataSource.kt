package jp.ahoashi.guitarchord.chordsdb

import jp.ahoashi.guitarchord.chordsdb.model.ChordVoicing

interface UserChordVoicingDataSource {
    suspend fun findUserVoicings(dbKey: String, suffix: String): List<ChordVoicing>
}

/**
 * ユーザー独自コード追加機能（設計書 項目3）はスコープ外のため、常に空リストを返すstub。
 * 永続化の実装に差し替えれば [ChordVoicingRepositoryImpl] 側は変更不要。
 */
class NoOpUserChordVoicingDataSource : UserChordVoicingDataSource {
    override suspend fun findUserVoicings(dbKey: String, suffix: String): List<ChordVoicing> = emptyList()
}
