package com.buck.data.network

import com.buck.BuildConfig
import com.buck.data.db.User
import com.buck.data.model.RequestResult
import com.buck.internal.AppProperties
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 *  baseUrl: 目录形式 https://xxx/xxx/xxx/
 *  path: 相对路径： xxx/xxx
 *
 *  完整url： baseUrl+ path
 */
interface ApiService {

    /*登录接口*/
    @FormUrlEncoded
    @POST("api/login")
    suspend fun login(
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("rememberMe") rememberMe: Boolean = false
//        @Field("validateCode") validateCode: String
    ): RequestResult<User>





    companion object {

        val BASE_URL
            get() = AppProperties.baseURL


        operator fun invoke(): ApiService {

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level =
                if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
//                .addInterceptor(HeaderInterceptor())
                .retryOnConnectionFailure(true)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(AppProperties.baseURL)
//                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}