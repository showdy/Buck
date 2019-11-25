package com.buck.data.db

import androidx.room.*

/**
 * loginName，phonenumber，email，userId 四字段不能为null
 */
@Entity(tableName = "table_user")
data class User(
    val admin: Boolean?,
    val avatar: String?,
    val email: String,
    val loginDate: String?,
    val loginIp: String?,
    val loginName: String,
    val phonenumber: String,
    val sex: String?,
    @PrimaryKey(autoGenerate = false)
    val userId: Int,
    val userName: String?
)




