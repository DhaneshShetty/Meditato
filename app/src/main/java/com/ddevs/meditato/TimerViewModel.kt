package com.ddevs.meditato

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TimerViewModel @Inject constructor():ViewModel() {
    private var countDownTimer:CountDownTimer?=null
    enum class TimerState{
        INITIAL,RUNNING,FINISHED
    }
    var checkInterval:Int=7
    private var _timerState:MutableLiveData<TimerState> = MutableLiveData(TimerState.INITIAL)
    val timerState:LiveData<TimerState>
        get()=_timerState

    private var _milliseconds: MutableLiveData<Long> = MutableLiveData(0)
    val milliseconds: LiveData<Long>
        get()=_milliseconds

    private var _breatheState:MutableLiveData<String> = MutableLiveData("")
    val breatheState:LiveData<String>
        get()=_breatheState


    fun startTimer(){
        countDownTimer?.cancel()
        var count=0
        _breatheState.value="Inhale"
        countDownTimer=object:CountDownTimer(120000,1000){
            override fun onTick(p0: Long) {
                _milliseconds.value=p0
                if(count==checkInterval){
                    count=0
                    changeBreatheState()
                }
                count++
            }
            override fun onFinish() {
                _timerState.value=TimerState.FINISHED
            }
        }.start()
        _timerState.value=TimerState.RUNNING
    }

    fun changeBreatheState(){
        if(_breatheState.value=="Inhale") {
            _breatheState.value = "Exhale"
            checkInterval=5
        }
        else {
            _breatheState.value = "Inhale"
            checkInterval=7
        }
    }

    fun cancelTimer() {
        countDownTimer?.cancel()
    }
}