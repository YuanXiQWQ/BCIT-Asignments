package com.bcit.lecture8

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random

data class Cartoon(val name:String, val imageId:Int)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Box(modifier = Modifier.safeDrawingPadding()){
                MainContent()
            }
        }
    }

    @Composable
    fun MainContent(){

        val sponge = stringResource(R.string.sponge)
        val cartoons = stringArrayResource(R.array.cartoons)

        val cartoonList = listOf(
            Cartoon(sponge, R.drawable.spongebob),
            Cartoon(cartoons[0], R.drawable.krabs),
            Cartoon(cartoons[1], R.drawable.patrick),
            Cartoon(cartoons[2], R.drawable.pearl),
            Cartoon(cartoons[3], R.drawable.sandy),
            Cartoon(cartoons[4], R.drawable.squidward),
        )

        val stateList = remember{
            cartoonList.toMutableStateList()
        }

        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            LazyColumn(modifier = Modifier.padding(bottom = 60.dp)) {
                items(stateList.size) {
                    MyCard(stateList[it])
                }
            }
            Button(
                modifier = Modifier.align(alignment = Alignment.BottomCenter),
                shape = RectangleShape,
                onClick = {
                    val r = Random.nextInt(cartoonList.size)
                    stateList.add(cartoonList[r])
                }) {
                Text("Add")
            }
        }
    }

    @Composable
    fun MyCard(cartoon: Cartoon){
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            elevation = CardDefaults.elevatedCardElevation(1.dp),
            border = BorderStroke(1.dp, Color.Blue)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(150.dp)
            ) {
                Image(
                    modifier = Modifier
                        .padding(all = 12.dp)
                        .clip(CircleShape),
                    painter = painterResource(cartoon.imageId),
                    contentDescription = null
                )
                Spacer(modifier = Modifier.width(80.dp))
                Column {
                    Text(cartoon.name, fontSize = 28.sp)
                    Button(
                        enabled = true,
                        shape = RectangleShape,
                        onClick = {
                            println("Clicked Button")
                        }) {
                        Text("View Info")
                    }
                }
            }

        }
    }

}
