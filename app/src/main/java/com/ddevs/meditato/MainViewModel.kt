package com.ddevs.meditato

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ddevs.meditato.repository.PrefRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: PrefRepository,
    ): ViewModel() {
        val streakFlow:Flow<Int> = repo.streakCounterFlow

    fun checkStreak(){
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        val mills=System.currentTimeMillis()
        val calendar: Calendar = Calendar.getInstance()
        calendar.setTimeInMillis(mills)
        val curr=formatter.format(calendar.getTime())
        calendar.setTimeInMillis(mills-86400000)
        val prev=formatter.format(calendar.getTime())
        viewModelScope.launch {
            repo.checkStreak(prev,curr)
        }
    }

    fun addToStreak(){
        viewModelScope.launch {
            val formatter = SimpleDateFormat("dd/MM/yyyy")
            val mills = System.currentTimeMillis()
            val calendar: Calendar = Calendar.getInstance()
            calendar.timeInMillis = mills
            val curr = formatter.format(calendar.time)
            repo.incrementCounter(curr)
            checkStreak()
        }
    }
}