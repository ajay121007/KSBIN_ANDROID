package com.example.ks.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ViewPagerAdapter(supportFragmentManger:FragmentManager):FragmentPagerAdapter(supportFragmentManger, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {


    private val mFragmentList=ArrayList<Fragment>()
    private val mFragmentNameList=ArrayList<String>()


    override fun getItem(position: Int): Fragment {
        return mFragmentList[position]

    }


    override fun getCount(): Int {
        return mFragmentList.size

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentNameList[position]
    }
    fun addFragments(fragment: Fragment, names:String){
        mFragmentList.add(fragment)
        mFragmentNameList.add(names)
    }
}