package com.jamal.todo.utility

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * This provides methods to help Activities load their UI.
 */
class ActivityUtils
/**
 * Don't let anyone instantiate this class.
 */
private constructor() {

    init {
        throw Error("Do not need instantiate!")
    }

    companion object {

        /**
         * The `fragment` is added to the container view with id `frameId`. The operation is
         * performed by the `fragmentManager`.
         */
        fun addFragmentToActivity(
            fragmentManager: FragmentManager
            , fragment: Fragment, frameId: Int
            , isAddToBackStack: Boolean, tag: String?
        ) {
            checkNotNull(fragmentManager)
            val transaction = fragmentManager.beginTransaction()
            if (null != tag) {
                transaction.replace(frameId, fragment, tag)
            } else {
                transaction.replace(frameId, fragment)
            }
            if (isAddToBackStack) {
                transaction.addToBackStack(fragment.javaClass.simpleName)
            }
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction.commit()
        }

        /**
         * The `fragment` is added to the container view with id `frameId`. The operation is
         * performed by the `fragmentManager`.
         */
        fun addNonUIFragmentToActivity(
            fragmentManager: FragmentManager
            , nonUIFragment: Fragment, fragmentTag: String
        ) {
            checkNotNull(fragmentManager)
            checkNotNull(nonUIFragment)
            val transaction = fragmentManager.beginTransaction()
            transaction.add(nonUIFragment, fragmentTag)
            transaction.commit()
        }

        /**
         * The `fragment` is added to the container view with id `frameId`. The operation is
         * performed by the `fragmentManager`.
         */
        fun addFragmentToContentContainer(
            fragmentManager: FragmentManager, fragment: Fragment
            , frameId: Int
            , tag: String
        ) {
            checkNotNull(fragmentManager)
            checkNotNull(fragment)
            val transaction = fragmentManager.beginTransaction()

            transaction.replace(frameId, fragment, tag)

            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction.commit()
        }
    }

}