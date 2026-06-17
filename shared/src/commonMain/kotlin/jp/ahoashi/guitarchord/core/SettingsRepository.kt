package jp.ahoashi.guitarchord.core

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    suspend fun setLefty(lefty: Boolean)
    suspend fun setTheme(theme: AppTheme)

    fun getSettingStream(): Flow<Setting>

    data class Setting(
        val lefty: Boolean = false,
        val theme: AppTheme = AppTheme.TEAL,
    )
}
