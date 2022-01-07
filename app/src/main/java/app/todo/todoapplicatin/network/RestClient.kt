package app.todo.todoapplicatin.network

import android.text.TextUtils
import app.todo.todoapplicatin.BuildConfig
import app.todo.todoapplicatin.base.App
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RestClient {

    companion object {
        val instance = RestClient()
        val TIME_OUT = 45L
        lateinit var apiService: TodoApis
        lateinit var BASE_URL: String
    }

    init {
        val gson = Gson()
        val okHttpBuilder = OkHttpClient.Builder()
        okHttpBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .readTimeout(TIME_OUT, TimeUnit.SECONDS).writeTimeout(TIME_OUT, TimeUnit.SECONDS)
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        if (BuildConfig.DEBUG) {
            BASE_URL = App.APP_BASE_URL
            interceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            BASE_URL = App.APP_BASE_URL
            interceptor.level = HttpLoggingInterceptor.Level.NONE
        }

        val okHttpClient =
            okHttpBuilder.addInterceptor(interceptor).addInterceptor(HeaderInterceptor()).build()

        val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).client(okHttpClient).build()
        apiService = retrofit.create(TodoApis::class.java)

    }

    class HeaderInterceptor : Interceptor {
        private var token = ""

        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

            var request = chain.request()
            if (!TextUtils.isEmpty(token)) {
                if (BuildConfig.DEBUG) {
                    request = request.newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .header("connection", "close")
                        .build()
                } else {
                    request = request.newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .header("connection", "close")
                        .build()
                }
            } else {
                if (BuildConfig.DEBUG) {
                    request = request.newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .header("connection", "close")
                        .build()
                } else {
                    request = request.newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .header("connection", "close")
                        .build()
                }
            }
            return chain.proceed(request)

        }

    }

    fun getServiceApi(): TodoApis {
        return apiService
    }

}
