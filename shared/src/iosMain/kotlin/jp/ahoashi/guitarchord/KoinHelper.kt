package jp.ahoashi.guitarchord

import jp.ahoashi.guitarchord.core.SettingsRepository
import jp.ahoashi.guitarchord.data.SettingsRepositoryImpl
import org.koin.core.context.startKoin
import org.koin.dsl.module

fun initKoin() {
    startKoin {
        modules(
            module {
                single<SettingsRepository> { SettingsRepositoryImpl() }
                factory { ChordScreenViewModel(get()) }
            }
        )
    }
}
