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

@Module
@InstallIn(SingletonComponent::class)
object ApiConnectModule {
    @Provides
    @Singleton
    fun provideApiConnect(@ApplicationContext context: Context?): ApiConnect =
        ApiConnectFactoryImpl.create(context)
}