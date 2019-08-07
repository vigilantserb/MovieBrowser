package com.stameni.com.whatshouldiwatch.screens.singlePerson

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.stameni.com.whatshouldiwatch.screens.singlePerson.appearances.SinglePersonAppearancesFragment
import com.stameni.com.whatshouldiwatch.screens.singlePerson.biography.SingleActorBiographyFragment

class SinglePersonViewPagerAdapter(childFragmentManager: FragmentManager)
    : FragmentStatePagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> SingleActorBiographyFragment()
            else -> SinglePersonAppearancesFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Biography"
            else -> "Appearances"
        }
    }
}