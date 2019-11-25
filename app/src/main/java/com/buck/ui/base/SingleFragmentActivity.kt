package com.buck.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.buck.R

abstract class SingleFragmentActivity : BaseActivity() {

    override fun getLayoutResId(): Int = R.layout.fragment_activity_single

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, buildFragment())
            .commitNow()
    }

    abstract fun buildFragment(): Fragment

}