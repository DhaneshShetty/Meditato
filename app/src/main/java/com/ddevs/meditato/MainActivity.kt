package com.ddevs.meditato

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ddevs.meditato.ui.theme.MeditatoTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
    private val timerViewModel by viewModels<TimerViewModel>()
    lateinit var navController: NavHostController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        viewModel.checkStreak()
        timerViewModel.breatheState.observe(this) {
            if (it != "") {
                val v: Vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
                } else {
                    //deprecated in API 26
                    v.vibrate(500);
                }
            }
        }
        timerViewModel.timerState.observe(this){
            if(it==TimerViewModel.TimerState.FINISHED){
                viewModel.addToStreak()
                navController.navigate("finish")
            }
        }
        setContent {
            MeditatoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    navController = rememberNavController()
                    NavHost(navController, startDestination = "main") {
                        composable("main") { HomeScreen(navController, viewModel,timerViewModel) }
                        composable("timer") { TimerScreen(timerViewModel,viewModel,navController) }
                        composable("finish"){ FinishScreen(navController) }
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        timerViewModel.cancelTimer()
        navController.navigate("main")
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MeditatoTheme {

    }
}