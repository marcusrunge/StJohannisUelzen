package com.marcusrunge.stjohannisuelzen.di

import android.content.Context
import com.marcusrunge.stjohannisuelzen.dailymotto.implementations.DailyMottoFactoryImpl
import com.marcusrunge.stjohannisuelzen.dailymotto.interfaces.DailyMotto
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * A Dagger Hilt module for providing the [DailyMotto] dependency.
 *
 * This module is installed in the [SingletonComponent], meaning that the provided [DailyMotto]
 * instance will be a singleton and live as long as the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object DailyMottoModule {

    /**
     * Provides a singleton instance of [DailyMotto].
     *
     * This method uses the [DailyMottoFactoryImpl] to create the instance.
     * It requires the application [Context], which is provided by Hilt.
     *
     * @param context The application context, injected by Hilt.
     * @return A singleton [DailyMotto] instance.
     */
    @Provides
    @Singleton
    fun provideDailyMotto(@ApplicationContext context: Context): DailyMotto =
        DailyMottoFactoryImpl.create(context)
}
