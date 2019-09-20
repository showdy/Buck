package com.buck.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BuckApiService {
    //api...



    companion object {
        operator fun invoke(): BuckApiService {
            //添加公共的参数
            val requestInterceptor = Interceptor {
                val url = it.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter("key", "")
                    .build()
                val request = it.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor it.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .build()
            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://www.baidu.com/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BuckApiService::class.java)
        }
    }
}