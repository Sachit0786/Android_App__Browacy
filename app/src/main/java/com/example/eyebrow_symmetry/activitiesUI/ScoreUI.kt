package com.example.eyebrow_symmetry.activitiesUI

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eyebrow_symmetry.R
import com.example.eyebrow_symmetry.camera_elements.CameraViewModel

@Composable
fun ScoreUI(
    tryAgain: ()->Unit,
    bitmap: Bitmap?,
    iouScore: String
){
    Surface(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)){

        Image(painter = painterResource(id = R.drawable.img_background), contentDescription = null,
            modifier = Modifier.fillMaxSize(), contentScale = ContentScale.Crop)

        Column(modifier = Modifier.fillMaxWidth()
            .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            if (bitmap != null) {
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .size(270.dp,400.dp),
                    alignment = Alignment.Center
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(60.dp)
                )
                Text(
                    text = iouScore,
                    color = Color.Black,
                    modifier = Modifier.wrapContentSize(),
                    fontSize = 40.sp,
                    fontFamily = FontFamily(Font(R.font.blacksword))
                )
            }
            else{
                Text(
                    text = "Kindly Wait While",
                    modifier = Modifier.wrapContentSize(),
                    color = Color.Black,
                    fontSize = 40.sp,
                    fontFamily = FontFamily(Font(R.font.blacksword))
                )
                Text(
                    text = "Your Image is being",
                    modifier = Modifier.wrapContentSize(),
                    color = Color.Black,
                    fontSize = 40.sp,
                    fontFamily = FontFamily(Font(R.font.blacksword))
                )
                Text(
                    text = "processed",
                    modifier = Modifier.wrapContentSize(),
                    color = Color.Black,
                    fontSize = 40.sp,
                    fontFamily = FontFamily(Font(R.font.blacksword))
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp))
            Button(
                onClick = tryAgain,
                shape = RoundedCornerShape(20.dp),
                enabled = true,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = Color.LightGray),
                modifier = Modifier
                    .size(width = 250.dp, height = 100.dp)
            ){
                Text(text = "Try Again", fontSize = 21.sp, fontFamily = FontFamily(Font(R.font.black_bruno)))
            }
        }
    }
}

@Preview
@Composable
private fun Testing() {
    val viewModel = CameraViewModel()
    ScoreUI(
        tryAgain = {},
        bitmap = viewModel.bitmap.firstOrNull(),
        iouScore = "125.5")
}