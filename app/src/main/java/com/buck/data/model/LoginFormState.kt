package com.buck.data.model

data class LoginFormState(
    val usernameError: Int? = null,
    val passwordError: Int? = null,
    var isDataValid: Boolean = false
)