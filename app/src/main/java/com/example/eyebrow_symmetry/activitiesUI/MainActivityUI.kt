package com.example.eyebrow_symmetry.activitiesUI

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eyebrow_symmetry.R

@Composable
fun MainActivityUI(takeSelfie: ()->Unit){
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)){
            Image(painter = painterResource(id = R.drawable.img_background), contentDescription = null,
                modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
            Column(verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
                    .padding(vertical = 100.dp)
                    .verticalScroll(rememberScrollState())) {

                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Know Your",
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontSize = 70.sp,
                        fontFamily = FontFamily(Font(R.font.blacksword)),
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = "Eyebrows",
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontSize = 70.sp,
                        fontFamily = FontFamily(Font(R.font.blacksword)),
                        fontWeight = FontWeight.ExtraBold,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                Column(verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Unleash your natural beauty",
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontSize = 35.sp,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = "with perfectly symmetrical",
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontSize = 35.sp,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = "eyebrows. Let's help you turn",
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontSize = 35.sp,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                    Text(
                        text = "heads!",
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontSize = 35.sp,
                        fontFamily = FontFamily.Cursive,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                    )
                }

                Button(
                    onClick = takeSelfie,
                    shape = RoundedCornerShape(20.dp),
                    enabled = true,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary,
                        disabledContainerColor = Color.LightGray),
                    modifier = Modifier
                        .size(width = 280.dp, height = 80.dp)
                ){
                    Text(text = "Get Started",color = Color.Black, fontSize = 20.sp, fontFamily = FontFamily(Font(R.font.black_bruno)))
                }
            }
    }
}

@Composable
@Preview
fun MainActivityUIPreview(){
    var mainActivity by rememberSaveable { mutableStateOf(true) }
    if(mainActivity) MainActivityUI(takeSelfie = { mainActivity = false})
    MainActivityUI(takeSelfie = { mainActivity = false})
}