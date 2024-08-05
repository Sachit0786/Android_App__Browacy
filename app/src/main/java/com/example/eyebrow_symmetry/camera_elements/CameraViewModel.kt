package com.example.eyebrow_symmetry.camera_elements

import android.annotation.SuppressLint
import android.graphics.Bitmap
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel

class CameraViewModel : ViewModel() {

    private val _bitmap = mutableStateListOf<Bitmap?>()
    val bitmap: MutableList<Bitmap?>
        get() = _bitmap

    fun onTakePhoto(bitmap: Bitmap?) {
        _bitmap.add(bitmap)
    }
    @SuppressLint("SuspiciousIndentation")
    fun removePhoto() {
        if(!_bitmap.isEmpty())
        _bitmap.removeAt(0)
    }
}
