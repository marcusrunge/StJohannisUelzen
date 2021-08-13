package com.marcusrunge.stjohannisuelzen.di

import com.marcusrunge.stjohannisuelzen.core.implementation.CoreFactoryImpl
import com.marcusrunge.stjohannisuelzen.core.interfaces.Core
import com.marcusrunge.stjohannisuelzen.webcontroller.implementation.WebControllerFactoryImpl
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.WebController
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