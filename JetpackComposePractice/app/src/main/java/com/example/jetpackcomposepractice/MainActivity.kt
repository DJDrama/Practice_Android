package com.example.jetpackcomposepractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.imageFromResource
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .border(
                            border = BorderStroke(
                                width = 1.dp,
                                color = Color.Black
                            )
                        ),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Item1",
                        // for columns, children can be only horizontal
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "Item2",
                        // for columns, children can be only horizontal
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                }
                Spacer(modifier = Modifier.padding(20.dp))
                Row(
                    modifier = Modifier
                        .width(200.dp)
                        .height(200.dp)
                        .border(border = BorderStroke(1.dp, Color.Black)),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Item2",
                        // for rows, children can be only vertical
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }


//        setContent {
//            ScrollableColumn(
//                modifier = Modifier
////                    .fillMaxHeight()
////                    .fillMaxWidth()
//                    .fillMaxSize()
//                    .background(color = Color(0xFFF2F2F2))
//            ) {
//                Image(
//                    bitmap = imageFromResource(resources, resId = R.drawable.happy_meal_small),
//                    modifier = Modifier.height(300.dp),
//                    contentScale = ContentScale.Crop
//                )
//                Column(modifier = Modifier.padding(16.dp)) {
//                    Text(
//                        text = "Happy Meal",
//                        style = TextStyle(
//                            fontSize = TextUnit.Companion.Sp(26)
//                        )
//                    )
//                    Spacer(modifier = Modifier.padding(top = 10.dp))
//                    Text(
//                        text = "800 Calories",
//                        style = TextStyle(
//                            fontSize = TextUnit.Companion.Sp(17)
//                        )
//                    )
//                    Spacer(modifier = Modifier.padding(top = 10.dp))
//                    Text(
//                        text = "₩4,500",
//                        style = TextStyle(
//                            color = Color(0xFF85bb65),
//                            fontSize = TextUnit.Companion.Sp(17)
//                        )
//                    )
//                }
//            }
//        }
    }
}