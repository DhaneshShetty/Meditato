package com.ddevs.meditato

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import java.util.concurrent.TimeUnit

@Composable
fun TimerScreen(viewModel: TimerViewModel,mainViewModel: MainViewModel,navController: NavController){
    val time=viewModel.milliseconds.observeAsState()
    val breathe = viewModel.breatheState.observeAsState("")
    BackHandler(true) {
        viewModel.cancelTimer()
        navController.navigateUp()
    }
    Column(
        Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.orange)), verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        BreatheText(breathe = breathe)
        TimerView(time,breathe)
    }
}

@Composable
fun BreatheText(breathe: State<String>){
    Text(breathe.value,style=TextStyle.Default.copy(color= Color.White, fontSize = 28.sp, fontStyle = FontStyle.Italic))
}

@Composable
fun TimerView(time: State<Long?>,breathe:State<String>){
    val sizeAnimation: Dp by animateDpAsState(
        when(breathe.value){
            ""->50.dp
            "Inhale"->300.dp
            else -> {50.dp}
        },
        tween(7000)
    )
    Canvas(modifier = Modifier.size(sizeAnimation), onDraw = {
        drawCircle(color = Color.White)
    })
    Text(text=(String.format("%02d:%02d", time.value?.let { TimeUnit.MILLISECONDS.toMinutes(it)},
        time.value?.let{TimeUnit.MILLISECONDS.toSeconds(it) -TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(it))})),style=TextStyle.Default.copy(fontSize = 32.sp))
}