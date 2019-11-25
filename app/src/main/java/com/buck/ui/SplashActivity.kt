package com.buck.ui

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.antonioleiva.weatherapp.extensions.DelegatesExt
import com.buck.R
import com.buck.internal.StatusBarUtils
import com.buck.ui.base.BaseActivity
import com.buck.ui.login.LoginActivity
import com.buck.ui.login.LoginViewModel
import com.yanzhenjie.permission.AndPermission
import com.yanzhenjie.permission.runtime.PermissionDef
import com.buck.internal.RuntimeRationale
import android.content.DialogInterface
import android.text.TextUtils
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.buck.internal.startActivity
import com.squareup.haha.perflib.Main
import com.yanzhenjie.permission.runtime.Permission
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 * 闪屏界面一般的逻辑为：
 * 1. 设置默认的背景图片（theme），反正白屏
 * 2. 加载splash界面的背景图片
 * 3. 如果是第一次安装进入app，则app跳转到用户导航界面（userGide）
 * 4. 进入登录界面
 *
 * 此APP的大致逻辑为：
 * Splash 判断是否登录过，
 * 已登录--> 通过用户信息直接登录
 * 未登录--> 跳转到登录界面
 *
 */
class SplashActivity : BaseActivity() {

    companion object {
        val sHandler = Handler()

        const val REQUEST_CODE_SETTING = 0x001
    }

    private val username: String by DelegatesExt.preference(this, LoginActivity.USERNAME, "")
    private val password: String by DelegatesExt.preference(this, LoginActivity.PASSWORD, "")


    private val viewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun getLayoutResId(): Int = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 适配刘海屏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val layoutParams = window.attributes
            layoutParams.layoutInDisplayCutoutMode =
                WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES
            window.attributes = layoutParams
        }
        // 适配Android 4.4
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            StatusBarUtils.setColor(window, Color.TRANSPARENT, true)
        }

        //检查外部存储可读
        requestPermission(Permission.WRITE_EXTERNAL_STORAGE)

        //存在
//        if (password.isNotBlank()) {
//            sHandler.postDelayed({ viewModel.login(username, password) }, 2000)
//        } else {
//            sHandler.postDelayed({ launchActivity<LoginActivity>() }, 2500)
//        }
//
//        viewModel.loginResult.observe(this, Observer {
//            if (it.isSuccess()) {
//                launchActivity<MainActivity>()
//            } else {
//                launchActivity<LoginActivity>()
//            }
//        })
        launch {
            delay(2000)
            startActivity<MainActivity>()
            finish()
        }

    }

    private fun requestPermission(@PermissionDef vararg permissions: String) {
        AndPermission.with(this)
            .runtime()
            .permission(*permissions)
            .rationale(RuntimeRationale())
            .onGranted {

            }
            .onDenied {
                if (AndPermission.hasAlwaysDeniedPermission(this@SplashActivity, *permissions)) {
                    showSettingDialog(this@SplashActivity, *permissions)
                }
            }
            .start()
    }

    private fun showSettingDialog(context: Context, @PermissionDef vararg permissions: String) {
        val permissionNames = Permission.transformText(context, permissions)
        val message = context.getString(
            R.string.message_permission_always_failed,
            TextUtils.join("\n", permissionNames)
        )

        AlertDialog.Builder(context).setCancelable(false)
            .setTitle(R.string.title_dialog)
            .setMessage(message)
            .setPositiveButton(
                R.string.setting
            ) { dialog, which ->
                setPermission()
            }
            .setNegativeButton(
                R.string.cancel
            ) { dialog, which -> }
            .show()
    }


    private fun setPermission() {
        AndPermission.with(this).runtime().setting().start(REQUEST_CODE_SETTING)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_SETTING -> Toast.makeText(
                this,
                R.string.message_setting_comeback,
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        sHandler.removeCallbacksAndMessages(null)
    }


    private inline fun <reified T> launchActivity() {
        startActivity(
            Intent(this@SplashActivity, T::class.java)
        )
        finish()
    }

}