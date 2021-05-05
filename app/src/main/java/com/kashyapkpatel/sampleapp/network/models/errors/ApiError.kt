package com.kashyapkpatel.sampleapp.network.models.errors

data class ApiError(
    val status_code: Int,
    val status_message: String,
    val success: Boolean
)