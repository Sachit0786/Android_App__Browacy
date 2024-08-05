package com.example.eyebrow_symmetry

data class ApiResponse(
    var iouScore: Double,
    var message: String,
    var base64: String
)
