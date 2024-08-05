package com.example.eyebrow_symmetry.activitiesUI

import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.example.eyebrow_symmetry.R

@Composable
fun TakeSelfieUI(openCamera: ()->Unit){
    Box(modifier = Modifier.fillMaxSize()){
        Image(painter = painterResource(id = R.drawable.img_background), contentDescription = "",
            modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)
        Column(verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())) {

            Column(verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Get",
                    textAlign = TextAlign.Start,
                    color = Color.Black,
                    fontSize = 70.sp,
                    fontFamily = FontFamily(Font(R.font.blacksword)),
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp),
                    letterSpacing = 7.sp
                )
                Text(
                    text = "Ready",
                    textAlign = TextAlign.End,
                    color = Color.Black,
                    fontSize = 70.sp,
                    fontFamily = FontFamily(Font(R.font.blacksword)),
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 50.dp),
                    letterSpacing = 7.sp
                )
            }

            Column(verticalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp)) {
                Box {
                    ElevatedCard(
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.size(180.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 20.dp
                        )
                    ) {

                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(id = R.drawable.img_no_glasses),
                                contentDescription = "Remove your glasses",
                                contentScale = ContentScale.Crop
                            )

                            Box(
                                contentAlignment = Alignment.BottomCenter,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = 5.dp)
                            ) {
                                Text(
                                    text = "Remove Glasses",
                                    color = Color.White,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontFamily = FontFamily(Font(R.font.montserrat_regular))
                                )
                            }
                        }
                    }
                }

                Box(modifier = Modifier.align(Alignment.CenterHorizontally)) {
                    ElevatedCard(
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.size(180.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 20.dp
                        )
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(id = R.drawable.img_no_makeup),
                                contentDescription = "No Makeup",
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                contentAlignment = Alignment.BottomCenter,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = 5.dp)
                            ) {
                                Text(
                                    text = "No Makeup",
                                    color = Color.White,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontFamily = FontFamily(Font(R.font.montserrat_regular))
                                )
                            }
                        }
                    }
                }

                Box(modifier = Modifier.align(Alignment.End)) {
                    ElevatedCard(
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.size(180.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 20.dp
                        )
                    ) {
                        Box(modifier = Modifier.fillMaxSize()) {
                            Image(
                                painter = painterResource(id = R.drawable.img_tie_hair),
                                contentDescription = "Tie your hair",
                                contentScale = ContentScale.Crop
                            )
                            Box(
                                contentAlignment = Alignment.BottomCenter,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(bottom = 5.dp)
                            ) {
                                Text(
                                    text = "Tie your hair",
                                    color = Color.White,
                                    fontSize = 22.sp,
                                    fontWeight = FontWeight.ExtraBold,
                                    fontFamily = FontFamily(Font(R.font.montserrat_regular))
                                )
                            }
                        }
                    }
                }
            }

            Button(
                onClick = openCamera,
                shape = RoundedCornerShape(20.dp),
                enabled = true,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = Color.LightGray),
                modifier = Modifier
                    .size(width = 250.dp, height = 80.dp)
                    .padding(bottom = 20.dp)
            ){
                Text(text = "Take a Selfie",color = Color.Black,  fontSize = 25.sp, fontFamily = FontFamily(Font(R.font.black_bruno)))
            }

        }
    }
}

@Composable
@Preview(showBackground = true)
fun TakeSelfiePreview(){
    var openCamera by rememberSaveable { mutableStateOf(false) }
    TakeSelfieUI(openCamera = {openCamera = true})
}