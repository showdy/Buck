package com.medisana.tender.ui.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class BaseFragmentAdapter(
    val fragmentManager: FragmentManager,
    val fragments: List<Fragment>,
    val titles: List<String>? = null
) : FragmentPagerAdapter(fragmentManager,BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment = fragments[position]
    override fun getCount(): Int = fragments.size
    override fun getPageTitle(position: Int): CharSequence? = titles?.get(position) ?: ""
}