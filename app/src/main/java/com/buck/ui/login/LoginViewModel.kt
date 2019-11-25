package com.buck.ui.login

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.buck.R
import com.buck.data.db.BuckDatabase
import com.buck.data.db.User
import com.buck.data.model.LoginFormState
import com.buck.data.model.RequestResult
import com.buck.data.repository.LoginRepository
import com.buck.data.repository.local.UserLocalCache
import com.buck.data.repository.remote.LoginDataSource
import com.medisana.tender.ui.base.BaseViewModel
import kotlinx.coroutines.launch


class LoginViewModel(application: Application) : BaseViewModel(application) {


    private val _loginResult = MutableLiveData<RequestResult<User>>()
    val loginResult: LiveData<RequestResult<User>> = _loginResult


    private val _loginFormState = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginFormState

    val loginRepository: LoginRepository

    init {
        val userDao = BuckDatabase.invoke(application, viewModelScope).userDao()

        val userCache = UserLocalCache(userDao)

        val dataSource = LoginDataSource()

        loginRepository = LoginRepository(dataSource, userCache)
    }


    //登录
    fun login(username: String, password: String) {

        viewModelScope.launch {
            val login = loginRepository.login(username, password)
            _loginResult.postValue(login)
        }
    }


    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginFormState.value = LoginFormState(usernameError = R.string.invalid_user_name)
        } else if (!isPasswordValid(password)) {
            _loginFormState.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginFormState.value = LoginFormState(isDataValid = true)
        }

    }


    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return username.isNotBlank()
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 4
    }
}