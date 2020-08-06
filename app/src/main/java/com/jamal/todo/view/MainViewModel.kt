package com.jamal.todo.view

import android.app.Application
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.AndroidViewModel
import com.jamal.todo.R
import com.jamal.todo.core.AppExecutors
import com.jamal.todo.core.Injection
import com.jamal.todo.utility.ActivityUtils

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private var mFragment: String? = null
    private var mFragmentManager: FragmentManager? = null

    fun initFragmentManager(fragmentManager: FragmentManager) {
        mFragmentManager = fragmentManager
    }

    fun navigateToChildFragment(fragment: Fragment) {
        if (mFragment != null &&
                mFragment.equals(fragment::class.simpleName)) {
            return
        }
        ActivityUtils.addFragmentToContentContainer(mFragmentManager!!, fragment
                , R.id.fragment_container, fragment::class.simpleName!!)
        mFragment = fragment::class.simpleName
    }
    }
