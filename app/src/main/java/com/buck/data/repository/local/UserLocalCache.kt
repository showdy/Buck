package com.buck.data.repository.local

import androidx.lifecycle.LiveData
import com.buck.data.db.User
import com.buck.data.db.UserDao

class UserLocalCache(private val userDao: UserDao) {


    fun getUserByName(loginName: String): LiveData<User> {
        return userDao.getUserByName(loginName)
    }


    suspend fun insert(user: User) {
        userDao.insert(user)
    }
}