package com.buck.data.repository

import com.buck.data.db.User
import com.buck.data.model.RequestResult
import com.buck.data.repository.local.UserLocalCache
import com.buck.data.repository.remote.LoginDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRepository(
    private val dataSource: LoginDataSource,
    private val userCache: UserLocalCache
) {


    suspend fun login(username: String, password: String): RequestResult<User> {
        return withContext(Dispatchers.IO) {
            val result = dataSource.login(username, password)

            if (result.isSuccess() && result.data != null) {
                //插入数据库
                userCache.insert(result.data)
            }
            return@withContext result
        }
    }


}