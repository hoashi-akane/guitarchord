package jp.ahoashi.guitarchord.chordsdb

import org.koin.dsl.module

val chordsDbModule = module {
    single<BuiltInChordVoicingDataSource> { BuiltInChordVoicingDataSourceImpl() }
    single<UserChordVoicingDataSource> { NoOpUserChordVoicingDataSource() }
    single<ChordVoicingRepository> { ChordVoicingRepositoryImpl(get(), get()) }
}
