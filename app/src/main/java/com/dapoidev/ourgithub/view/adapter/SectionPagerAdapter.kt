package com.dapoidev.ourgithub.view.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dapoidev.ourgithub.R
import com.dapoidev.ourgithub.view.fragment.FollowersFragment
import com.dapoidev.ourgithub.view.fragment.FollowingFragment

class SectionPagerAdapter(private val context: Context, fragManager: FragmentManager, data: Bundle)
    : FragmentPagerAdapter(fragManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private var fragmentBundle: Bundle = data

    // menentukan nama judul dari setiap tab layout
    @StringRes
    private val tabName = intArrayOf(R.string.tab1, R.string.tab2)

    // menentukan banyaknya tab layout yang kita buat
    override fun getCount(): Int = 2

    // memastikan dan membuat kedua item tersebut, berjalan sebagaimana mestinya terhadap fragment
    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when(position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

    // menyesuaikan nama tab layout sesuai posisinya
    override fun getPageTitle(position: Int): CharSequence = context.resources.getString(tabName[position])

}

