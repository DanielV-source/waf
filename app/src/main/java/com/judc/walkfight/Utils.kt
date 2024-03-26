package com.judc.walkfight

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

/**
 * Util class to work with fragments
 */
class Utils {
    companion object {
        /**
         * Adds a new fragment on top of the current one.
         * @param containerId The layout ID of the container for the fragment.
         * @param fragment The new fragment to add.
         * @param addToBackStack Determines whether the transaction should be added to the back stack.
         * @param tag Optional tag name for the fragment.
         */
        fun Fragment.addFragment(
            @IdRes containerId: Int,
            fragment: Fragment,
            addToBackStack: Boolean = false,
            tag: String? = null
        ) {
            performTransaction(containerId, fragment, addToBackStack, tag, isReplace = false)
        }

        /**
         * Replaces the current fragment with a new one.
         * @param containerId The layout ID of the container for the fragment.
         * @param fragment The new fragment to replace the current one.
         * @param addToBackStack Determines whether the transaction should be added to the back stack.
         * @param tag Optional tag name for the fragment.
         */
        fun Fragment.replaceFragment(
            @IdRes containerId: Int,
            fragment: Fragment,
            addToBackStack: Boolean = false,
            tag: String? = null
        ) {
            performTransaction(containerId, fragment, addToBackStack, tag, isReplace = true)
        }

        /**
         * Detach a fragment.
         * @param fragment The fragment to detach.
         */
        fun Fragment.detachFragment(
            fragment: Fragment
        ) {
            val transaction = parentFragmentManager.beginTransaction()
            transaction.detach(fragment)
            transaction.commit()
        }

        /**
         * Detach a fragment by its tag.
         * @param fragmentTag The tag of the fragment to detach.
         */
        fun Fragment.detachFragment(
            fragmentTag: String
        ) {
            val transaction = parentFragmentManager.beginTransaction()
            childFragmentManager.findFragmentByTag(fragmentTag)?.let { transaction.detach(it) }
            transaction.commit()
        }

        /**
         * Performs the actual fragment transaction.
         * @param containerId The layout ID of the container for the fragment.
         * @param fragment The fragment to be added or replaced.
         * @param addToBackStack Determines whether the transaction should be added to the back stack.
         * @param tag Optional tag name for the fragment.
         * @param isReplace Determines whether the fragment should be replaced or added.
         */
        private fun Fragment.performTransaction(
            @IdRes containerId: Int,
            fragment: Fragment,
            addToBackStack: Boolean,
            tag: String?,
            isReplace: Boolean
        ) {
            val transaction = parentFragmentManager.beginTransaction()

            if (isReplace) {
                transaction.replace(containerId, fragment, tag)
            } else {
                transaction.add(containerId, fragment, tag)
            }

            if (addToBackStack) {
                if (tag != null) {
                    parentFragmentManager.clearBackStack(tag)
                }
                transaction.addToBackStack(tag)
            }

            transaction.commit()
        }
    }
}