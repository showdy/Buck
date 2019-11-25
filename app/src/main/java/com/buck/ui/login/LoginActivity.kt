package com.buck.ui.login

import android.os.Bundle
import com.buck.R
import com.buck.internal.StatusBarUtils
import com.buck.ui.base.BaseActivity

class LoginActivity : BaseActivity() {

    companion object {
        const val USERNAME = "username"
        const val PASSWORD = "password"
    }


    private val mAdapter by lazy {
//        BaseFragmentAdapter(
//            supportFragmentManager,
//            arrayListOf<Fragment>(LoginFragment.newInstance(), RegisterFragment.newInstance()),
//            arrayListOf(getString(R.string.sign_in), getString(R.string.sign_up))
//        )
    }

    override fun getLayoutResId() = R.layout.fragment_activity_single

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        StatusBarUtils.setTransparent(this)
        //设置状态栏颜色
//        StatusBarUtils.setTextDark(this, true)
//        viewpager.adapter = mAdapter
//        tablayout.setupWithViewPager(viewpager)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, LoginFragment.newInstance())
            .commitNow()
    }
}
