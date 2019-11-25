package com.buck.ui.login

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.Observer
import com.antonioleiva.weatherapp.extensions.DelegatesExt
import com.buck.App
import com.buck.R
import com.buck.internal.afterTextChanged
import com.buck.internal.startActivity
import com.buck.ui.MainActivity
import com.buck.ui.base.BaseFragment

import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : BaseFragment() {

    companion object {
        fun newInstance() = LoginFragment()
    }

    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this@LoginFragment).get(LoginViewModel::class.java)
    }
    private var username: String by DelegatesExt.preference(
        App.instance,
        LoginActivity.USERNAME,
        ""
    )
    private var password: String by DelegatesExt.preference(
        App.instance,
        LoginActivity.PASSWORD,
        ""
    )

    override fun getLayoutResId(): Int = R.layout.fragment_login

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        et_username.setText(username)
        et_username.setSelection(username.length)
        et_pwd.setText(password)
        et_pwd.setSelection(password.length)
        login.isEnabled = !username.isBlank()
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListener()
        observerResult()
    }

    private fun observerResult() {
        //1. 一个LiveData字段用于界面信息的更新
        viewModel.loginFormState.observe(this@LoginFragment, Observer {
            val loginState = it ?: return@Observer
            loginState.passwordError?.apply { ll_pwd.error = getString(this) }
            loginState.usernameError?.apply { ll_username.error = getString(this) }
            ll_username.isErrorEnabled = loginState.usernameError != null
            ll_pwd.isErrorEnabled = loginState.passwordError != null
            login.isEnabled = loginState.isDataValid
        })

        //2. -个LiveData用于网络登录后界面的更新
        viewModel.loginResult.observe(this@LoginFragment, Observer {
            val loginResult = it ?: return@Observer
            showLoginProgressbar(false)
            when {
                it.isSuccess() -> rememberUser()
                it.isError() -> showLoginFailed(loginResult.message!!)
            }
        })
    }


    private fun initListener() {
        et_username.afterTextChanged {
            loginDataChanged()
        }

        et_pwd.afterTextChanged {
            loginDataChanged()
        }

        ck_pwd.setOnCheckedChangeListener { _, isChecked ->
            when {
                isChecked -> et_pwd.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                else -> et_pwd.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            }
        }

        et_pwd.setOnEditorActionListener { _, actionId, _ ->
            when (actionId) {
                EditorInfo.IME_ACTION_DONE -> login()
            }
            false
        }

        login.setOnClickListener {
            login()
        }

        tv_register.setOnClickListener {
//            activity?.startActivity<RegisterActivity>()
        }
    }

    private fun rememberUser() {
        username = et_username.text.toString().trim()
        if (ck_remember.isChecked) {
            //记住登录密码
            password = et_pwd.text.toString().trim()
        }
        context?.startActivity<MainActivity>()
        //跳转成功后，finish Activity
        activity?.finish()
    }


    private fun login() {
        showLoginProgressbar(true)
        viewModel.login(
            et_username.text.toString().trim(),
            et_pwd.text.toString().trim()
        )
    }

    private fun loginDataChanged() {
        viewModel.loginDataChanged(
            et_username.text.toString().trim(),
            et_pwd.text.toString().trim()
        )
    }


    private fun showLoginFailed(errorMsg: String) {
        Toast.makeText(context, errorMsg, Toast.LENGTH_SHORT).show()
    }

    private fun showLoginProgressbar(show: Boolean) {
        if (show) {
            progressbar.visibility = View.VISIBLE
        } else {
            progressbar.visibility = View.INVISIBLE

        }
    }

}
