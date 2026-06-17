package jp.ahoashi.guitarchord.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import jp.ahoashi.guitarchord.core.AppTheme
import jp.ahoashi.guitarchord.core.SettingsRepository
import jp.ahoashi.guitarchord.core.SettingsRepository.Setting
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class SettingsRepositoryImpl(
    val context: Context,
    val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : SettingsRepository {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun getSettingStream(): Flow<Setting> =
        context.dataStore.data.map {
            Setting(
                lefty = it[booleanPreferencesKey(KEY_LEFTY_BOOLEAN)] == true,
                theme = AppTheme.entries.find { t -> t.name == it[stringPreferencesKey(KEY_THEME)] }
                    ?: AppTheme.TEAL,
            )
        }

    override suspend fun setLefty(lefty: Boolean): Unit =
        withContext(dispatcher) {
            context.dataStore.edit {
                it[booleanPreferencesKey(KEY_LEFTY_BOOLEAN)] = lefty
            }
        }

    override suspend fun setTheme(theme: AppTheme): Unit =
        withContext(dispatcher) {
            context.dataStore.edit {
                it[stringPreferencesKey(KEY_THEME)] = theme.name
            }
        }

    companion object {
        const val KEY_LEFTY_BOOLEAN = "lefty"
        const val KEY_THEME = "theme"
    }
}
