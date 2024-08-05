package com.example.eyebrow_symmetry.camera_elements

import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.eyebrow_symmetry.R

@Composable
fun CameraUI(controller: LifecycleCameraController,
             photoTaken: () -> Unit,
             chooseImage: () -> Unit){
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            CameraPreview(
                controller = controller,
                modifier = Modifier
                    .fillMaxSize()
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(horizontal = 10.dp, vertical = 10.dp)
                    .height(90.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //open Gallery Button
                Button(
                    onClick = chooseImage,
                    modifier = Modifier
                        .wrapContentSize()
                        .aspectRatio(1f, matchHeightConstraintsFirst = true),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(13.dp)
                ) {
                    Image(painter = painterResource(id = R.drawable.gallery),
                        contentDescription = "open gallery",
                        modifier = Modifier.clip(RoundedCornerShape(10.dp)),
                        contentScale = ContentScale.Fit)
                }

                // Take Photo Button
                OutlinedButton(
                    onClick = photoTaken,
                    modifier = Modifier
                        .wrapContentSize()
                        .aspectRatio(1f, matchHeightConstraintsFirst = true),
                    shape = CircleShape,
                    elevation = ButtonDefaults.buttonElevation(
                        pressedElevation = 3.dp
                    )
                ) {
                    Image(painter = painterResource(id = R.drawable.camera), contentDescription = "take photo,",
                        contentScale = ContentScale.Fit)
                }

                //Rotate Camera Button
                Button(
                    onClick = {
                        controller.cameraSelector =
                            if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                                CameraSelector.DEFAULT_FRONT_CAMERA
                            } else CameraSelector.DEFAULT_BACK_CAMERA
                    },
                    modifier = Modifier
                        .wrapContentSize()
                        .aspectRatio(1f, matchHeightConstraintsFirst = true),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                    shape = CircleShape
                ) {
                    Image(painter = painterResource(id = R.drawable.rotate_camera),
                        contentDescription = "rotate camera",
                        contentScale = ContentScale.Fit)
                }
            }
        }
}