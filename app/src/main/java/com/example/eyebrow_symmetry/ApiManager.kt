package com.example.eyebrow_symmetry

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import android.util.Log
import retrofit2.create


class ApiManager {
//    private val retrofit = Retrofit.Builder()
//        .baseUrl("https://eyebrow-flask-api-2.onrender.com/")
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//        .create(ApiService::class.java)
//
//    private val service: ApiService = retrofit.create(ApiService::class.java)
//
//    suspend fun uploadImage(imageFile: File, callback: (ApiResponse?) -> Unit) {
//        val requestFile = imageFile.asRequestBody("image/*".toMediaTypeOrNull())
//        val body = MultipartBody.Part.createFormData("image", imageFile.name, requestFile)
//
//        val call = service.uploadImage(body)
//        call.enqueue(object : Callback<ApiResponse> {
//            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
//                if (response.isSuccessful) {
//                    val data = response.body()
//                    callback(data)
//                } else {
//                    Log.e("ApiManager", "Error: ${response.code()}")
//                    callback(null)
//                }
//            }
//
//            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
//                Log.e("ApiManager", "Error: ${t.message}", t)
//                callback(null)
//            }
//        })
//    }
}
