package com.marcusrunge.stjohannisuelzen.di

import com.marcusrunge.stjohannisuelzen.apiconnect.implementations.ApiConnectFactoryImpl
import com.marcusrunge.stjohannisuelzen.apiconnect.interfaces.ApiConnect
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiConnectModule {
    @Provides
    @Singleton
    fun provideApiConnect(): ApiConnect = ApiConnectFactoryImpl.create()
}