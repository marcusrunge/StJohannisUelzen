package com.marcusrunge.stjohannisuelzen.di

import com.marcusrunge.stjohannisuelzen.webcontroller.implementation.WebControllerFactoryImpl
import com.marcusrunge.stjohannisuelzen.webcontroller.interfaces.WebController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object WebModule {
    @Provides
    @Singleton
    fun provideWebController(): WebController = WebControllerFactoryImpl.create()
}