package com.buck.ui

import android.os.Bundle
import android.util.SparseArray
import android.util.SparseIntArray
import androidx.core.view.GravityCompat
import androidx.appcompat.app.ActionBarDrawerToggle
import android.view.MenuItem
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import androidx.fragment.app.Fragment
import com.buck.R
import com.buck.ui.live.LiveFragment
import com.buck.ui.photo.PhotoFragment
import com.buck.ui.video.VideoFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var mCurrentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.setNavigationItemSelectedListener(mOnDrawerNavigationItemSelectedListener)

        val bottomNavView: BottomNavigationView = findViewById(R.id.bottom_nav_view)
        bottomNavView.setOnNavigationItemSelectedListener(mOnBottomNavigationItemSelectedListener)
        //初始化LiveFragment
        val fragment = LiveFragment.newInstance()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container,fragment,LiveFragment.TAG)
            .commitAllowingStateLoss()
        mCurrentFragment= fragment
    }


    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }


    private val mOnBottomNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.live -> {
                    val fragment = supportFragmentManager.findFragmentByTag(LiveFragment.TAG)
                    fragment.let {
                        LiveFragment.newInstance()
                    }.apply {
                      switchFragment(this)
                    }
                    return@OnNavigationItemSelectedListener true
                }
                R.id.video -> {
                    val fragment = supportFragmentManager.findFragmentByTag(VideoFragment.TAG)
                    fragment.let {
                        VideoFragment.newInstance()
                    }.apply {
                        switchFragment(this)
                    }
                    return@OnNavigationItemSelectedListener true
                }
                R.id.photo -> {
                    val fragment = supportFragmentManager.findFragmentByTag(PhotoFragment.TAG)
                    fragment.let {
                        PhotoFragment.newInstance()
                    }.apply {
                        switchFragment(this)
                    }
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

    //切换fragment
    private fun switchFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        if (mCurrentFragment != fragment) {
            if (!fragment.isAdded) {
                transaction.hide(mCurrentFragment)
                    .add(R.id.fragment_container,fragment,fragment.javaClass.simpleName)
                    .commitAllowingStateLoss()
            } else {
                transaction.hide(mCurrentFragment)
                    .show(fragment)
                    .commitAllowingStateLoss()
            }
            mCurrentFragment = fragment
        }
    }



    private val mOnDrawerNavigationItemSelectedListener = NavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.nav_home -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_tools -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        drawerLayout.closeDrawer(GravityCompat.START)
        return@OnNavigationItemSelectedListener true
    }
}
