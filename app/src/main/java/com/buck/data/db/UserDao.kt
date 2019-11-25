package com.buck.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Query("SELECT * FROM `table_user` WHERE `loginName` LIKE :loginName")
    fun getUserByName(loginName: String): LiveData<User>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: User)

}