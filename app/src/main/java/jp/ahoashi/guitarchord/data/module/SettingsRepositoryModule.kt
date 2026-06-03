package jp.ahoashi.guitarchord.data.module

import jp.ahoashi.guitarchord.ChordScreenViewModel
import jp.ahoashi.guitarchord.core.SettingsRepository
import jp.ahoashi.guitarchord.data.SettingsRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single<SettingsRepository> { SettingsRepositoryImpl(androidContext()) }
    viewModel { ChordScreenViewModel(get()) }
}
