package com.marcusrunge.stjohannisuelzen.di

import com.marcusrunge.stjohannisuelzen.interfaces.MainActivityService
import com.marcusrunge.stjohannisuelzen.services.MainActivityServiceImpl
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MainActivityServiceModule {
    fun provideMainActivityService(): MainActivityService = MainActivityServiceImpl.create()
}