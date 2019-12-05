package com.savemykeys.views.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val TAG = "ViewPagerAdapter"
    private var mFragmentList = ArrayList<Fragment>()
    private var mFragmentTitleList = ArrayList<String>()

    override fun getItem(position: Int): Fragment {
        Log.d(TAG, "getItem")
        return mFragmentList.get(position)
    }

    override fun getCount(): Int {
        Log.d(TAG, "getCount")
        return mFragmentList.size
    }

    fun addFragment(fragment: Fragment, title: String) {
        Log.d(TAG, "addFragment")
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        Log.d(TAG, "getPageTitle")
        return mFragmentTitleList.get(position)
    }

}