package com.marcusrunge.stjohannisuelzen.di

import android.content.Context
import com.marcusrunge.stjohannisuelzen.dailymotto.implementations.DailyMottoFactoryImpl
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DailyMottoModule {
    @Provides
    fun provideApiConnect(@ApplicationContext context: Context?): DailyMotto {
        return DailyMottoFactoryImpl.create(context)
    }
}