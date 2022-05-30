package com.ddevs.meditato.repository

import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.*
import javax.inject.Inject

class PrefRepository @Inject constructor(val dataStore: DataStore<Preferences>){
    private val STREAKCOUNTER = intPreferencesKey("streak")
    private val LASTDATE = stringPreferencesKey("date")
    val streakCounterFlow: Flow<Int> =
        dataStore.data.map {
        pref-> pref[STREAKCOUNTER]?:0
    }

    suspend fun checkStreak(prevDate: String,currDate: String){
        dataStore.edit { settings ->
            val lastDate = settings[LASTDATE] ?: ""
            if (lastDate != prevDate && lastDate!=currDate)
                settings[STREAKCOUNTER] = 0
        }
    }

    suspend fun incrementCounter(currentDate: String) {
        dataStore.edit { settings ->
            val currentCounterValue = settings[STREAKCOUNTER] ?: 0
            val lastDate = settings[LASTDATE]?:""
            if(lastDate!=currentDate) {
                settings[STREAKCOUNTER] = currentCounterValue + 1
                settings[LASTDATE] = currentDate
            }
        }
    }
}