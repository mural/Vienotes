package com.vienotes.base

import android.content.Context
import androidx.fragment.app.Fragment
import com.vienotes.MainActivityActions

open class BaseFragment : Fragment() {

    protected lateinit var mainActivityActions: MainActivityActions

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivityActions) {
            mainActivityActions = context
        }
    }

    override fun onResume() {
        super.onResume()
        mainActivityActions.showFAB(false)
    }

}