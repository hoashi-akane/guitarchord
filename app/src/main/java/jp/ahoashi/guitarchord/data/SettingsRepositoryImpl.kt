package jp.ahoashi.guitarchord.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import jp.ahoashi.guitarchord.core.SettingsRepository
import jp.ahoashi.guitarchord.core.SettingsRepository.Setting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SettingsRepositoryImpl(
    val context: Context,
) : SettingsRepository {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun getSettingStream(): Flow<Setting> =
        context.dataStore.data.map {
            Setting(
                lefty = it[booleanPreferencesKey(KEY_LEFTY_BOOLEAN)] == true,
            )
        }

    override suspend fun setLefty(lefty: Boolean) {
        context.dataStore.edit {
            it.set(booleanPreferencesKey("lefty"), lefty)
        }
    }

    companion object {
        const val KEY_LEFTY_BOOLEAN = "lefty"
    }
}
