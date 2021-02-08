package com.vienotes

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.smartledge.vienotes.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainActivityActions {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        fab.setOnClickListener {
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_ListFragment_to_DetailFragment)
        }
    }

    override fun showFAB(show: Boolean) {
        fab.visibility = if (show) View.VISIBLE else View.GONE
    }
}

interface MainActivityActions {
    fun showFAB(show: Boolean)

}