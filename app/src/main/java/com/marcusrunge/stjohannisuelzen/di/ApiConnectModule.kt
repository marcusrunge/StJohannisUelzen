package com.marcusrunge.stjohannisuelzen.di

import android.content.Context
import com.marcusrunge.stjohannisuelzen.apiconnect.implementations.ApiConnectFactoryImpl
import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.ApiConnect
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * A Dagger Hilt module for providing the [ApiConnect] dependency.
 *
 * This module is installed in the [SingletonComponent], meaning that the provided [ApiConnect]
 * instance will be a singleton and live as long as the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiConnectModule {

    /**
     * Provides a singleton instance of [ApiConnect].
     *
     * This method uses the [ApiConnectFactoryImpl] to create the instance.
     * It requires the application [Context], which is provided by Hilt.
     *
     * @param context The application context, injected by Hilt.
     * @return A singleton [ApiConnect] instance.
     */
    @Provides
    @Singleton
    fun provideApiConnect(@ApplicationContext context: Context): ApiConnect =
        ApiConnectFactoryImpl.create(context)
}
