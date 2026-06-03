package jp.ahoashi.guitarchord.core

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setLefty(lefty: Boolean): Unit

    fun getSettingStream(): Flow<Setting>

    data class Setting(
        val lefty: Boolean = false,
    )
}
