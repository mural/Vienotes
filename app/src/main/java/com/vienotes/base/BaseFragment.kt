package com.vienotes.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.vienotes.FABBehavior

open class BaseFragment : Fragment() {

    protected lateinit var fabBehavior: FABBehavior

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FABBehavior) {
            fabBehavior = context
        }
    }

    override fun onResume() {
        super.onResume()
        fabBehavior.showFAB(false)
    }

}