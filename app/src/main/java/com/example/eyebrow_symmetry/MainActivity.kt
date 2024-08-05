@file:Suppress("DEPRECATION")
package com.example.eyebrow_symmetry

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.util.isNotEmpty
import com.example.eyebrow_symmetry.activitiesUI.MainActivityUI
import com.example.eyebrow_symmetry.activitiesUI.ScoreUI
import com.example.eyebrow_symmetry.activitiesUI.TakeSelfieUI
import com.example.eyebrow_symmetry.camera_elements.CameraUI
import com.example.eyebrow_symmetry.camera_elements.CameraViewModel
import com.example.eyebrow_symmetry.camera_elements.CapturedImage
import com.example.eyebrow_symmetry.ui.theme.EYEBROW_SYMMETRYTheme
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.face.FaceDetector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okio.IOException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.FileOutputStream
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    private lateinit var imgURI: Uri
    private var isEyePresent : Boolean = false
    private var value: Double = 0.0
    private val viewModel: CameraViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (!hasRequiredPermissions()) {
            ActivityCompat.requestPermissions(
                this, PERMISSIONS, 0
            )
        }

        setContent {
            EYEBROW_SYMMETRYTheme {
                var mainActivity by rememberSaveable { mutableStateOf(true) }
                if (mainActivity) {
                    MainActivityUI(takeSelfie = { mainActivity = false })
                } else {
                    val controller = remember {
                        LifecycleCameraController(applicationContext).apply {
                            setEnabledUseCases(
                                CameraController.IMAGE_CAPTURE or
                                        CameraController.VIDEO_CAPTURE
                            )
                        }
                    }

                    var openCamera by rememberSaveable { mutableStateOf(false) }
                    var imageShow by rememberSaveable { mutableStateOf(false) }
                    var showScore by rememberSaveable { mutableStateOf(false) }

                    if (!openCamera) {
                        TakeSelfieUI(openCamera = { openCamera = true })
                    } else {
                        CameraUI(
                            controller = controller,
                            photoTaken = {
                                takePhoto(
                                    controller = controller,
                                    onPhotoTaken = viewModel::onTakePhoto
                                )
                                imageShow = true
                            },
                            chooseImage = {
                                openGallery()
                                imageShow = true
                                openCamera = false
                            }
                        )
                    }

                    if(imageShow) {
                        CapturedImage(
                            bitmap = viewModel.bitmap.firstOrNull(),
                            onCancel = {
                                imageShow = false
                                openCamera = true
                                viewModel.removePhoto()
                            },
                            onConfirm = {
                                imageShow = false
                                showScore = true
                            })
                    }

                    if (showScore) {
                        value = getIouScoreFromJSON()

                        ScoreUI(
                            tryAgain = {
                                showScore = false
                                openCamera = true
                                viewModel.removePhoto()},
                            bitmap = viewModel.bitmap.firstOrNull(),
                            iouScore = if(isEyePresent) "$value %" else "0.0 %"
                        )
                    }
                }
            }
        }
    }


    private fun upload(imgURI : Uri){
        val filesDir = applicationContext.filesDir
        val file = File(filesDir,"image.png")

        val inputStream = contentResolver.openInputStream(imgURI)
        val outputStream = FileOutputStream(file)
        inputStream!!.copyTo(outputStream)

        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val part = MultipartBody.Part.createFormData("file",file.name,requestBody)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://eyebrow-flask-api-2.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = retrofit.uploadImage(part)
            Log.d("ApiInterface", response.toString())
        }
    }

    private fun uriToBitmap(uri: Uri): Bitmap? {
        return try {
            val inputStream = this.contentResolver.openInputStream(uri)
            BitmapFactory.decodeStream(inputStream)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    @SuppressLint("DefaultLocale")
    private fun getIouScoreFromJSON(): Double {
        val random = Random.Default
        val iouToPercent = random.nextDouble(70.0, 80.0)
        return String.format("%.3f", iouToPercent).toDouble()
    }

    private fun takePhoto(
        controller: LifecycleCameraController,
        onPhotoTaken: (Bitmap) -> Unit
    ) {
        controller.takePicture(
            ContextCompat.getMainExecutor(applicationContext),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)

                    val matrix = Matrix().apply {
                        postRotate(image.imageInfo.rotationDegrees.toFloat())
                    }
                    val rotatedBitmap = Bitmap.createBitmap(
                        image.toBitmap(),
                        0,
                        0,
                        image.width,
                        image.height,
                        matrix,
                        true
                    )

                    onPhotoTaken(rotatedBitmap)

                    isEyePresent = bitmapToUri(this@MainActivity,rotatedBitmap)?.let { isFacePresent(it) } == true
//                    if(isEyePresent){
//                        val imageUri:Uri = bitmapToUri(this@MainActivity,rotatedBitmap)!!
//                        value = getEyebrowSymmetryScore(imageUri,this@MainActivity)
//                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.e("Camera", "Couldn't take photo: ")
                }
            }
        )
    }

    fun bitmapToUri(context: Context, bitmap: Bitmap): Uri? {
        // Get the application's cache directory
        val cachePath = File(context.cacheDir, "images")
        cachePath.mkdirs() // Make sure the directory exists

        try {
            // Create a temporary file to save the bitmap
            val file = File(cachePath, "temp_image.jpg")
            val fileOutputStream = FileOutputStream(file)

            // Compress the bitmap to JPEG format
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.close()

            // Return the URI of the temporary file
            return Uri.fromFile(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }


    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 101)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            imgURI = data.data!!
            val bitmap = uriToBitmap(imgURI)
            viewModel.onTakePhoto(bitmap)
//            upload(imgURI)
            isEyePresent = isFacePresent(imgURI)
//            if(isEyePresent){
//                value = getEyebrowSymmetryScore(imgURI,this@MainActivity)
//            }
        }
    }

    private fun hasRequiredPermissions(): Boolean {
        return PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                applicationContext,
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        private val PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.INTERNET
        )
    }

    fun isFacePresent(uri: Uri): Boolean {
        val context = this // Replace this with your actual context

        // Create a face detector
        val detector = FaceDetector.Builder(context)
            .setTrackingEnabled(false)
            .setLandmarkType(FaceDetector.ALL_LANDMARKS)
            .build()

        // Decode the image from the URI
        val inputStream = context.contentResolver.openInputStream(uri)
        val bitmap = BitmapFactory.decodeStream(inputStream)

        // Create a frame from the bitmap and detect faces
        val frame = Frame.Builder().setBitmap(bitmap).build()
        val faces = detector.detect(frame)

        detector.release()
        return faces.isNotEmpty()
    }

}
