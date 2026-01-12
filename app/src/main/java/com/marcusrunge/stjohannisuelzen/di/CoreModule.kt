package com.marcusrunge.stjohannisuelzen.di

import com.marcusrunge.stjohannisuelzen.core.implementations.core.CoreFactoryImpl
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * A Dagger Hilt module for providing the [Core] dependency.
 *
 * This module is installed in the [SingletonComponent], which means that the provided [Core]
 * instance will have a singleton scope and will live as long as the application.
 */
@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    /**
     * Provides a singleton instance of the [Core] interface.
     *
     * This method uses the [CoreFactoryImpl] to create a concrete implementation of the [Core]
     * interface. The `@Singleton` annotation ensures that only one instance of the Core
     * component is created and shared across the application.
     *
     * @return A singleton instance of [Core].
     */
    @Provides
    @Singleton
    fun provideCore(): Core = CoreFactoryImpl.create()
}
