package com.ddevs.meditato

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun FinishScreen(navController: NavController) {
    BackHandler {
        navController.popBackStack("main",false)
    }

    ConstraintLayout(Modifier.fillMaxSize(), content = {
        val (image, fact) = createRefs()
        Image(
            painterResource(id = R.drawable.ic_undraw_mindfulness_scgo__1_), "Start Meditation",
            modifier = Modifier
                .size(200.dp)
                .constrainAs(image) {
                    top.linkTo(parent.top, margin = 32.dp)
                    absoluteLeft.linkTo(parent.absoluteLeft, margin = 8.dp)
                    absoluteRight.linkTo(parent.absoluteRight, margin = 8.dp)
                }
        )
        Column(
            Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .constrainAs(fact) {
                    top.linkTo(image.bottom, margin = 8.dp)
                    bottom.linkTo(parent.bottom, margin = 8.dp)
                    absoluteLeft.linkTo(parent.absoluteLeft, margin = 8.dp)
                    absoluteRight.linkTo(parent.absoluteRight, margin = 8.dp)
                }, horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                elevation = 20.dp,
                backgroundColor = Color.White,
                shape = RoundedCornerShape(percent = 20)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        "Fun Fact",
                        style = TextStyle.Default.copy(fontSize = 36.sp, color = Color(0xFFFF6600)),
                        modifier = Modifier.padding(16.dp)
                    )
                    Text(
                        "\"Meditation is a memory booster and keeps you focused.\"",
                        style = TextStyle.Default.copy(fontSize = 18.sp, color = Color.LightGray),
                        modifier = Modifier.padding(16.dp, 4.dp, 16.dp, 16.dp)
                    )
                }
            }
            Text(
                "Keep Meditating",
                style = TextStyle.Default.copy(
                    fontSize = 28.sp,
                    color = Color(0xFFFF6600),
                    fontStyle = FontStyle.Italic
                ),
                modifier = Modifier.padding(16.dp)
            )
            Button(
                onClick={ Log.d("Button","clicked")
                    navController.navigate("main") },
                modifier=Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(percent = 20),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFFF6600), contentColor = Color.White),
            ) {
                Text("Go to home",Modifier,style= MaterialTheme.typography.body1)
            }
        }
    })
}

@Preview(showBackground = true)
@Composable
fun FinishPreview() {
    MeditatoTheme {
        FinishScreen(rememberNavController())
    }
}