package com.ddevs.meditato

import android.widget.ImageButton
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ddevs.meditato.ui.theme.MeditatoTheme
import com.ddevs.meditato.ui.theme.OrangePrimary
import dagger.hilt.android.AndroidEntryPoint

@Composable
fun HomeScreen(navController:NavController, viewModel: MainViewModel,timerViewModel: TimerViewModel){
    ConstraintLayout(Modifier.fillMaxSize()) {
        val (card,button) = createRefs()

        StreakCard(viewModel.getStreak(),Modifier.constrainAs(card){
            top.linkTo(parent.top,margin=16.dp)
        })

        Image(painterResource(id = R.drawable.ic_baseline_play_circle_24),"Start Meditation",
            modifier= Modifier
                .size(100.dp)
                .clip(CircleShape)
                .constrainAs(button) {
                    top.linkTo(card.bottom, margin = 8.dp)
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                    absoluteLeft.linkTo(parent.absoluteLeft, margin = 8.dp)
                    absoluteRight.linkTo(parent.absoluteRight, margin = 8.dp)
                }
                .clickable {
                    timerViewModel.startTimer()
                    navController.navigate("timer")
                }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StreakCard(streak:Int,modifier: Modifier){
    Card(modifier = Modifier
        .composed { modifier }
        .padding(16.dp)
        .fillMaxWidth()
        .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(20.dp,10.dp),
        colors = CardDefaults.cardColors(Color.White),
        shape= RoundedCornerShape(percent=20)){
        Row(modifier= Modifier
            .fillMaxWidth()
            .padding(8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically){
            Image(painterResource(id = R.drawable.ic_baseline_check_circle_24),"Streak vector",modifier= Modifier
                .padding(32.dp, 16.dp, 16.dp, 16.dp)
                .size(40.dp)
               )
            Text(streak.toString(), style = TextStyle.Default.copy(fontSize = 36.sp, color = Color.Black),modifier=Modifier.padding(16.dp))
            Text("day Streak", style = TextStyle.Default.copy(fontSize = 18.sp, color = Color.Gray),modifier=Modifier.padding(0.dp,16.dp,16.dp,16.dp))
        }

    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    MeditatoTheme() {
        HomeScreen(navController = rememberNavController(), viewModel = hiltViewModel<MainViewModel>(),
            hiltViewModel<TimerViewModel>())
    }
}