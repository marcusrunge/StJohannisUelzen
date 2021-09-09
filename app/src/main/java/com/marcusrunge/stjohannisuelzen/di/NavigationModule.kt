package com.marcusrunge.stjohannisuelzen.di

import android.content.Context
import com.marcusrunge.stjohannisuelzen.implementations.NavigationImpl
import com.marcusrunge.stjohannisuelzen.interfaces.Navigation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
object NavigationModule {
    @Provides
    @ActivityScoped
    fun provideNavigation(@ActivityContext context: Context): Navigation =
        NavigationImpl.create(context)
}