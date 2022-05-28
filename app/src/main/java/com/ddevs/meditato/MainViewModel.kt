package com.ddevs.meditato

import androidx.lifecycle.ViewModel
import com.ddevs.meditato.repository.PrefRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repo: PrefRepository
    ):ViewModel() {
        fun getStreak():Int{
            return 0;
        }

}