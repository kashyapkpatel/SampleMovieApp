package com.kashyapkpatel.sampleapp.network.repo

import android.content.Context
import com.google.gson.Gson
import com.kashyapkpatel.sampleapp.R
import com.kashyapkpatel.sampleapp.network.models.errors.ApiError

import com.kashyapkpatel.sampleapp.util.Resource
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

open class BaseRepository @Inject constructor(open val context: Context) {

    @Inject
    lateinit var gson: dagger.Lazy<Gson>

    suspend fun <T : Any> safeApiCall(call: suspend () -> Response<T>): Resource<T> {
        return extractResultResource(call)
    }

    private suspend fun <T> extractResultResource(call: suspend () -> Response<T>): Resource<T> {
        return try {
            val response = call.invoke()
            if (response.isSuccessful) {
                Resource.success(response.body())
            } else {
                val errBodyString = response.errorBody()?.string()
                val errorResponse = gson.get().fromJson(errBodyString, ApiError::class.java)
                val errorMessage = parseErrorMessage(errorResponse)
                // Log the error message.
                println("BaseRepository: FAIL $errBodyString \n $errorMessage")
                Resource.error(msg = errorMessage)
            }
        } catch (e: Throwable) {
            val apiError = parseErrorResponse(e)
            val errorMessage = parseErrorMessage(apiError)
            val errMsg = when (e) {
                is HttpException -> errorMessage
                is ConnectException -> context.getString(R.string.error_internet_unavailable)
                is UnknownHostException -> context.getString(R.string.error_internet_unavailable)
                is SocketTimeoutException -> context.getString(R.string.error_socket_timeout)
                else -> context.getString(R.string.error_generic_failure)
            }
            // Log the error message.
            println("BaseRepository: THROWABLE ${apiError.toString()} \n" + " $errorMessage")
            Resource.error(msg = errMsg)
        }
    }

    private fun parseErrorResponse(throwable: Throwable?): ApiError? {
        var errorResponse: ApiError? = null
        throwable?.let {
            if (it is HttpException) {
                val responseBodyJson = it.response()?.errorBody()?.string()
                errorResponse =  gson.get().fromJson(responseBodyJson, ApiError::class.java)
            }
        }
        return errorResponse
    }

    private fun parseErrorMessage(apiError: ApiError?): String {
        var message = context.getString(R.string.error_generic_failure)
        apiError?.let {
            when (it.status_code) {
                503 -> {
                    message = context.getString(R.string.error_server_error)
                }
                else -> {
                    message = it.status_message ?: context.getString(R.string.error_generic_failure)
                }
            }
        }
        return message
    }

}