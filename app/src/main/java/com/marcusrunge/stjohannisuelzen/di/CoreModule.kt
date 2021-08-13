package com.marcusrunge.stjohannisuelzen.di

import com.marcusrunge.stjohannisuelzen.core.implementation.core.CoreFactoryImpl
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {
    @Provides
    fun provideCore(): Core = CoreFactoryImpl.create()
}