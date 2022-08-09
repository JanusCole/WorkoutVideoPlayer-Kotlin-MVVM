package com.example.workoutapp.ui.groups

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.workoutapp.R

private val TAB_TITLES = arrayOf(
    R.string.classes_fragment_heading,
    R.string.programs_fragment_heading,
    R.string.collections_fragment_heading
)

private val FRAGMENT_ARRAY = arrayOf(
    WorkoutGroupsFragment(),
    WorkoutProgramsFragment(),
    WorkoutCollectionsFragment()
)

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return FRAGMENT_ARRAY[position]
    }

    override fun getPageTitle(position: Int): CharSequence {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 3
    }
}