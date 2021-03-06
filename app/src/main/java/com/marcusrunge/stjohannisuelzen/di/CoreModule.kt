package com.marcusrunge.stjohannisuelzen.di

import com.marcusrunge.stjohannisuelzen.core.implementations.core.CoreFactoryImpl
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    @Singleton
    fun provideCore(): Core = CoreFactoryImpl.create()
}