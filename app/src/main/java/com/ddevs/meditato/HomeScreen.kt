package com.ddevs.meditato

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ddevs.meditato.ui.theme.MeditatoTheme

@Composable
fun HomeScreen(navController:NavController, viewModel: MainViewModel,timerViewModel: TimerViewModel){
    val streak by viewModel.streakFlow.collectAsState(initial = 0)
    ConstraintLayout(Modifier.fillMaxSize()) {
        val (card,button) = createRefs()
        StreakCard(streak,Modifier.constrainAs(card){
            top.linkTo(parent.top,margin=16.dp)
        })

        Column(
            Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .constrainAs(button) {
                    top.linkTo(card.bottom, margin = 8.dp)
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                    absoluteLeft.linkTo(parent.absoluteLeft, margin = 8.dp)
                    absoluteRight.linkTo(parent.absoluteRight, margin = 8.dp)
                }, horizontalAlignment = Alignment.CenterHorizontally) {

            Image(painterResource(id = R.drawable.ic_baseline_play_circle_24), "Start Meditation",
                modifier = Modifier
                    .size(100.dp)
                    .clip(CircleShape)
                    .clickable {
                        timerViewModel.startTimer()
                        navController.navigate("timer")
                    }
            )

            Text(
                "Lets Meditate!!",
                style = TextStyle.Default.copy(
                    fontSize = 28.sp,
                    color = Color(0xFFFF6600),
                    fontStyle = FontStyle.Italic
                ),
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun StreakCard(streak:Int,modifier: Modifier){
    Card(modifier = Modifier
        .composed { modifier }
        .padding(16.dp)
        .fillMaxWidth()
        .wrapContentHeight(),
        elevation = 20.dp,
        backgroundColor = Color.White,
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