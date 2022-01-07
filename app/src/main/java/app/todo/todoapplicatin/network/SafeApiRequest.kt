package app.todo.todoapplicatin.network

import com.google.gson.GsonBuilder
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.orhanobut.logger.Logger
import okhttp3.ResponseBody
import org.json.JSONException
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*

abstract class SafeApiRequest {
    suspend fun <T : Any> makeRequest(apiCall: suspend () -> Response<T>): T {
        val response = apiCall.invoke()
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            // Parse Exception here before throwing..
            throw AppException(parseError(response.errorBody()))
        }
    }
}

fun parseError(errorBody: ResponseBody?): String {
    val errorString = errorBody?.string() ?: "Something went wrong."
    Logger.d("Api -> $errorString")
    if (errorString.toUpperCase(Locale.getDefault()) == "ERROR") {
        return "Something went wrong."
    }
    val errorResponse = GsonBuilder().serializeNulls()
        .setLenient()
        .create()
        .fromJson(errorString, ErrorResponse::class.java)
    if (errorResponse.message == "Unauthorized") {
        return "You are not authorized to perform this request."
    }
    return "${errorResponse.status} - ${errorResponse.message}"
}

data class ErrorResponse(
    val error: String = "",
    val message: String = "",
    val path: String = "",
    val status: Int = 0,
    val timestamp: String = ""
)

class AppException(message: String) : IOException(message)

class ExceptionHandler {
    companion object {
        fun handle(e: Throwable): String {
            Logger.d(e)
            lateinit var error: String
            when (e) {
                is UnknownHostException -> {
                    error = "Couldn't connect to server"
                }
                is SocketTimeoutException -> {
                    error = "Couldn't connect to server"
                }
                is IOException -> {
                    error = "${e.message}"
                }
                is JsonSyntaxException -> {
                    error = "${e.message}"
                }
                is JSONException -> {
                    error = "${e.message}"
                }
                is JsonParseException -> {
                    error = "${e.message}"
                }
                else -> {
                    error = "Something went wrong..."
                }
            }
            return error
        }
    }
}