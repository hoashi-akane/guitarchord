package jp.ahoashi.guitarchord.data.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jp.ahoashi.guitarchord.core.SettingsRepository
import jp.ahoashi.guitarchord.data.SettingsRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SettingsRepositoryModule {
    @Provides
    @Singleton
    fun provideSettingsRepository(
        @ApplicationContext
        context: Context,
    ): SettingsRepository = SettingsRepositoryImpl(context)
}
