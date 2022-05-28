package com.ddevs.meditato.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.preferencesDataStore
import androidx.datastore.preferences.core.Preferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

val Context.userDataStore:DataStore<Preferences> by preferencesDataStore(name="com.ddevs.meditato.user_preferences")

@InstallIn(SingletonComponent::class)
@Module
object DatastoreModule{
    @Provides
    @Singleton
    fun providesDatastore(@ApplicationContext applicationContext: Context):DataStore<Preferences>{
        return applicationContext.userDataStore
    }
}