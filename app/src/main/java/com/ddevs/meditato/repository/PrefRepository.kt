package com.ddevs.meditato.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import javax.inject.Inject

class PrefRepository @Inject constructor(val dataStore: DataStore<Preferences>) {
}