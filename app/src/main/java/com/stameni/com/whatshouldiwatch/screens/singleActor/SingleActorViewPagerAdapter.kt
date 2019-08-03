package com.stameni.com.whatshouldiwatch.screens.singleActor

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.stameni.com.whatshouldiwatch.screens.singleActor.appearances.SingleActorAppearancesFragment
import com.stameni.com.whatshouldiwatch.screens.singleActor.biography.SingleActorBiographyFragment

class SingleActorViewPagerAdapter(childFragmentManager: FragmentManager)
    : FragmentStatePagerAdapter(childFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> SingleActorBiographyFragment()
            else -> SingleActorAppearancesFragment()
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