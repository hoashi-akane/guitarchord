package jp.ahoashi.guitarchord.data

import jp.ahoashi.guitarchord.core.SettingsRepository
import jp.ahoashi.guitarchord.core.SettingsRepository.Setting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import platform.Foundation.NSUserDefaults

class SettingsRepositoryImpl : SettingsRepository {
    private val userDefaults = NSUserDefaults.standardUserDefaults
    private val _settings = MutableStateFlow(
        Setting(lefty = userDefaults.boolForKey("lefty"))
    )

    override fun getSettingStream(): Flow<Setting> = _settings

    override suspend fun setLefty(lefty: Boolean) {
        userDefaults.setBool(lefty, forKey = "lefty")
        _settings.update { it.copy(lefty = lefty) }
    }
}
