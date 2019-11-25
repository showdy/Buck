package com.buck.data.repository.remote

import com.buck.data.db.User
import com.buck.data.model.RequestResult
import com.buck.data.network.ApiService


/**
 * 真正的网络请求执行类
 */
class LoginDataSource {


    suspend fun login(username: String, password: String): RequestResult<User> {
        return try {
            val response = ApiService.invoke().login(
                username = username,
                password = password,
                rememberMe = false
            )
            if (response.code == 0) {
                RequestResult.success(response.data)
            } else {
                RequestResult.error(response.message)
            }
        } catch (e: Throwable) {
            RequestResult.error(msg = e.message)
        }

    }


}